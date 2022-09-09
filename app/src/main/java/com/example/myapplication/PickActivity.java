package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PickActivity extends AppCompatActivity {
    private FilterAdapter mFilterAdapter;
    private RecyclerView rcvFilter;
    private ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        rcvFilter = findViewById(R.id.recFilter);
        back = findViewById(R.id.back);
        mFilterAdapter = new FilterAdapter(Singleton.getInstance().ListFilter, getApplicationContext());
        rcvFilter.setAdapter(mFilterAdapter);
        LinearLayoutManager hori = new LinearLayoutManager(this);
        rcvFilter.setLayoutManager(hori);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
