package com.example.myapplication.ViewModels.Borrow;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import com.example.myapplication.Borrow.BorrowAdapter;
import com.example.myapplication.Constant;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Discount;
import com.example.myapplication.Singleton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VMBorrowFrag {

    public VMBorrowFrag() {

    }

    public void addBookTolistBorrow(List<BorrowBook> listborrowbook){

        for(int i = 0; i< Singleton.getInstance().getListBook().size(); i++){
            for(int j = 0; j<listborrowbook.size(); j++){
                if(listborrowbook.get(j).getBookid() == Singleton.getInstance().getListBook().get(i).getId()){
                    listborrowbook.get(j).setBook(Singleton.getInstance().getListBook().get(i));
                }
            }
        }
    }

    public void getdiscount(List<Discount> discountslist) {
        Constant.DB_DISCOUNT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                discountslist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Discount discount = dataSnapshot.getValue(Discount.class);
                    discountslist.add(discount);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void deleteBorrowBook(String id, Context context){
        Constant.DB_USER.child(Constant.ID_USER).child("borrowbook").child(id).removeValue()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context,"Delete ok", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Delete fail", Toast.LENGTH_SHORT).show();
                }
            });
    }

}
