package com.david402.androidoperation;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 
 * @author davidliu
 *
 */
public class Operation extends FutureTask<Object> {
    private Object mResult;

    public Operation(Callable<Object> callable) {
        super(callable);
    }

    public Object getResult() {
        return mResult;
    }
}
