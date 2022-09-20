package com.example.myapplication.ViewModels.Borrow;

import android.widget.TextView;

import com.example.myapplication.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VMBorrowDialog {
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    DatabaseReference DB_USER = FirebaseDatabase.getInstance().getReference("Users");
    public VMBorrowDialog() {
    }
    public void VMUpdateBorrowDialog(String id, String datestart, String date, int duration, int pricetotal, int price, int quantity){

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = df.parse(datestart);
            Date date2 = df.parse(date);
            long tmp1 = Math.abs(date2.getTime() - date1.getTime());
            long tmp2 = TimeUnit.DAYS.convert(tmp1, TimeUnit.MILLISECONDS);

            if(tmp2 == 0.0f){
                tmp2 = 1;
            }
            duration = Math.toIntExact(tmp2);
            if(duration == 0){
                duration = 1;
            }


            pricetotal = price * quantity * duration;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        DB_USER.child(mauth.getUid()).child("borrowbook").child(id).child("count").setValue(quantity);
        DB_USER.child(mauth.getUid()).child("borrowbook").child(id).child("expirationdate").setValue(date);
        DB_USER.child(mauth.getUid()).child("borrowbook").child(id).child("duration").setValue(duration);
        DB_USER.child(mauth.getUid()).child("borrowbook").child(id).child("pricetotal").setValue(pricetotal);
    }

}
