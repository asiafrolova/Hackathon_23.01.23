package com.king.zxing.app;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.king.zxing.app.databinding.ActivityMainRegBinding;


public class MainActivity_reg extends AppCompatActivity {

    private ActivityMainRegBinding binding;
    private String login;
    private String password;
    private String role;
    private DatabaseReference myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnReg.setOnClickListener(v -> {
            String login = binding.login.getText().toString();
            String password = binding.password.getText().toString();
            String role = binding.role.getText().toString();

            myDatabase = FirebaseDatabase.getInstance().getReference();

            User user = new User( login, password, role);
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(role)) {
                myDatabase.push().setValue(user);
                Snackbar.make(binding.getRoot(), "Регистрация успешна !", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.getRoot(), "Недостаточно данных!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}


