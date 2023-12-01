
package com.king.zxing.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CodeActivity extends AppCompatActivity {

    private TextView tvTitle;

    private TextView tvBarcodeFormat;
    private ImageView ivCode;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_activity);
        ivCode = findViewById(R.id.ivCode);
        tvTitle = findViewById(R.id.tvTitle);
        tvBarcodeFormat = findViewById(R.id.tvBarcodeFormat);
        tvTitle.setText(getIntent().getStringExtra(MainActivity.KEY_TITLE));
        boolean isQRCode = getIntent().getBooleanExtra(MainActivity.KEY_IS_QR_CODE,false);
        String login1=getIntent().getStringExtra("login");
        if(isQRCode){
            tvBarcodeFormat.setText("BarcodeFormat: QR_CODE");
            createQRCode(login1);
        }else{
            tvBarcodeFormat.setText("BarcodeFormat: CODE_128");
            createBarCode("1234567890");
        }
    }


    private void createQRCode(String content){
        executor.execute(() -> {

            Bitmap logo = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
            Bitmap bitmap =  CodeUtils.createQRCode(content,600,logo);
            runOnUiThread(()->{

                ivCode.setImageBitmap(bitmap);
            });
        });

    }

    private void createBarCode(String content){
        executor.execute(() -> {

            Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_128,800,200,null,true);
            runOnUiThread(()->{

                ivCode.setImageBitmap(bitmap);
            });
        });
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivLeft:
                finish();
                break;
        }
    }
}
