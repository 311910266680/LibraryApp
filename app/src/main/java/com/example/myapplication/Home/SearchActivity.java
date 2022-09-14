package com.example.myapplication.Home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FilterAdapter adapter;
    EditText ss;
    ImageView back;
    List<Book> mListBook;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mListBook = new ArrayList<>();

        getBook();
        recyclerView = findViewById(R.id.recsearch);
        ss = findViewById(R.id.ss);
        back = findViewById(R.id.pre);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilterAdapter(mListBook,this);
        recyclerView.setAdapter(adapter);
        ss.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void filter(String text){
        List<Book> itemSearchList = new ArrayList<>();
        for(Book itemSearch : mListBook){
            if(itemSearch.getTitle().toLowerCase().contains(text.toLowerCase())){
                itemSearchList.add(itemSearch);
            }
        }
        adapter.setmListFilter(itemSearchList);
    }
    private void getBook() {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("book");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListBook.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book shop = dataSnapshot.getValue(Book.class);
                    mListBook.add(shop);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}