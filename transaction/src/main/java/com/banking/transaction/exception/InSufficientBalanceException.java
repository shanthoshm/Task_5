package com.banking.transaction.exception;

public class InSufficientBalanceException extends RuntimeException {
    public InSufficientBalanceException(String message){
        super(message);
    }
}
