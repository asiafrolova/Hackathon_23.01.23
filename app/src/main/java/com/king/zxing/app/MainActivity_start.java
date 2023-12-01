package com.king.zxing.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.king.zxing.app.databinding.ActivityMainStartBinding;

public class MainActivity_start extends AppCompatActivity {
    private DatabaseReference myDatabase;

    private ActivityMainStartBinding binding;
    private String true_login="admin";
    private String true_password="admin";
    private String role1;

    public boolean answer=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        binding=ActivityMainStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDatabase = FirebaseDatabase.getInstance().getReference();

        binding.btnEntry.setOnClickListener(v -> {
            String login1= binding.login.getText().toString();
            String password1= binding.password.getText().toString();

            User this_user=new User(login1,password1,"none");

            boolean c=getDataFromDB(this_user);
            if(TextUtils.isEmpty(login1) || TextUtils.isEmpty(password1)){
                Snackbar.make(binding.getRoot(),"Недостаточно данных! ",Snackbar.LENGTH_SHORT).show();
            }
            else if((true_password.equals(password1))&& (true_login.equals(login1))){
                Intent i=new Intent(this, MainActivity_reg.class);
                startActivity(i);

            }
            else if(c){
                if("guard".equals(role1)){
                    Intent intent=new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else if("student".equals(role1) || "teacher".equals(role1)){
                    Intent intent=new Intent(this, MainActivity_Student.class);
                    intent.putExtra("login",login1);
                    startActivity(intent);
                }

            }
            else{
                Snackbar.make(binding.getRoot(),"Неверный логин или пароль! ",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private boolean getDataFromDB(User user_this){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                    User user=ds.getValue(User.class);
                    if(user.matching(user_this,user)){
                        answer=true;
                        role1=user.role;

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myDatabase.addValueEventListener((vListener));
        return answer;
    }


}

