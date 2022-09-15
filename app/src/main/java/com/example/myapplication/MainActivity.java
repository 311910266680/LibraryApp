package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Account.AccountFragment;
import com.example.myapplication.Borrow.BorrowFrag;
import com.example.myapplication.Favorite.FavoriteFrag;
import com.example.myapplication.Fragment.HomeFrag;
import com.example.myapplication.Home.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        FirebaseMessaging.getInstance().subscribeToTopic("newNoti");

        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFrag()).commit();


        BottomNavigationView bottomNavigation = findViewById(R.id.bot);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
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
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,new AccountFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }


}