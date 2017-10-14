package com.apps.nishtha.hackdtu;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by nishtha on 14/10/17.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    Camera c;
    public CameraView(Context context,Camera c) {
        super(context);
        this.c=c;
        SurfaceHolder surfaceHolder=getHolder();
        surfaceHolder.addCallback(this);  // as implemented now
    }

    /*public CameraView(Context context, AttributeSet attrs) {  //REQD IF USNG XML
        super(context, attrs);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }*/


    //can't start preview directly as surface view takes time to render
    //callbacks reqd

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            c.setPreviewDisplay(holder);
//        c.autoFocus(new Camera.AutoFocusCallback() {
//            @Override
//            public void onAutoFocus(boolean success, Camera camera) {
//                Log.d(TAG, "onAutoFocus: "+success);
//            }
////        });
//        c.startFaceDetection();
            c.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //orientatn or multiwindow

        c.stopPreview();
        try {
            c.setPreviewDisplay(holder);
//            c.autoFocus(new Camera.AutoFocusCallback() {
//                @Override
//                public void onAutoFocus(boolean success, Camera camera) {
//                    Log.d(TAG, "onAutoFocus: "+success);
//                }
//            });
//            c.startFaceDetection();
            c.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        c.release();
//        camera.release();  already released in mainActivity
    }
}
