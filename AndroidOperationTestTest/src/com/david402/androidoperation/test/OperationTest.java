package com.david402.androidoperation.test;

import java.util.concurrent.Callable;

import junit.framework.TestCase;
import android.util.Log;

import com.david402.androidoperation.Operation;
import com.david402.androidoperation.Operation.OperationListener;
import com.david402.androidoperation.OperationQueue;
import com.david402.androidoperation.OperationQueueFactory;

public class OperationTest extends TestCase {

    private final static String TAG = "Android Operation Test";
    
    public OperationTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
        
    public void testConcurrentQueue() {
        OperationQueue queue = OperationQueueFactory.newBackgroundOperationQueue();
        System.out.print("[1]");
        for (int i = 0; i < 100; i++) {
            final int outInt = i;
            queue.add(new Operation<Object>(new Callable<Object>() {
                @Override
                public Object call() {
                    System.out.print(outInt + " ");
                    return Integer.valueOf(outInt);
                }
            }));
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.print("\n");
        assertTrue(true);
    }

    public void testSerialQueue() {
        OperationQueue queue = OperationQueueFactory.newSerialOperationQueue();
        System.out.print("[2]");
        for (int i = 0; i < 100; i++) {
            final int outInt = i;
            queue.add(new Operation<Object>(new Callable<Object>() {
                @Override
                public Object call() {
                    System.out.print(outInt + " ");
                    return Integer.valueOf(outInt);
                }
            }));
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.print("\n");
        assertTrue(true);
    }
    
    public void testListenerOfOperation() {
        OperationQueue queue = OperationQueueFactory.newSerialOperationQueue();
        Operation<String> op = new Operation<String>(new Callable<String>() {
            @Override
            public String call() {
                return new String("[OP Result] operation finished successfully.");
            }
        });
        op.setListener(new OperationListener<String>() {
            @Override
            public void callback(String result, Exception e) {
                if (e != null) {
                    Log.d(TAG, "[OP listener] exception: " + e);
                    return;
                }
                
                // Check the result should not be 'null'
                assertTrue(result != null);
                
                Log.d(TAG, "[OP listener] result: " + result);
            }
        });
        queue.add(op);
    }
    
    public void testListenerOfCancelledOperation() {
        OperationQueue queue = OperationQueueFactory.defaultBackgroundQueue();
        final Operation<String> infiniteOp = new Operation<String>(new Callable<String>() {
            @Override
            public String call() {
                // Block this operation to finish and wait for cancellation
                while (true) {
                    
                }
            }
        });
        infiniteOp.setListener(new OperationListener<String>() {
            @Override
            public void callback(String result, Exception e) {
                if (e != null) {
                    Log.d(TAG, "[OP listener] exception: " + e);
                    return;
                }
                
                // Shouldn't enter here
                Log.d(TAG, "[OP listener] result: " + result);
            }
        });
        queue.add(infiniteOp);
        Operation<Void> cancelOp = new Operation<Void>(new Callable<Void>() {
            @Override
            public Void call() {
                // Cancel `infinitOp`
                Log.d(TAG, "Trying to cancel `infiniteOp`");
                boolean status = infiniteOp.cancel(true);
                Log.d(TAG, "Cancel `infiniteOp` result: " + status);
                return null;
            }
        });
        queue.add(cancelOp);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.print("\n");
    }
}
