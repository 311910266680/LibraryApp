package com.example.myapplication.ViewModels.Borrow;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class VMBorrowActivityOrder {
    public static void deletelistborrow(){
        Constant.DB_USER.child(Constant.ID_USER).child("borrow").removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG",String.valueOf(e.getMessage()));
                    }
                });
    }
}
