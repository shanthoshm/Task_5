package com.banking.transaction.exception;

public class InvalidTransactionIdException extends RuntimeException {
    public InvalidTransactionIdException(String message){
        super(message);
    }
}
