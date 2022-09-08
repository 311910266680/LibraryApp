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
import com.example.myapplication.Model.Book;
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
    int a = 1;


    private String img, title, type;
    private int id,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityDetailbookBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent get = getIntent();

        img = get.getStringExtra("img");
        type = get.getStringExtra("type");
        title = get.getStringExtra("title");
        price = get.getIntExtra("price",1);
        id = get.getIntExtra("id",1);

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
                        addtolistfavorite();
                        a = 0;
                        break;
                    case 0 :
                        binding.btnheart.setBackgroundResource(R.drawable.ic_heartwhite);
                        removefavorite();
                        a = 1;
                        break;
                }

            }
        });

    }

    @Override
    public void ClickQuantityBorrow(int quantity, String date) {
        Date currentday = Calendar.getInstance().getTime();
        Singleton.getListBookBorrow().add(new BorrowBook(img,title,quantity,String.valueOf(currentday),date,price * quantity));
        Toast.makeText(getApplicationContext(),"Add sucessfull: " +title,Toast.LENGTH_LONG).show();
    }
    public void addtolistfavorite(){
        Book book = new Book(id,img,title,type,price);

        if(Singleton.getListbookfavorite().size() == 0){
            Singleton.getListbookfavorite().add(book);
            Toast.makeText(getApplicationContext(),"Add sucessfull"+book.getTitle(),Toast.LENGTH_LONG).show();
        }
        else {
            for(int i = 0; i< Singleton.getListbookfavorite().size(); i++){
                if(Singleton.getListbookfavorite().get(i).getId() == book.getId()){
                    Singleton.getListbookfavorite().remove(Singleton.getListbookfavorite().get(i));
                }
            }
            Singleton.getListbookfavorite().add(book);
            Toast.makeText(getApplicationContext(),"Add sucessfull"+book.getTitle(),Toast.LENGTH_LONG).show();
        }

    }
    public void removefavorite(){
        for(int i = 0; i< Singleton.getListbookfavorite().size(); i++){
            if(Singleton.getListbookfavorite().get(i).getId() == id){
                Singleton.getListbookfavorite().remove(Singleton.getListbookfavorite().get(i));
            }
        }
        Toast.makeText(getApplicationContext(),"Remove sucessfull"+title,Toast.LENGTH_LONG).show();
    }
}
