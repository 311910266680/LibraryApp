package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    private static Singleton instance;
    private static List<BorrowBook> listBookBorrow;
    private static List<Book> listbookfavorite;

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
        intent.putExtra("id",book.getId());
        intent.putExtra("type",book.getType());
        intent.putExtra("title",book.getTitle());
        intent.putExtra("img",book.getImage());
        intent.putExtra("price",book.getPrice());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static List<BorrowBook> getListBookBorrow(){
        if(listBookBorrow == null){
            listBookBorrow = new ArrayList<>();
        }
        return  listBookBorrow;
    }
    public static List<Book> getListbookfavorite(){
        if(listbookfavorite == null){
            listbookfavorite = new ArrayList<>();
        }
        return  listbookfavorite;
    }

}
