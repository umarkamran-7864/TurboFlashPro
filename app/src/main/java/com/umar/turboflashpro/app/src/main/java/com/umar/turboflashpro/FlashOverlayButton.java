package com.umar.turboflashpro;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class FlashOverlayButton {
    static boolean flashOn = false;

    public static void show(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View floatView = LayoutInflater.from(context).inflate(R.layout.flash_button, null);
        ImageView flashBtn = floatView.findViewById(R.id.flash_toggle);

        flashBtn.setOnClickListener(v -> {
            flashOn = !flashOn;
            toggleFrontFlash(context, flashOn);
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                200, 200,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;

        wm.addView(floatView, params);
    }

    private static void toggleFrontFlash(Context context, boolean state) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String id : cameraManager.getCameraIdList()) {
                if (cameraManager.getCameraCharacteristics(id)
                        .get(android.hardware.camera2.CameraCharacteristics.LENS_FACING)
                        == android.hardware.camera2.CameraCharacteristics.LENS_FACING_FRONT) {
                    cameraManager.setTorchMode(id, state);
                    break;
                }
            }
        } catch (CameraAccessException e) {
            Toast.makeText(context, "Flash error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
