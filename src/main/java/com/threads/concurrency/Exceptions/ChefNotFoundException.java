package com.threads.concurrency.Exceptions;


public class ChefNotFoundException extends RuntimeException {
    public ChefNotFoundException(String message) {
        super(message);
    }
}

