package com.labs.jonathan.jnrxs.stream;

public interface StreamPost<T, K> {
    int PRIORITY_MEDIUM = 2;

    void setMessage(T message);

    T getMessage();

    Integer getPriority();
}
