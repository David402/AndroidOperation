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
    private boolean mIsCascadeCancel = false;
    /**
     * Queue of dependent operations - All the dependent operations should 
     * be done before this operation being executed.
     */
    private final ArrayList<Operation<?>> mDependentOps = 
            new ArrayList<Operation<?>>();
    /**
     * Queue of following operations - Following operations needs to be
     * executed after this operation is done. If this operation is cancelled,
     * all the following operations should be cancelled as well if `cascadeCancel`
     * flag is `true`.
     */
    private final ArrayList<Operation<?>> mFollowingOps = 
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
    
    public boolean isCascadeCancel() {
        return mIsCascadeCancel;
    }
    
    public void setCascadeCancel(boolean isCascadeCancel) {
        mIsCascadeCancel = isCascadeCancel;
    }
 
    public void addDependency(Operation<?> operation) {
        if (operation == null) return;
        mDependentOps.add(operation);
        operation.addFollowingOp(this);
    }
    
    public void addDependency(Operation<?>...operations) {
        if (operations == null) return;
        for (Operation<?> op : operations) {
            mDependentOps.add(op);
            op.addFollowingOp(this);
        }
    }
    
    public void addFollowingOp(Operation<?> operation) {
        if (operation == null) return;
        mFollowingOps.add(operation);
    }
    
    /**
     * Override {@link #cancel(boolean)} to cascade cancel following operations
     * if {@link #isCascadeCancel()} is true.
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (isCascadeCancel()) {
            for (Operation<?> op : mFollowingOps) {
                op.cancel(mayInterruptIfRunning);
            }
        }
        return super.cancel(mayInterruptIfRunning);
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
        Exception exception = null;
        try {
            super.run();
        } catch (Exception e) {
            exception = e;
        }
        
        if (mListener != null) {
            try {
                mListener.callback(get(), exception);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
