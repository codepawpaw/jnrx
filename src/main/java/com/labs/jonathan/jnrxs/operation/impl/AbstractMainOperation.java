package com.labs.jonathan.jnrxs.operation.impl;

import com.labs.jonathan.jnrxs.operation.MainOperation;
import com.labs.jonathan.jnrxs.stream.Stream;
import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public abstract class AbstractMainOperation implements MainOperation {

    private ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                    ConcurrentHashMap<Integer,WeakReference<Object>>>> streamMap;

    private PriorityBlockingQueue<WeakReference<StreamPostImpl>> postQueue;

    public AbstractMainOperation(ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                ConcurrentHashMap<Integer,WeakReference<Object>>>> streamMap, PriorityBlockingQueue<WeakReference<StreamPostImpl>> postQueue) {
        this.streamMap = streamMap;
        this.postQueue = postQueue;
    }

    @Override
    public PriorityBlockingQueue<WeakReference<StreamPostImpl>> getPostQueue() {
        return postQueue;
    }

    @Override
    public ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                ConcurrentHashMap<Integer,WeakReference<Object>>>> getStreamMap() {
        return streamMap;
    }

    public ConcurrentHashMap<Integer,WeakReference<Object>> getSubscriberMap(Integer streamId) {
        return streamMap.get(streamId).getSubscriberMap();
    }
}
