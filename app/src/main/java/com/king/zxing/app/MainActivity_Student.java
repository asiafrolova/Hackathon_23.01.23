package com.king.zxing.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.king.zxing.app.databinding.ActivityMainStudentBinding;

public class MainActivity_Student extends AppCompatActivity {
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_QR_CODE = "key_code";

    private ActivityMainStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);

        binding= ActivityMainStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGenerateQR.setOnClickListener(v -> {
            String login1=getIntent().getStringExtra("login");
            startGenerateCodeActivity(true, "My School",login1);
        });
    }
    private void startGenerateCodeActivity(boolean isQRCode, String title,String login1) {
        Intent intent = new Intent(this, CodeActivity.class);
        intent.putExtra(KEY_IS_QR_CODE, isQRCode);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra("login", login1);
        startActivity(intent);
    }
}