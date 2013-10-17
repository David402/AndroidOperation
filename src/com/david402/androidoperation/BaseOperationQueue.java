/**
 * 
 */
package com.david402.androidoperation;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author davidliu
 *
 */
public class BaseOperationQueue implements OperationQueue {
    private final ThreadPoolExecutor mExecutor;
    
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final int DEFAULT_KEEP_ALIVE = 1;

    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "CBAsyncTask #" + counter.incrementAndGet());
        }
    };

    
    public BaseOperationQueue(ThreadPoolExecutor executor) {
        mExecutor = executor;
    }

    /**
     * Should only be called in UI thread
     */
    @Override
    public void add(Operation op) {
        mExecutor.execute(op);
    }

    /**
     * Should only be called in UI thread
     */
    @Override
    public void remove(Operation op) {
        mExecutor.remove(op);
    }

    public static OperationQueue newSerialOperationQueue() {
        return new BaseOperationQueue(new ThreadPoolExecutor(1, 1,
                DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), DEFAULT_THREAD_FACTORY));
    }
    
    public static OperationQueue newBackgroundOperationQueue() {
        return new BaseOperationQueue(new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE,
                DEFAULT_KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), DEFAULT_THREAD_FACTORY));
    }
}
