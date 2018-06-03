package com.labs.jonathan.jnrxs.exception;

public class InvalidSubscriberException extends Exception{

    public InvalidSubscriberException(String message) {
        super(message);
    }

    public String toString() {
        return "InvalidSubscriberException[" + super.getMessage() + "]";
    }
}
