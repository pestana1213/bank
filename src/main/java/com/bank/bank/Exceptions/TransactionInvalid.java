package com.bank.bank.Exceptions;

public class TransactionInvalid extends IllegalArgumentException {
    public TransactionInvalid(String message) {
        super(message);
    }
}
