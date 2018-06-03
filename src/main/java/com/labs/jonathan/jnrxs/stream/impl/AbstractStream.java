package com.labs.jonathan.jnrxs.stream.impl;

import com.labs.jonathan.jnrxs.stream.Stream;
import com.labs.jonathan.jnrxs.stream.StreamState;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class AbstractStream<Q extends PriorityBlockingQueue<? extends WeakReference<? extends StreamPostImpl>>,
        M extends ConcurrentHashMap<? extends Integer,? extends WeakReference<?>>> implements Stream<Q, M> {

    private Integer streamId;
    private StreamState streamState;
    private ConcurrentHashMap<Integer,WeakReference<Object>> subscriberMap;

    public AbstractStream(Integer streamId, StreamState state, ConcurrentHashMap<Integer,WeakReference<Object>> subscriberMap) {
        this.streamId = streamId;
        this.streamState = state;
        this.subscriberMap = subscriberMap;
    }

    @Override
    public Integer getStreamId() {
        return streamId;
    }

    @Override
    public StreamState getStreamState() {
        return streamState;
    }

    @Override
    public ConcurrentHashMap<Integer,WeakReference<Object>> getSubscriberMap() {
        return subscriberMap;
    }

    public ConcurrentHashMap<Integer,WeakReference<Object>> subscriberMap() {
        return subscriberMap;
    }
}
