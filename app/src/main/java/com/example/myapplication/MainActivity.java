package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Borrow.BorrowFrag;
import com.example.myapplication.Favorite.FavoriteFrag;
import com.example.myapplication.Fragment.HomeFrag;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.Type;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookAdapter mBookAdapter;
    private TypeAdapter mTypeAdapter;
    private RecyclerView rcvBook,recPick,recBorrowed;
    private List<Book> mListBook;
    private List<Type> ListType;
    private FloatingActionButton btnSearch;
    String noti = "asdvsa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFrag()).commit();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("TAG", "onSuccess: "+newToken );
            }
        });

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
                    case R.id.borrow:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new BorrowFrag()).commit();
                        return true;
                    case R.id.fvr:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new FavoriteFrag()).commit();
                        return true;
                }
                return false;
            }
        });
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("new_user_forums");
    }
}