package com.banking.account.service;

import com.banking.account.dto.AccountDTO;
import com.banking.account.entity.Account;
import com.banking.account.enumeration.AccountStatus;
import com.banking.account.exception.InvalidAccountNumberException;
import com.banking.account.external.Transaction;
import com.banking.account.external.TransactionType;
import com.banking.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private Account toAccount(AccountDTO accountDTO){
        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setFullName(accountDTO.getFullName());
        account.setEmail(accountDTO.getEmail());
        account.setStreet(accountDTO.getStreet());
        account.setCity(accountDTO.getCity());
        account.setState(accountDTO.getState());
        account.setCountry(accountDTO.getCountry());
        account.setAccountType(accountDTO.getAccountType());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setBalance(accountDTO.getBalance());

        return account;
    }

    private AccountDTO toAccountDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setStreet(account.getStreet());
        accountDTO.setCity(account.getCity());
        accountDTO.setState(account.getState());
        accountDTO.setCountry(account.getCountry());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setAccountStatus(account.getAccountStatus());
        accountDTO.setBalance(account.getBalance());

        return accountDTO;
    }

    private Long generateAccountNumber(){
        Random random = new Random();
        Long accountNumber = random.nextLong(10000000000L);
        return accountNumber;
    }
    public AccountDTO findByAccountNumber(Long accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()){
            Account foundAccount = accountRepository.findByAccountNumber(accountNumber).get();
            AccountDTO accountDTO = toAccountDTO(foundAccount);
            return accountDTO;
        }
        return null;
    }

    public void deleteAccountByAccountNumber(Long accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (!account.isPresent()){
            throw new InvalidAccountNumberException("Invalid account number!!");
        }
        accountRepository.deleteByAccountNumber(accountNumber);
    }

    public AccountDTO findAccountByAccountNumber(Long accountNumber){
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (!account.isPresent()){
            throw new InvalidAccountNumberException("Invalid account number!!");
        }
        Account foundAccount = accountRepository.findByAccountNumber(accountNumber).get();
        AccountDTO accountDTO = toAccountDTO(foundAccount);
        return accountDTO;
    }

    public Boolean checkAccountStatus(Long accountNumber){
        AccountDTO accountDTO = findByAccountNumber(accountNumber);
        if (accountDTO.getAccountStatus().equals(AccountStatus.ACTIVE)){
            return true;
        } else {
            return false;
        }
    }

    public AccountDTO createAccount(AccountDTO accountDTO){
        Account account = toAccount(accountDTO);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account = accountRepository.save(account);

        Double balance = accountDTO.getBalance();
        if (balance > 0) {
//            WebClient webClient = WebClient.create("http://localhost:8072");
            WebClient webClient = WebClient.create("http://localhost:8092/transaction-service");
            webClient.post()
                    .uri("/transaction/initial-deposit/{accountNumber}/{fullName}/{balance}",
                            account.getAccountNumber(),
                            accountDTO.getFullName(),
                            balance
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchange()
                    .block();
        }

        return toAccountDTO(account);
    }

    public String changeAccountStatus(Long accountNumber, String accountStatus){
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()){
            Account foundAccount = accountRepository.findByAccountNumber(accountNumber).get();
            foundAccount.setAccountStatus(AccountStatus.valueOf(accountStatus.toUpperCase()));
            accountRepository.save(foundAccount);
            return "Status updated successfully!";
        }
        else {
            return "Account not found!!!";
        }
    }

    public String updateAccountForDeposit(Long accountNumber, Double amount){
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()){
            Account foundAccount = accountRepository.findByAccountNumber(accountNumber).get();
            foundAccount.setBalance(foundAccount.getBalance() + amount);
            accountRepository.save(foundAccount);
            return "Deposited successfully!";
        }
        else {
            return "Account not found!!!";
        }
    }

    public String updateAccountForWithdrawal(Long accountNumber, Double amount){
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()){
            Account foundAccount = accountRepository.findByAccountNumber(accountNumber).get();
            foundAccount.setBalance(foundAccount.getBalance() - amount);
            accountRepository.save(foundAccount);
            return "Withdrawn successfully!";
        }
        else {
            return "Account not found!!!";
        }
    }

    public String updateAccountForTransfer(Long fromAccountNumber, Long toAccountNumber, Double amount){
        if (accountRepository.findByAccountNumber(fromAccountNumber).isPresent() &&
                accountRepository.findByAccountNumber(toAccountNumber).isPresent()){
            Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).get();
            Double fromAccountBalance = fromAccount.getBalance() - amount;

            Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).get();
            Double toAccountBalance = toAccount.getBalance() + amount;

            fromAccount.setBalance(fromAccountBalance);
            toAccount.setBalance(toAccountBalance);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            return "Transferred successfully!";
        }
        else {
            return "Account not found!!!";
        }
    }
}
