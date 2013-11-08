/**
 * 
 */
package com.david402.androidoperation;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author davidliu
 *
 */
public class OperationQueueImpl implements OperationQueue {
    private final ThreadPoolExecutor mExecutor;
    
    public OperationQueueImpl(ThreadPoolExecutor executor) {
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
}
