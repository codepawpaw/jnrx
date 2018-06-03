package com.labs.jonathan.jnrxs;

import com.labs.jonathan.jnrxs.stream.Stream;
import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class Jnrx {
    public static JnrxCenter jnrx;

    static {
        init();
    }

    public static void init() {
        ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                                ConcurrentHashMap<Integer,WeakReference<Object>>>> tunnelMap = new ConcurrentHashMap<>(5);
        PriorityBlockingQueue<WeakReference<StreamPostImpl>> queue = new PriorityBlockingQueue<>(Stream.MSG_QUEUE_INITIAL_CAPACITY,
                new Comparator<WeakReference<StreamPostImpl>>() {
                    @Override
                    public int compare(WeakReference<StreamPostImpl> o1, WeakReference<StreamPostImpl> o2) {
                        StreamPostImpl post1 = o1.get();
                        StreamPostImpl post2 = o2.get();
                        if(post1 != null || post2 != null
                                || post1.getPriority() != null
                                || post2.getPriority() != null){
                            return post1.getPriority().compareTo(post2.getPriority());
                        }else{
                            return 0;
                        }
                    }
                });

        jnrx = new JnrxCenter(tunnelMap, queue);
    }
}
