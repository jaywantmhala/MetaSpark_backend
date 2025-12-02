package com.switflow.swiftFlow.Exception;

public class ProductAlreadyExistsException extends RuntimeException {
    
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}