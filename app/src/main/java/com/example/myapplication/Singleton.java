package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.myapplication.Home.DetailBookActivity;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton {
    private static Singleton instance;
    private Retrofit retrofit = null;
    private  List<BorrowBook> listBookBorrow;
    private List<Book> listbookfavorite;
    public String type;
    public  List<Book> ListFilter;
    private List<Book> listBookmain;

    public Singleton(){

    }
    public static Singleton getInstance(){
        if (instance ==null){
            instance = new Singleton();
        }
        return instance;
    }

    public List<Book> getListFilter(){
        if(ListFilter == null){
            ListFilter = new ArrayList<>();
        }
        return  ListFilter;
    }

    public Retrofit getRetrofit(String baseUrl) {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public void getDetail(Context context, Book book ){
        Intent intent = new Intent(context, DetailBookActivity.class);
        intent.putExtra("id",book.getId());
        intent.putExtra("type",book.getType());
        intent.putExtra("title",book.getTitle());
        intent.putExtra("img",book.getImage());
        intent.putExtra("price",book.getPrice());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public List<Book> getListBook() {
        if (listBookmain == null) {
            listBookmain = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("book");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listBookmain.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Book shop = dataSnapshot.getValue(Book.class);
                        listBookmain.add(shop);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return listBookmain;
    }
}
