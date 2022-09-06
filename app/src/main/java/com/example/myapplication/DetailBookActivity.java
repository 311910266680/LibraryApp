package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailBookActivity  extends AppCompatActivity {
    private ImageView imgD;
    private TextView titleD;
    private  ImageView back2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detailbook);
        back2 = findViewById(R.id.back2);
        titleD = findViewById(R.id.titleDetail);
        imgD = findViewById(R.id.imageDetail);
        Intent get = getIntent();
        String img = get.getStringExtra("img");
        String title = get.getStringExtra("title");
        titleD.setText(title);
        Picasso.get().load(img).into(imgD);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
