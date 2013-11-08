package com.david402.androidoperation;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class OperationQueueFactory {
        
    private static OperationQueue mDefaultSerialQueue;
    private static OperationQueue mDefaultBackgroundQueue;
    
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final int DEFAULT_KEEP_ALIVE = 1;

    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AndroidOperation #" + counter.incrementAndGet());
        }
    };
    
    /**
     * If you need a generic queue to run operations serially, 
     * call this method to get the default serial operation queue.
     * 
     * The identical queue will be returned no matter how many times
     * this method is called.
     * 
     * @return default serial queue
     */
    public static synchronized OperationQueue defaultSerialQueue() {
        if (mDefaultSerialQueue == null) {
            mDefaultSerialQueue = newSerialOperationQueue();
        }
        return mDefaultSerialQueue;
    }
    
    /**
     * If you need a generic queue to run operations concurrently,
     * call this method to get the default background operation queue.
     * All operations added to this queue will be executed concurrently
     * with default thread pool configuration.
     * 
     * The identical queue will be returned no matter how many times 
     * this method is called.
     * 
     * @return default background operation queue
     */
    public static synchronized OperationQueue defaultBackgroundQueue() {
        if (mDefaultBackgroundQueue == null) {
            mDefaultBackgroundQueue = newBackgroundOperationQueue();
        }
        return mDefaultBackgroundQueue;
    }
    
    /**
     * Create a new serial operation queue with default configured
     * thread pool, caller is responsible to manage the queue for its 
     * own usage.
     * 
     * @return a new serial operation queue
     */
    public static OperationQueue newSerialOperationQueue() {
        
        return new BaseOperationQueue(new ThreadPoolExecutor(1, 1,
                DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, 
                new LinkedBlockingQueue<Runnable>(), 
                DEFAULT_THREAD_FACTORY));
    }
    
    /**
     * Create a new background operation queue with default configured
     * thread pool, caller is responsible to manage the queue for its 
     * own usage.
     * 
     * @return a new background operation queue
     */
    public static OperationQueue newBackgroundOperationQueue() {
        
        return newBackgroundOperationQueue(DEFAULT_CORE_POOL_SIZE, 
                DEFAULT_MAXIMUM_POOL_SIZE);
    }
    

    /**
     * Create a new background operation queue with specified thread 
     * pool configuration, caller is responsible to manage the queue
     * for its own usage.
     * 
     * @param defaultPoolSize initial pool size
     * @param maxPoolSize max pool size
     * @return a new background operation queue with specified thread
     * pool configuration
     */
    public static OperationQueue newBackgroundOperationQueue(
            int defaultPoolSize, 
            int maxPoolSize) {
        
        return new BaseOperationQueue(new ThreadPoolExecutor(
                defaultPoolSize, maxPoolSize,
                DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, 
                new LinkedBlockingQueue<Runnable>(), 
                DEFAULT_THREAD_FACTORY));
    }
}
