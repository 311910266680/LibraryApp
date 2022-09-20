package com.example.myapplication.ViewModels.Borrow;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VMBorrowActivityOrder {
    public static void deletelistborrow(){
        FirebaseAuth mauth = FirebaseAuth.getInstance();
        DatabaseReference DB_USER = FirebaseDatabase.getInstance().getReference("Users");
        DB_USER.child(mauth.getUid()).child("borrowbook").removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("TAG",String.valueOf("SUCESS"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG",String.valueOf("FAIL"));
                    }});
    }
}
