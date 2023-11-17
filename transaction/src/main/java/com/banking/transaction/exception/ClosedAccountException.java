package com.banking.transaction.exception;

public class ClosedAccountException extends RuntimeException {
    public ClosedAccountException(String message){
        super(message);
    }
}
