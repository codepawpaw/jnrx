package com.labs.jonathan.jnrxs.operation;

import com.labs.jonathan.jnrxs.stream.Stream;
import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public interface MainOperation {

    PriorityBlockingQueue<WeakReference<StreamPostImpl>> getPostQueue();

    ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                        ConcurrentHashMap<Integer,WeakReference<Object>>>> getStreamMap();
}
