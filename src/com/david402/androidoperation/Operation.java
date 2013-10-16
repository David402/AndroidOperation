package com.david402.androidoperation;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 
 * @author davidliu
 *
 */
public class Operation extends FutureTask<OperationResult> {

    public Operation(Callable<OperationResult> callable) {
        super(callable);
        // TODO Auto-generated constructor stub
    }

}
