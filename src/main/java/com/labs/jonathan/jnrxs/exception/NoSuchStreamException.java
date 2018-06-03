package com.labs.jonathan.jnrxs.exception;

public class NoSuchStreamException extends Exception{

    public NoSuchStreamException(String message) {
        super(message);
    }

    public String toString() {
        return "NoSuchStreamException[" + super.getMessage() + "]";
    }
}
