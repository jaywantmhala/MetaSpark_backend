package com.switflow.swiftFlow.Exception;

public class CustomerEmailAlreadyExistsException extends RuntimeException {
    public CustomerEmailAlreadyExistsException(String message) {
        super(message);
    }
}