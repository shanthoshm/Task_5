package com.banking.account.exception;

public class InvalidAccountNumberException extends RuntimeException {
    public InvalidAccountNumberException(String message){
        super(message);
    }
}
