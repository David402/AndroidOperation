package com.david402.androidoperation;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.os.Looper;

/**
 * 
 * @author davidliu
 * @param <T>
 *
 */
public class Operation<T> extends FutureTask<T> {
    private String mName;
    private Object mResult;
    private OperationListener<T> mListener;
    private final ArrayList<Operation<?>> mDependentOps = 
            new ArrayList<Operation<?>>();

    public static interface OperationListener<T> {
        public void callback(T result, Exception e);
    }
    
    public Operation(Callable<T> callable) {
        super(callable);
    }

    public Object getResult() {
        return mResult;
    }
    
    public void setName(final String name) {
        mName = name;
    }
    
    public void setListener(OperationListener<T> listener) {
        mListener = listener;
    }
 
    public void addDependency(Operation<?> operation) {
        if (operation == null) return;
        mDependentOps.add(operation);
    }
    
    public void addDependency(Operation<?>...operations) {
        if (operations == null) return;
        for (Operation<?> op : operations) {
            mDependentOps.add(op);
        }
    }
    
    @Override
    public void run() {
        // Wait for all dependent task complete
        for (Operation<?> depTask : mDependentOps) {
            try {
                depTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        // Prepare looper
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        
        // Run self's task
        super.run();
    }
}
