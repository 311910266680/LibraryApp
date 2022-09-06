package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton instance;
    public  List<Book> ListBook, ListFilter;
    public Singleton(){
        if (ListBook==null|| ListFilter==null){
            ListBook = new ArrayList<>();
            ListFilter = new ArrayList<>();
        }
    }
    public static Singleton getInstance(){
        if (instance==null){
            instance = new Singleton();
        }
        return instance;
    }
    public void getDetail(Context context, Book book ){
        Intent intent = new Intent(context, DetailBookActivity.class);
        intent.putExtra("title",book.getTitle());
        intent.putExtra("img",book.getImage());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
