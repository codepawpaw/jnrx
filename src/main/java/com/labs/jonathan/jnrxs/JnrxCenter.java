package com.labs.jonathan.jnrxs;

import com.labs.jonathan.jnrxs.operation.CoreOperation;
import com.labs.jonathan.jnrxs.stream.Stream;
import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class JnrxCenter extends CoreOperation {
    public JnrxCenter(ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                    ConcurrentHashMap<Integer,WeakReference<Object>>>> tunnelMap, PriorityBlockingQueue<WeakReference<StreamPostImpl>> queue) {
        super(tunnelMap, queue);
    }
}
