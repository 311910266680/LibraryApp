package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.Fragment.HomeFrag;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.Type;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookAdapter mBookAdapter;
    private TypeAdapter mTypeAdapter;
    private RecyclerView rcvBook,recPick,recBorrowed;
    private List<Book> mListBook;
    private List<Type> ListType;
    private FloatingActionButton btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
//        rcvBook = findViewById(R.id.rec1);
//        recPick = findViewById(R.id.rec2);
//        recBorrowed = findViewById(R.id.rec3);
//
//        mListBook = new ArrayList<>();
//        Singleton.getInstance().ListBook = mListBook;
//        ListType= new ArrayList<>();
//        mBookAdapter = new BookAdapter(mListBook,getApplicationContext());
//        mTypeAdapter = new TypeAdapter(ListType,getApplicationContext());
//        rcvBook.setAdapter(mBookAdapter);
//        LinearLayoutManager hori = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        rcvBook.setLayoutManager(hori);
//
//        recPick.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
//        recBorrowed.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
//        recPick.setAdapter(mTypeAdapter);
//        recBorrowed.setAdapter(mBookAdapter);
//
//     getBook();
//     getType();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFrag()).commit();
        BottomNavigationView bottomNavigation = findViewById(R.id.bot);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFrag()).commit();
                        return true;
                }
                return false;
            }
        });
    }

//    private void getBook() {
//        FirebaseDatabase database =FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("book");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mListBook.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Book shop = dataSnapshot.getValue(Book.class);
//                    mListBook.add(shop);
//                }
//                mBookAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    private void getType() {
//        FirebaseDatabase database =FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("type");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ListType.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Type shop = dataSnapshot.getValue(Type.class);
//                    ListType.add(shop);
//                }
//                mTypeAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}