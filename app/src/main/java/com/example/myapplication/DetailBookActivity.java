package com.example.myapplication;

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
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
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
    private FirebaseAuth firebaseAuth;
    private ActivityDetailbookBinding binding;
    private List<Book> mListBook;
    private String img, title, type;
    private int id,price, dayconvert;
    private long dateeee,dayys;
    boolean isInMyFavorite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBook();
        binding = ActivityDetailbookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        Intent get = getIntent();

        mListBook = new ArrayList<>();
        id = get.getIntExtra("id",1);

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
                    removeFromFavorite(getApplicationContext(),String.valueOf(id));
                }
                else {
                    addToFavorite(getApplicationContext(),String.valueOf(id));
                }

            }
        });
        checkIsFavorite(this,String.valueOf(id));

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
        String uid = firebaseAuth.getUid();
        String idborrowbook = "T"+id;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("idbookborrow",idborrowbook);
        hashMap.put("bookid",id);
        hashMap.put("count",quantity);
        hashMap.put("datestart",current);
        hashMap.put("expirationdate",date);
        hashMap.put("pricetotal",price * quantity * dayconvert);
        hashMap.put("duration", dayconvert);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid).child("borrowbook").child(idborrowbook).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Add to your favorite", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to add your favorite"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//
//        Singleton.getListBookBorrow().add(new BorrowBook(Singleton.getListBookBorrow().size() + 1,img,title,quantity,current,date,price, price * quantity,dayys));
        Toast.makeText(getApplicationContext(),"Add sucessfull: " +title,Toast.LENGTH_LONG).show();
    }


    public void checkIsFavorite(Context context, String bookId ){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("favorite").child(bookId)
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
    public void addToFavorite(Context context, String bookId  ){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        HashMap<String, Object> hashMap =new HashMap<>();
        hashMap.put("bookId",""+bookId);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("favorite").child(bookId).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Add to your favorite", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to add your favorite"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void removeFromFavorite(Context context, String bookId){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("favorite").child(bookId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Remove from your favorite", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to remove from your favorite"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getBook() {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("book");
        myRef.addValueEventListener(new ValueEventListener() {
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
                    }
                }
                binding.titleDetail.setText(title);
                binding.tvprice.setText(String.valueOf(price));
                Picasso.get().load(img).into(binding.imageDetail);

                Log.e("TAG", "onDataChange: "+mListBook );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
