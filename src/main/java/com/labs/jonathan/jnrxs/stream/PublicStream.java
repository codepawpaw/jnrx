package com.labs.jonathan.jnrxs.stream;

import com.labs.jonathan.jnrxs.stream.impl.AbstractStream;
import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class PublicStream<Q extends PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
        M extends ConcurrentHashMap<Integer,WeakReference<Object>>>
        extends AbstractStream<Q,M> {

    public PublicStream(Integer streamId, StreamState state, ConcurrentHashMap<Integer,WeakReference<Object>> subscriberMap) {
        super(streamId, state, subscriberMap);

    }
}
