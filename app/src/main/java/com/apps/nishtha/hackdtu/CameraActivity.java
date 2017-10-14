package com.apps.nishtha.hackdtu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;

public class CameraActivity extends AppCompatActivity {
    Camera camera;
    CameraView cameraView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }

    public void takePic(View view){
        camera.takePicture(null,
                new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {

                    }
                },
                new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {


                        camera.stopPreview();
                        camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
                            @Override
                            public void onPreviewFrame(byte[] bytes, Camera camera) {

                            }
                        });
                        Intent i=new Intent(CameraActivity.this,PresActivity.class );
                        startActivity(i);
                        finish();
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        camera=Camera.open();
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 123);
        }
        if (ActivityCompat.checkSelfPermission(CameraActivity.this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            camera=Camera.open();
            int currentOrientation = getWindowManager().getDefaultDisplay().getRotation();
            switch (currentOrientation) {
                case Surface.ROTATION_0:
                    camera.setDisplayOrientation(90);
                    break;
                case Surface.ROTATION_90:
                    camera.setDisplayOrientation(0);
                    break;
                case Surface.ROTATION_180:
                    camera.setDisplayOrientation(180);
                    break;
                case Surface.ROTATION_270:
                    camera.setDisplayOrientation(180);
                    break;
                default:
                    camera.setDisplayOrientation(180);
            }
        }
        frameLayout = (FrameLayout) findViewById(R.id.container);
        cameraView = new CameraView(this, camera);
        frameLayout.addView(cameraView);
    }
    @Override
    protected void onStop() {
        camera.release();
        frameLayout.removeView(cameraView);

        super.onStop();
    }
}