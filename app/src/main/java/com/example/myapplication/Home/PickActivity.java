package com.example.myapplication.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Book;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.ActivityPickBinding;

import java.util.ArrayList;
import java.util.List;


public class PickActivity extends AppCompatActivity {

    private ActivityPickBinding binding;
    private FilterAdapter mFilterAdapter;
    private List<Book> listFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPickBinding.inflate(getLayoutInflater());
        listFilter = new ArrayList<>();
        binding.typeList.setText(Singleton.getInstance().type);
        mFilterAdapter = new FilterAdapter(listFilter, getApplicationContext());
        binding.recFilter.setAdapter(mFilterAdapter);
        LinearLayoutManager hori = new LinearLayoutManager(this);
        binding.recFilter.setLayoutManager(hori);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Singleton.getInstance().getListFilter(listFilter,mFilterAdapter);
        setContentView(binding.getRoot());
    }
}
