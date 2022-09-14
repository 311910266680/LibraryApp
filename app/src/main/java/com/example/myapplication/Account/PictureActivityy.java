package com.example.myapplication.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.databinding.ActivityPictureActivityyBinding;

public class PictureActivityy extends AppCompatActivity {

    private ActivityPictureActivityyBinding binding;
    private String image;
    private Uri myuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPictureActivityyBinding.inflate(getLayoutInflater());

        image = getIntent().getStringExtra("img");
        Uri myuri = Uri.parse(image);
        Log.e("HH", String.valueOf(myuri));

        binding.imgpic.setImageURI(myuri);
        binding.btupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("imgresult",String.valueOf(myuri));
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });
        binding.btdiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setContentView(binding.getRoot());
    }
}