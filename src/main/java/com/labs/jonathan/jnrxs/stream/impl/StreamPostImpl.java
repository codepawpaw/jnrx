package com.labs.jonathan.jnrxs.stream.impl;

import com.labs.jonathan.jnrxs.stream.StreamPost;

public class StreamPostImpl<T, V> implements StreamPost<T, V> {

    private T message;
    private Integer streamId;
    private Integer priority;

    public StreamPostImpl(T message, Integer streamId, Integer priority) {
        this.message = message;
        this.streamId = streamId;
        this.priority = priority;
    }

    @Override
    public void setMessage(T message){
        this.message = message;
    }

    @Override
    public T getMessage() {
        return message;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    public Integer getStreamId() {
        return streamId;
    }

    public void setStreamId(Integer streamId){
        this.streamId = streamId;
    }
}
