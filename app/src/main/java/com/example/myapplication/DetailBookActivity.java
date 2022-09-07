package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.Borrow.BorrowBottomsheet;
import com.example.myapplication.Borrow.BorrowFrag;
import com.example.myapplication.Borrow.CLickQuantity;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.databinding.ActivityDetailbookBinding;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailBookActivity  extends AppCompatActivity implements CLickQuantity {
    private ImageView imgD;
    private TextView titleD;
    private  ImageView back2;


    private ActivityDetailbookBinding binding;
    int a;


    private String img, title;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityDetailbookBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent get = getIntent();
        img = get.getStringExtra("img");
        title = get.getStringExtra("title");
        price = get.getIntExtra("price",1);
        binding.titleDetail.setText(title);
        binding.tvprice.setText(String.valueOf(price));
        Picasso.get().load(img).into(binding.imageDetail);
        binding.back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnborrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrowBottomsheet borrowBottomsheet = new BorrowBottomsheet(DetailBookActivity.this);
                borrowBottomsheet.show(getSupportFragmentManager(),"show bottomsheet borrow");

            }
        });
        binding.btnheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (a){
                    case 1:
                        binding.btnheart.setBackgroundResource(R.drawable.img_heart_red);
                        a = 0;
                        break;
                    case 0 :
                        binding.btnheart.setBackgroundResource(R.drawable.ic_heartwhite);
                        a = 1;
                        break;
                }

            }
        });

    }

    @Override
    public void ClickQuantityBorrow(int quantity) {
        Date currentday = Calendar.getInstance().getTime();
        Singleton.getListBookBorrow().add(new BorrowBook(img,title,quantity,String.valueOf(currentday),String.valueOf(currentday),price * quantity));
        Toast.makeText(getApplicationContext(),"Add sucessfull: " +title,Toast.LENGTH_LONG).show();
    }
}
