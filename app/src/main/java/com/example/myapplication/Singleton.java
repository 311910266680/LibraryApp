package com.example.myapplication;

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
}
