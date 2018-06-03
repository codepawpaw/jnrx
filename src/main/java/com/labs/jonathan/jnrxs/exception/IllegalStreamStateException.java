package com.labs.jonathan.jnrxs.exception;

public class IllegalStreamStateException extends Exception{

    public IllegalStreamStateException(String message) {
        super(message);
    }

    public String toString() {
        return "IllegalStreamStateException[" + super.getMessage() + "]";
    }
}
