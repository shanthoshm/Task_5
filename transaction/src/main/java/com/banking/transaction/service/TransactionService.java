package com.banking.transaction.service;

import com.banking.transaction.dto.TransactionDTO;
import com.banking.transaction.entity.Transaction;
import com.banking.transaction.enumeration.TransactionType;
import com.banking.transaction.exception.ClosedAccountException;
import com.banking.transaction.exception.InSufficientBalanceException;
import com.banking.transaction.exception.InvalidAccountNumberException;
import com.banking.transaction.exception.InvalidTransactionIdException;
import com.banking.transaction.external.Account;
import com.banking.transaction.repository.TransactionRepository;
import com.banking.transaction.request.DepositRequest;
import com.banking.transaction.request.TransferRequest;
import com.banking.transaction.request.WithdrawalRequest;
import com.banking.transaction.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Account findByAccountNumber(Long accountNumber){
        //WebClient webClient = WebClient.create("http://localhost:8071");
        WebClient webClient = WebClient.create("http://localhost:8092/account-service");
        Account account = webClient.get()
                .uri("/account/{accountNumber}", accountNumber)
                .retrieve()
                .bodyToMono(Account.class)
                .block();
        return account;
    }

    private Long generateTransactionId(){
        Random random = new Random();
        long transactionId = Math.abs(1_000_000_000L + random.nextLong(9_000_000_000L));
        return transactionId;
    }

    public Boolean checkAccountStatus(Long accountNumber){
//        WebClient webClient = WebClient.create("http://localhost:8071");
        WebClient webClient = WebClient.create("http://localhost:8092/account-service");
        Boolean b = webClient.get()
                .uri("/account/status/{accountNumber}", accountNumber)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return b;
    }

    private TransactionDTO toTransactionDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setAccountNumber(transaction.getAccountNumber());
        transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
        transactionDTO.setAvailableBalance(transaction.getAvailableBalance());
        transactionDTO.setUserName(transaction.getUserName());

        return transactionDTO;
    }
    public TransactionDTO findByTransactionId(Long transactionId){
        if (!transactionRepository.findByTransactionId(transactionId).isPresent()){
            throw new InvalidTransactionIdException("Invalid transaction ID!!");
        }
        Transaction transaction = transactionRepository.findByTransactionId(transactionId).get();
        TransactionDTO transactionDTO = toTransactionDTO(transaction);
        return transactionDTO;
    }

    public void createInitialDepositTransaction(Long accountNumber, String fullName, Double balance){
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionId(generateTransactionId());
        transaction.setUserName(fullName);
        transaction.setTransactionType(TransactionType.INTIAL_DEPOSIT);
        transaction.setTransactionAmount(balance);
        transaction.setAvailableBalance(balance);
        transactionRepository.save(transaction);
    }

    public TransferResponse makeTransfer(TransferRequest transferRequest) {
        if (findByAccountNumber(transferRequest.getFromAccountNumber()) == null){
            throw new InvalidAccountNumberException("Invalid sender's account number");
        }

        if (findByAccountNumber(transferRequest.getToAccountNumber()) == null){
            throw new InvalidAccountNumberException("Invalid recipient's account number");
        }

        if (checkAccountStatus(transferRequest.getFromAccountNumber()) == false) {
            throw new ClosedAccountException("CLOSED Account!!");
        }

        if (checkAccountStatus(transferRequest.getToAccountNumber()) == false) {
            throw new ClosedAccountException("CLOSED Account!!");
        }

        Account fromAccount = findByAccountNumber(transferRequest.getFromAccountNumber());
        Account toAccount = findByAccountNumber(transferRequest.getToAccountNumber());

        Double initialBalance1 = fromAccount.getBalance();
        Double balanceAfterTransfer1 = initialBalance1 - transferRequest.getTransferAmount();

        Double initialBalance2 = toAccount.getBalance();
        Double balanceAfterTransfer2 = initialBalance2 + transferRequest.getTransferAmount();

        if (fromAccount.getBalance() >= transferRequest.getTransferAmount()) {
            fromAccount.setBalance(balanceAfterTransfer1);
            toAccount.setBalance(balanceAfterTransfer2);

            Long fromAccountNumber = fromAccount.getAccountNumber();
            Long toAccountNumber = toAccount.getAccountNumber();
            Double amount = transferRequest.getTransferAmount();

//            WebClient webClient = WebClient.create("http://localhost:8071");
            WebClient webClient = WebClient.create("http://localhost:8092/account-service");

            webClient.put()
                    .uri("/account/transfer/{fromAccountNumber}/{toAccountNumber}/{amount}", fromAccountNumber, toAccountNumber, amount)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchange()
                    .block();

            Transaction transaction1 = new Transaction();
            transaction1.setTransactionType(TransactionType.TRANSFER_SENT);
            transaction1.setTransactionId(generateTransactionId());
            transaction1.setTransactionAmount(transferRequest.getTransferAmount());
            transaction1.setUserName(fromAccount.getFullName());
            transaction1.setAccountNumber(fromAccount.getAccountNumber());
            transaction1.setAvailableBalance(balanceAfterTransfer1);
            transactionRepository.save(transaction1);

            Transaction transaction2 = new Transaction();
            transaction2.setTransactionType(TransactionType.TRANSFER_RECEIVED);
            transaction2.setTransactionId(generateTransactionId());
            transaction2.setTransactionAmount(transferRequest.getTransferAmount());
            transaction2.setUserName(toAccount.getFullName());
            transaction2.setAccountNumber(toAccount.getAccountNumber());
            transaction2.setAvailableBalance(balanceAfterTransfer2);
            transactionRepository.save(transaction2);

            TransferResponse transferResponse = new TransferResponse();
            transferResponse.setTransactionId(transaction1.getTransactionId());
            transferResponse.setTransferAmount(transaction1.getTransactionAmount());
            transferResponse.setFromAccount(transaction1.getAccountNumber());
            transferResponse.setToAccount(transferRequest.getToAccountNumber());
            transferResponse.setPreviousBalance(initialBalance1);
            transferResponse.setAvailableBalance(transaction1.getAvailableBalance());

            return transferResponse;
        } else {
            throw new InSufficientBalanceException("Account doesn't have sufficient balance to make transfer");
        }
    }
    public DepositResponse makeDeposit(DepositRequest depositRequest) {
        Account account = findByAccountNumber(depositRequest.getAccountNumber());
        if (findByAccountNumber(depositRequest.getAccountNumber()) == null){
            throw new InvalidAccountNumberException("Invalid account number");
        }

        if (checkAccountStatus(account.getAccountNumber()) == false){
            throw new ClosedAccountException("CLOSED Account!!");
        }

        account.setAccountNumber(depositRequest.getAccountNumber());
        account.setBalance(depositRequest.getDepositAmount() + account.getBalance());
        Double balanceAfterDeposit = account.getBalance();

        Long accountNumber = account.getAccountNumber();
        Double amount = depositRequest.getDepositAmount();
//        WebClient webClient = WebClient.create("http://localhost:8071");
        WebClient webClient = WebClient.create("http://localhost:8092/account-service");

        webClient.put()
                .uri("/account/deposit/{accountNumber}/{amount}", accountNumber, amount)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .block();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionAmount(depositRequest.getDepositAmount());
        transaction.setUserName(account.getFullName());
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAvailableBalance(balanceAfterDeposit);
        transactionRepository.save(transaction);

        DepositResponse depositResponse = new DepositResponse();
        depositResponse.setTransactionId(generateTransactionId());

        Double depositedAmount = depositRequest.getDepositAmount();
        Double previousBalance = account.getBalance() - depositedAmount;

        depositResponse.setDepositedAmount(depositedAmount);
        depositResponse.setPreviousBalance(previousBalance);
        depositResponse.setAvailableBalance(account.getBalance());

        return depositResponse;
    }

    public WithdrawalResponse withdraw(WithdrawalRequest withdrawalRequest) {
        Account account = findByAccountNumber(withdrawalRequest.getAccountNumber());
        if (findByAccountNumber(withdrawalRequest.getAccountNumber()) == null){
            throw new InvalidAccountNumberException("Invalid account number");
        }

        if (checkAccountStatus(withdrawalRequest.getAccountNumber()) == false) {
            throw new ClosedAccountException("CLOSED Account!!");
        }

        if (account.getBalance() < withdrawalRequest.getWithdrawalAmount()) {
            throw new InSufficientBalanceException("Insufficient balance to withdraw");
        }

        Double balanceAfterWithdrawal = account.getBalance() - withdrawalRequest.getWithdrawalAmount();
        Long accountNumber = account.getAccountNumber();
        Double amount = withdrawalRequest.getWithdrawalAmount();
//        WebClient webClient = WebClient.create("http://localhost:8071");
        WebClient webClient = WebClient.create("http://localhost:8092/account-service");

        webClient.put()
                .uri("/account/withdraw/{accountNumber}/{amount}", accountNumber, amount)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .block();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionAmount(withdrawalRequest.getWithdrawalAmount());
        transaction.setUserName(account.getFullName());
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAvailableBalance(balanceAfterWithdrawal);
        transactionRepository.save(transaction);

        WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
        withdrawalResponse.setTransactionId(generateTransactionId());

        Double withdrawalAmount = withdrawalRequest.getWithdrawalAmount();
        Double previousBalance = account.getBalance();
        Double availableBalance = account.getBalance() - withdrawalAmount;

        withdrawalResponse.setWithdrawalAmount(withdrawalAmount);
        withdrawalResponse.setPreviousBalance(previousBalance);
        withdrawalResponse.setAvailableBalance(availableBalance);

        return withdrawalResponse;
    }

    public AccountStatementResponse findAccountStatementByAccountNumber(Long accountNumber){
        if (findByAccountNumber(accountNumber) == null){
            throw new InvalidAccountNumberException("Invalid account number");
        }
        List<Transaction> transactionsByAccountNumber = transactionRepository.findByAccountNumber(accountNumber);
        List<TransactionListingResponse> transactionListingResponses = new ArrayList<>();
        transactionListingResponses = transactionsByAccountNumber.stream().map(transaction -> new TransactionListingResponse(
                transaction.getTransactionId(),
                transaction.getTransactionType(),
                transaction.getTransactionAmount(),
                transaction.getAvailableBalance()
        )).collect(Collectors.toList());

        Account account = findByAccountNumber(accountNumber);
        String fullName = account.getFullName();
        Double currentBalance = account.getBalance();

        AccountStatementResponse accountStatementResponse = new AccountStatementResponse();
        accountStatementResponse.setFullName(fullName);
        accountStatementResponse.setBalance(account.getBalance());
        accountStatementResponse.setAccountNumber(accountNumber);
        accountStatementResponse.setTransactionListings(transactionListingResponses);

        return accountStatementResponse;
    }
}
