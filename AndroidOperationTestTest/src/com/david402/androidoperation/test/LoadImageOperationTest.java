package com.david402.androidoperation.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.david402.androidoperation.Operation;
import com.david402.androidoperation.OperationQueue;
import com.david402.androidoperation.OperationQueueFactory;

public class LoadImageOperationTest extends AndroidTestCase {

    private static final String TAG = "LoadImageOperationTest";

    public void testLoadLocalImage() {
        OperationQueue queue = OperationQueueFactory.newBackgroundOperationQueue();
        Log.d(TAG, "Test loading local image");
  
        Operation<Bitmap> op = new Operation<Bitmap>(new Callable<Bitmap>() {
            @Override
            public Bitmap call() {
                String path = "/storage/emulated/0/Pictures/PicCollage for au/Collage 2013-10-30 10_47_14.png";
                Uri uri = Uri.fromFile(new File(path));
              
                // Load image
                InputStream is = null;
                Bitmap bmp = null;
                try {
                    is = getContext().getContentResolver().openInputStream(uri);
                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inPreferredConfig = Bitmap.Config.RGB_565;
                    o.inSampleSize = 1;
                    o.inDither = false;
                    bmp = BitmapFactory.decodeStream(is, null, o);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null)
                        try {
                            is.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                }
                Log.d(TAG, "bmp: " + bmp);
                return bmp;
            }
        });
        queue.add(op);
        try {
            assertTrue(op.get() != null);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
