package com.banking.account.controller;

import com.banking.account.dto.AccountDTO;

import com.banking.account.exception.InvalidAccountNumberException;
import com.banking.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/status/{accountNumber}")
    public Boolean checkAccountStatus(@PathVariable Long accountNumber){
        Boolean b = accountService.checkAccountStatus(accountNumber);
        return b;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> findByAccountNumber(@PathVariable Long accountNumber){
        try {
            AccountDTO accountDTO = accountService.findByAccountNumber(accountNumber);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        }catch (RuntimeException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteByAccountNumber(@PathVariable Long accountNumber){
        try{
            accountService.deleteAccountByAccountNumber(accountNumber);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (InvalidAccountNumberException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<?> findAccountByAccountNumber(@PathVariable Long accountNumber){
        try {
            AccountDTO accountDTO = accountService.findAccountByAccountNumber(accountNumber);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        }catch (InvalidAccountNumberException e){
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO){
        AccountDTO createdAccountDTO = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccountDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{accountNumber}/{accountStatus}")
    public ResponseEntity<String> changeAccountStatus(@PathVariable Long accountNumber, @PathVariable String accountStatus){
        String message = accountService.changeAccountStatus(accountNumber, accountStatus);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/deposit/{accountNumber}/{amount}")
    public ResponseEntity<String> updateAccountForDeposit(@PathVariable Long accountNumber, @PathVariable Double amount){
        String message = accountService.updateAccountForDeposit(accountNumber, amount);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public ResponseEntity<String> updateAccountForWithdrawal(@PathVariable Long accountNumber, @PathVariable Double amount){
        String message = accountService.updateAccountForWithdrawal(accountNumber, amount);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/transfer/{fromAccountNumber}/{toAccountNumber}/{amount}")
    public ResponseEntity<String> updateAccountForTransfer(@PathVariable Long fromAccountNumber,
                                                           @PathVariable Long toAccountNumber,
                                                           @PathVariable Double amount){
        String message = accountService.updateAccountForTransfer(fromAccountNumber, toAccountNumber, amount);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
