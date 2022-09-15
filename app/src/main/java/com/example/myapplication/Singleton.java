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
    private static Retrofit retrofit = null;
    public List<BorrowBook> listBookBorrow;
    public List<Book> listbookfavorite;
    public String type;
    public  List<Book> ListBook, ListFilter;

    public List<Book> listBookmain;

    public Singleton(){
        if (ListBook==null|| ListFilter==null){
            ListBook = new ArrayList<>();
            ListFilter = new ArrayList<>();
        }
    }
    public  Retrofit getRetrofit(String baseUrl) {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Singleton getInstance(){
        if (instance==null){
            instance = new Singleton();
        }
        return instance;
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




}
