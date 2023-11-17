package com.banking.transaction.controller;

import com.banking.transaction.dto.TransactionDTO;
import com.banking.transaction.exception.ClosedAccountException;
import com.banking.transaction.exception.InSufficientBalanceException;
import com.banking.transaction.exception.InvalidAccountNumberException;
import com.banking.transaction.exception.InvalidTransactionIdException;
import com.banking.transaction.request.DepositRequest;
import com.banking.transaction.request.TransferRequest;
import com.banking.transaction.request.WithdrawalRequest;
import com.banking.transaction.response.DepositResponse;
import com.banking.transaction.response.AccountStatementResponse;
import com.banking.transaction.response.TransferResponse;
import com.banking.transaction.response.WithdrawalResponse;
import com.banking.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> findByTransactionId(@PathVariable Long transactionId){
        try{
            TransactionDTO transactionDTO = transactionService.findByTransactionId(transactionId);
            return new ResponseEntity<>(transactionDTO, HttpStatus.FOUND);
        }catch (InvalidTransactionIdException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deposit")
    public ResponseEntity<?> makeDeposit(@RequestBody DepositRequest depositRequest){
        try {
            DepositResponse depositResponse = transactionService.makeDeposit(depositRequest);
            return new ResponseEntity<>(depositResponse, HttpStatus.OK);
        } catch (InvalidAccountNumberException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }catch (ClosedAccountException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawalRequest withdrawalRequest) {
        try {
            WithdrawalResponse withdrawalResponse = transactionService.withdraw(withdrawalRequest);
            return new ResponseEntity<>(withdrawalResponse, HttpStatus.OK);
        } catch (ClosedAccountException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (InSufficientBalanceException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (InvalidAccountNumberException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/transfer")
    public ResponseEntity<?> makeTransfer (@RequestBody TransferRequest transferRequest){
        try {
            TransferResponse transferResponse = transactionService.makeTransfer(transferRequest);
            return new ResponseEntity<>(transferResponse, HttpStatus.OK);
        } catch (InvalidAccountNumberException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (InSufficientBalanceException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        } catch (ClosedAccountException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/initial-deposit/{accountNumber}/{fullName}/{balance}")
    public ResponseEntity<Void> createInitialDepositTransaction (@PathVariable Long accountNumber,
                                                                 @PathVariable String fullName,
                                                                 @PathVariable Double balance
    ){
        transactionService.createInitialDepositTransaction(accountNumber, fullName, balance);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/statement/{accountNumber}")
    public AccountStatementResponse findAccountStatementByAccountNumber(@PathVariable Long accountNumber){
        AccountStatementResponse accountStatementResponse = transactionService.findAccountStatementByAccountNumber(accountNumber);
        return accountStatementResponse;
    }
}
