package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constant {
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference DB_BOOK = firebaseDatabase.getReference("book");
    public static DatabaseReference DB_DISCOUNT = firebaseDatabase.getReference("discountcode");
    public static DatabaseReference DB_ORDER = firebaseDatabase.getReference("Order");
    public static DatabaseReference DB_TYPE = firebaseDatabase.getReference("type");

}
