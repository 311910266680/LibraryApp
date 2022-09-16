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

import com.example.myapplication.Constant;
import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.ActivitySearchBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FilterAdapter adapter;
    private List<Book> mListBook;
    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        mListBook = new ArrayList<>();
        binding.recsearch.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilterAdapter(mListBook,this);
        binding.recsearch.setAdapter(adapter);
        binding.ss.addTextChangedListener(new TextWatcher() {
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
        binding.pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setContentView(binding.getRoot());
        Singleton.getInstance().getBookSearch(mListBook,adapter);
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

}