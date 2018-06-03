package com.labs.jonathan.jnrxs.operation;

import com.labs.jonathan.jnrxs.OnMessage;
import com.labs.jonathan.jnrxs.exception.AlreadyExistsException;
import com.labs.jonathan.jnrxs.exception.IllegalStreamStateException;
import com.labs.jonathan.jnrxs.exception.NoSuchStreamException;
import com.labs.jonathan.jnrxs.exception.NullObjectException;
import com.labs.jonathan.jnrxs.operation.impl.AbstractMainOperation;
import com.labs.jonathan.jnrxs.stream.PublicStream;
import com.labs.jonathan.jnrxs.stream.Stream;
import com.labs.jonathan.jnrxs.stream.StreamPost;
import com.labs.jonathan.jnrxs.stream.StreamState;
import com.labs.jonathan.jnrxs.stream.impl.StreamPostImpl;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class CoreOperation extends AbstractMainOperation {
    public CoreOperation(ConcurrentHashMap<Integer, Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                        ConcurrentHashMap<Integer,WeakReference<Object>>>> streamMap, PriorityBlockingQueue<WeakReference<StreamPostImpl>> queue) {
        super(streamMap, queue);
    }

    public Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                ConcurrentHashMap<Integer,WeakReference<Object>>> getStream(Integer streamId)
            throws NoSuchStreamException, NullObjectException {
        if(streamId == null){
            throw new NullObjectException("Stream id cannot be null");
        }
        if(!getStreamMap().containsKey(streamId)){
            throw new NoSuchStreamException("Stream with id " + streamId + " does not exists");
        }
        if(getStreamMap().get(streamId) == null){
            throw new NoSuchStreamException("Stream with id " + streamId + " does not exists");
        }

        return getStreamMap().get(streamId);

    }

    public Stream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>, ConcurrentHashMap<Integer,
                WeakReference<Object>>> createPublicStream(Integer streamId) throws AlreadyExistsException {
        if(streamId == null){
            return null;
        }

        if (getStreamMap().containsKey(streamId)) {
            throw new AlreadyExistsException("Stream with id " + streamId + " already exists");
        }

        PublicStream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>, ConcurrentHashMap<Integer,
                                        WeakReference<Object>>> publicStream =
                new PublicStream<PriorityBlockingQueue<WeakReference<StreamPostImpl>>,
                                        ConcurrentHashMap<Integer,WeakReference<Object>>>(
                        streamId,
                        StreamState.OPEN,
                        new ConcurrentHashMap<Integer,WeakReference<Object>>(Stream.SUBSCRIBER_INITIAL_CAPACITY));

        getStreamMap().put(streamId, publicStream);

        return publicStream;
    }

    public <T> void addSubscriber(Integer streamId, T subscriber, Integer subscriberId)
            throws NoSuchStreamException, AlreadyExistsException, IllegalStreamStateException, NullObjectException {
        runSubscriptionTask(streamId, subscriber, subscriberId);
    }

    private <T>void runSubscriptionTask(Integer streamId, T subscriber, Integer subscriberId)
            throws NoSuchStreamException, AlreadyExistsException, IllegalStreamStateException, NullObjectException{

        if(subscriber == null){
            throw new NullObjectException("subscriber cannot be null");
        }
        if(subscriberId == null){
            throw new NullObjectException("subscriberId cannot be null");
        }

        getSubscriberMap(streamId).put(subscriberId, new WeakReference<Object>(subscriber));
    }

    public <T> void publish(Integer streamId, T msg, Integer subscribers){
        try {
            publishTask(streamId, msg, subscribers);
        } catch (NoSuchStreamException | NullObjectException | IllegalStreamStateException e){
            e.printStackTrace();
        }
    }

    private <T>void publishTask(Integer streamId, T msg, Integer subscribers)
            throws NoSuchStreamException, IllegalStreamStateException, NullObjectException {

        Stream stream = getStream(streamId);

        if(stream.getStreamState() == StreamState.OPEN) {
            if (subscribers == null) {
                publishMessageToAllSubscriber(msg, stream.getStreamId());
            } else {
                publishMessageToSubscriber(msg, stream.getStreamId(), subscribers);
            }
        } else {
            throw new IllegalStreamStateException("Stream with streamId " + streamId + " has been " + stream.getStreamState());
        }
    }

    public <T> void publishMessageToAllSubscriber(T msg, Integer streamId) throws NullObjectException, IllegalStreamStateException {
        if(msg == null){
            throw new NullObjectException("message is null");
        }
        StreamPostImpl post = new StreamPostImpl<>(msg, streamId, StreamPost.PRIORITY_MEDIUM);
        getPostQueue().put(new WeakReference<>(post));

        while (!getPostQueue().isEmpty()) {
            WeakReference<StreamPostImpl> msgRef = getPostQueue().poll();

            if(msgRef == null) {
                continue;
            }

            StreamPostImpl mspPost = msgRef.get();

            if(mspPost == null && mspPost.getStreamId() == null) {
                continue;
            }

            if(!mspPost.getStreamId().equals(streamId)) {
                continue;
            }

            for (Integer subscriberId : getSubscriberMap(streamId).keySet()) {

                WeakReference<Object> subscriberRef = getSubscriberMap(streamId).get(subscriberId);

                if(subscriberRef == null) {
                    continue;
                }

                Object subscriberObj = subscriberRef.get();

                if (subscriberObj == null) {
                    continue;
                }

                for (final Method method : subscriberObj.getClass().getDeclaredMethods()) {
                    Annotation annotation = method.getAnnotation(OnMessage.class);
                    if (annotation != null) {
                        deliverMessage(subscriberObj, (OnMessage) annotation, method, mspPost, streamId);
                    }
                }
            }
        }
    }

    public <T> void publishMessageToSubscriber(T msg, Integer streamId, Integer subscriberId) throws NullObjectException, IllegalStreamStateException {
        if(msg == null){
            throw new NullObjectException("message is null");
        }
        StreamPostImpl post = new StreamPostImpl<>(msg, streamId, StreamPost.PRIORITY_MEDIUM);
        getPostQueue().put(new WeakReference<>(post));

        while (!getPostQueue().isEmpty()) {
            WeakReference<StreamPostImpl> msgRef = getPostQueue().poll();

            if(msgRef == null) {
                continue;
            }

            StreamPostImpl mspPost = msgRef.get();

            if(mspPost == null && mspPost.getStreamId() == null) {
                continue;
            }

            if(!mspPost.getStreamId().equals(streamId)) {
                continue;
            }

            WeakReference<Object> subscriberRef = getSubscriberMap(streamId).get(subscriberId);

            if(subscriberRef == null) {
                continue;
            }

            Object subscriberObj = subscriberRef.get();

            if (subscriberObj == null) {
                continue;
            }

            for (final Method method : subscriberObj.getClass().getDeclaredMethods()) {
                Annotation annotation = method.getAnnotation(OnMessage.class);
                if (annotation != null) {
                    deliverMessage(subscriberObj, (OnMessage) annotation, method, mspPost, streamId);
                }
            }
        }
    }

    public <T, P extends StreamPost<?, ?>> boolean deliverMessage(T subscriber, OnMessage msgAnnotation, Method method, P post, Integer idStream) {
        try {
            boolean methodFound = false;
            for (final Class paramClass : method.getParameterTypes()) {
                if (paramClass.equals(post.getMessage().getClass())) {
                    methodFound = true;
                    break;
                }
            }
            if (methodFound) {
                method.setAccessible(true);
                method.invoke(subscriber, post.getMessage());
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
