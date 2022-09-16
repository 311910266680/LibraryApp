package com.example.myapplication.ViewModels.Home;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class VMHomeDetailBook {

    public VMHomeDetailBook() {
    }
    public void addToFavorite(Context context, String bookId  ){
        HashMap<String, Object> hashMap =new HashMap<>();
        hashMap.put("bookId",""+bookId);

        Constant.DB_USER.child(Constant.ID_USER).child("favorite").child(bookId).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void removeFromFavorite(Context context, String bookId){
        Constant.DB_USER.child(Constant.ID_USER).child("favorite").child(bookId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Remove from your favorite", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to remove from your favorite"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
