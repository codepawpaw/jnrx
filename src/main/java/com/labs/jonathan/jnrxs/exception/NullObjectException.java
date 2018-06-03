package com.labs.jonathan.jnrxs.exception;

public class NullObjectException extends Exception{

    public NullObjectException(String message) {
        super(message);
    }

    public String toString() {
        return "NullObjectException[" + super.getMessage() + "]";
    }
}
