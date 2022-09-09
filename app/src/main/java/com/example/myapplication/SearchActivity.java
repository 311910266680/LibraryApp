package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FilterAdapter adapter;
    EditText ss;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recsearch);
        ss = findViewById(R.id.ss);
        back = findViewById(R.id.pre);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilterAdapter(Singleton.getInstance().ListBook,this);
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
        for(Book itemSearch : Singleton.getInstance().ListBook){
            if(itemSearch.getTitle().toLowerCase().contains(text.toLowerCase())){
                itemSearchList.add(itemSearch);
            }
        }
        adapter.setmListFilter(itemSearchList);
    }
}