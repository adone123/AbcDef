package com.abc.packagelibrary;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class BitmapUtil {

        public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
            try {
                return Bitmap.createBitmap(width, height, config);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                if (retryCount > 0) {
                    System.gc();
                    return createBitmapSafely(width, height, config, retryCount - 1);
                }
                return null;
            }
        }

        public static Bitmap createBitmapFromView(View view) {
            view.clearFocus();
            Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
            if (bitmap != null) {
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
            return bitmap;
        }
    }


