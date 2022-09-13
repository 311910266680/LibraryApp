package com.example.myapplication.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth = FirebaseAuth.getInstance();
        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        binding.tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        checkUser();
        Log.e("das", "onCreate: "+mAuth );
        setContentView(binding.getRoot());
    }
    private void login(){
        String email = binding.edtusername.getText().toString().trim();
        String password = binding.edtpassword.getText().toString().trim();
        if(email.isEmpty()){
            binding.edtusername.setError("Please enter email");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtusername.setError("Please provide valid email");
        }
        if (password.isEmpty()){
            binding.edtpassword.setError("Please enter password");
        }
        else {
            progressDialog.setMessage("Logging in...");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failt", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void checkUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser==null){


        }
        else{ progressDialog.setMessage("Logging in...");
            progressDialog.show();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}