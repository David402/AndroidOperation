/**
 * 
 */
package com.david402.androidoperation;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author davidliu
 *
 */
public class BaseOperationQueue implements OperationQueue {
    private final ThreadPoolExecutor mExecutor;
    
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
}
