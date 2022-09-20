package com.example.myapplication;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.myapplication.Borrow.BorrowAdapter;
import com.example.myapplication.Home.BookAdapter;
import com.example.myapplication.Home.DetailBookActivity;
import com.example.myapplication.Home.FilterAdapter;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Order;
import com.google.firebase.auth.FirebaseAuth;
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
    public String type;
    private List<Book> listBookmain;
    public List<BorrowBook> listborrow;

    public Singleton(){
    }
    public static Singleton getInstance(){
        if (instance ==null){
            instance = new Singleton();
        }
        return instance;
    }


    public void getListFilter(List<Book>ListFilter, FilterAdapter filterAdapter){
        Constant.DB_BOOK.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListFilter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book shop = dataSnapshot.getValue(Book.class);
                    if (shop.getType().equals(type)){
                        ListFilter.add(shop);
                    }

                }
                filterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            Constant.DB_BOOK.addValueEventListener(new ValueEventListener() {
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
    public void getBookAvailable(List<Book> list, BookAdapter bookAdapter) {
        Constant.DB_BOOK.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book shop = dataSnapshot.getValue(Book.class);
                    if (shop.getQuantity()!=0){
                        list.add(shop);
                    }
                }
                bookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public List<BorrowBook> getlistborrow(){
        if(listborrow == null){
            listborrow = new ArrayList<>();
        }
        return listborrow;
    }




    public void getBorrowed(List<Order> listOrder,List<BorrowBook> listBorrowed, List<Book>listBor,BookAdapter mBookBorrowedAdapter) {
        FirebaseAuth mauth = FirebaseAuth.getInstance();
         Constant.DB_ORDER.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOrder.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order oder = dataSnapshot.getValue(Order.class);
                    listOrder.add(oder);
                }
                listBorrowed.clear();
                for (Order order: listOrder){
                    if (order.getIduser().equals(mauth.getUid())){
                        listBorrowed.addAll(order.getBorrowbook());
                    }
                }
                Constant.DB_BOOK.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listBor.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Book item = dataSnapshot.getValue(Book.class);
                            for(BorrowBook a : listBorrowed) {
                                if (a.getBookid()==item.getId()){
                                    listBor.add(item);
                                }

                            }


                        }  mBookBorrowedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    public void getBookSearch(List<Book>mListBook, FilterAdapter adapter ) {
        Constant.DB_BOOK.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListBook.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book shop = dataSnapshot.getValue(Book.class);
                    mListBook.add(shop);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
