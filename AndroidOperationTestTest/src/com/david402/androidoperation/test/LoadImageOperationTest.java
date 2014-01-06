package com.david402.androidoperation.test;

import android.test.AndroidTestCase;

public class LoadImageOperationTest extends AndroidTestCase {

    private static final String TAG = "LoadImageOperationTest";

    public void testLoadLocalImage() {
//        OperationQueue queue = OperationQueueFactory.newBackgroundOperationQueue();
//        Log.d(TAG, "Test loading local image");
//  
//        Operation<Bitmap> op = new Operation<Bitmap>(new Callable<Bitmap>() {
//            @Override
//            public Bitmap call() {
//                String path = "/storage/emulated/0/Pictures/PicCollage for au/Collage 2013-10-30 10_47_14.png";
//                Uri uri = Uri.fromFile(new File(path));
//              
//                // Load image
//                InputStream is = null;
//                Bitmap bmp = null;
//                try {
//                    is = getContext().getContentResolver().openInputStream(uri);
//                    BitmapFactory.Options o = new BitmapFactory.Options();
//                    o.inPreferredConfig = Bitmap.Config.RGB_565;
//                    o.inSampleSize = 1;
//                    o.inDither = false;
//                    bmp = BitmapFactory.decodeStream(is, null, o);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (is != null)
//                        try {
//                            is.close();
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                }
//                Log.d(TAG, "bmp: " + bmp);
//                return bmp;
//            }
//        });
//        queue.add(op);
//        try {
//            assertTrue(op.get() != null);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
