package com.example.myapplication.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Borrow.BorrowBottomsheet;
import com.example.myapplication.Borrow.CLickQuantity;
import com.example.myapplication.Constant;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.R;
import com.example.myapplication.ViewModels.Home.VMHomeDetailBook;
import com.example.myapplication.databinding.ActivityDetailbookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DetailBookActivity  extends AppCompatActivity implements CLickQuantity {
    private ActivityDetailbookBinding binding;
    private List<Book> mListBook,listSimilar;
    private String img, title, author,type;
    private int id,price, dayconvert;
    private long dateeee,dayys;
    boolean isInMyFavorite = false;
    private BookAdapter adapterSimilar;
    private FirebaseAuth mauth;
    private DatabaseReference DB_USER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBook();

        binding = ActivityDetailbookBinding.inflate(getLayoutInflater());
        mListBook = new ArrayList<>();
        VMHomeDetailBook vmHomeDetailBook = new VMHomeDetailBook();
        listSimilar= new ArrayList<>();
        id = getIntent().getIntExtra("id",1);
        mauth = FirebaseAuth.getInstance();
        DB_USER = FirebaseDatabase.getInstance().getReference("Users");
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
                if(isInMyFavorite){
                    vmHomeDetailBook.removeFromFavorite(getApplicationContext(),String.valueOf(id));
                }
                else {
                    vmHomeDetailBook.addToFavorite(getApplicationContext(),String.valueOf(id));
                }

            }
        });
        checkIsFavorite(this,String.valueOf(id));
        adapterSimilar = new BookAdapter(listSimilar,getApplicationContext());
        binding.similar.setAdapter(adapterSimilar);
        setContentView(binding.getRoot());

    }
    @Override
    public void ClickQuantityBorrow(int quantity, String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String current = df.format(calendar.getTime());

        try {
            Date date1 = df.parse(current);
            Date date2 = df.parse(date);
            dateeee = Math.abs(date2.getTime() - date1.getTime());
            dayys = TimeUnit.DAYS.convert(dateeee, TimeUnit.MILLISECONDS);

            dayconvert = Math.toIntExact(dayys);
            if(dayconvert == 0){
                dayconvert = 1;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        String idborrowbook = "T"+id;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("idbookborrow",idborrowbook);
        hashMap.put("bookid",id);
        hashMap.put("count",quantity);
        hashMap.put("datestart",current);
        hashMap.put("expirationdate",date);
        hashMap.put("pricetotal",price * quantity * dayconvert);
        hashMap.put("duration", dayconvert);


        DB_USER.child(mauth.getUid()).child("borrowbook").child(idborrowbook).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Add ok", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        Toast.makeText(getApplicationContext(),"Add sucessfull: " +title,Toast.LENGTH_LONG).show();
    }
    public void checkIsFavorite(Context context, String bookId ){
        DB_USER.child(mauth.getUid()).child("favorite").child(bookId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isInMyFavorite = snapshot.exists();
                        if(isInMyFavorite){
                            binding.btnheart.setBackgroundResource(R.drawable.img_heart_red);
                        }
                        else{
                            binding.btnheart.setBackgroundResource(R.drawable.ic_heartwhite);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getBook() {
        Constant.DB_BOOK.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListBook.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book book = dataSnapshot.getValue(Book.class);
                    mListBook.add(book);
                }
                for(Book book:  mListBook){
                    if (book.getId()==id){
                        title=book.getTitle();
                        price=book.getPrice();
                        img=book.getImage();
                        type=book.getType();
                        author=book.getAuthor();
                    }
                }
                binding.authorDetail.setText(author);
                binding.titleDetail.setText(title);
                binding.tvprice.setText(String.valueOf(price));
                Picasso.get().load(img).into(binding.imageDetail);
                for(Book book:  mListBook){
                    if (book.getType().contains(type)){
                        listSimilar.add(book);
                        if (book.getId()==id)
                            listSimilar.remove(book);
                    }
                    adapterSimilar.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
