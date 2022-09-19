package com.example.myapplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Constant;
import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mauth;
    private FirebaseUser firebaseUser;
    private  String name,age,password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        mauth = FirebaseAuth.getInstance();
        firebaseUser = mauth.getCurrentUser();
        binding.btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        binding.btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setContentView(binding.getRoot());
    }
    private void register(){
        name = binding.edtname.getText().toString().trim();
        age = binding.edtage.getText().toString().trim();
        email = binding.edtemail.getText().toString().trim();
        password = binding.edtpassword.getText().toString().trim();

        if(name.isEmpty()){
            binding.edtname.setError("Full Name is required");
        }
        if(age.isEmpty()){
            binding.edtage.setError("Age is required");
        }
        if(email.isEmpty()){
            binding.edtemail.setError("Email is required");
        }
        if(password.isEmpty()){
            binding.edtpassword.setError("Password is required");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtemail.setError("Please provide valid email");
        }
        if(password.length() <6){
            binding.edtpassword.setError("Min password lenght should be 6 character!");
        }
        else {
            mauth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            createuser();
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                    });
        }

    }
    private void createuser(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", mauth.getUid());
        hashMap.put("name",name);
        hashMap.put("password",password);
        hashMap.put("email",email);
        hashMap.put("profileImage","");
        Constant.DB_USER.child(mauth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterActivity.this,"Regis sucessfull", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,"Regis Fail !!!", Toast.LENGTH_LONG).show();
            }
        });
    }

}