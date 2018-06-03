package com.labs.jonathan.jnrxs.exception;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException(String message) {
        super(message);
    }

    public String toString() {
        return "AlreadyExistsException[" + super.getMessage() + "]";
    }
}
