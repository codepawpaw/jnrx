package com.labs.jonathan.jnrxs.stream;

import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public interface Stream<Q extends PriorityBlockingQueue<? extends WeakReference<? extends StreamPostImpl>>,
        M extends ConcurrentHashMap<? extends Integer,? extends WeakReference<?>>> {
    int MSG_QUEUE_INITIAL_CAPACITY = 10;
    int SUBSCRIBER_INITIAL_CAPACITY = 10;

    Integer getStreamId();

    StreamState getStreamState();

    ConcurrentHashMap<Integer,WeakReference<Object>> getSubscriberMap();
}
