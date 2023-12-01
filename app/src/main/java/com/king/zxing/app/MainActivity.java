
package com.king.zxing.app;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.king.camera.scan.CameraScan;
import com.king.camera.scan.util.LogUtils;
import com.king.camera.scan.util.PermissionUtils;
import com.king.zxing.util.CodeUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;


public class MainActivity extends AppCompatActivity {

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_QR_CODE = "key_code";

    public static final int REQUEST_CODE_SCAN = 0x01;
    public static final int REQUEST_CODE_PHOTO = 0x02;

    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 0x99;

    private Toast toast;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guard);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
                    showToast(result);
                    break;
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }

        }
    }

    private void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);
        }
        toast.show();
    }

    private void parsePhoto(Intent data) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            asyncThread(() -> {
                final String result = CodeUtils.parseCode(bitmap);
                runOnUiThread(() -> {
                    LogUtils.d("result:" + result);
                    showToast(result);
                });

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE && PermissionUtils.requestPermissionsResult(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                permissions,
                grantResults)) {
            startPickPhoto();
        }
    }

    private void asyncThread(Runnable runnable) {
        executor.execute(runnable);
    }

    private void startScan(Class<?> cls) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, cls);
        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
    }


    private void startPickPhoto() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnQRCode:
                startScan(QRCodeScanActivity.class);
                break;

        }

    }
}
