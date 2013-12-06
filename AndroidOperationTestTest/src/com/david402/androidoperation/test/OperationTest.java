package com.david402.androidoperation.test;

import java.util.concurrent.Callable;

import junit.framework.TestCase;

import com.david402.androidoperation.Operation;
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
}
