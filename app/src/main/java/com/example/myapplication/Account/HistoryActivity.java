package com.example.myapplication.Account;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Borrow.BorrowAdapter;
import com.example.myapplication.Constant;
import com.example.myapplication.Home.BookAdapter;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Order;
import com.example.myapplication.Model.Type;
import com.example.myapplication.databinding.ActivityHistoryBinding;
import com.example.myapplication.databinding.ActivityNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private List<Order> orderList;
    private List<BorrowBook> borrowBookList;
    private ActivityHistoryBinding binding;
    private HistoryAdapter historyAdapter;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        orderList = new ArrayList<>();
        borrowBookList = new ArrayList<>();
        mauth  = FirebaseAuth.getInstance();
        LinearLayoutManager hori = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.recHistory.setLayoutManager(hori);
        historyAdapter = new HistoryAdapter(orderList,borrowBookList, getApplicationContext(),mauth.getUid());
        binding.recHistory.setAdapter(historyAdapter);
        getOrder();
        setContentView(binding.getRoot());
    }
    private void getOrder() {
        Constant.DB_ORDER.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Order shop = dataSnapshot.getValue(Order.class);
                   if (shop.getIduser().equals(mauth.getUid())){

                       orderList.add(shop);
                   }

                }
                borrowBookList.clear();
                for (Order order: orderList){
                    if (order.getIduser().equals(mauth.getUid())){
                        borrowBookList.addAll(order.getBorrowbook());
                    }
                }
                historyAdapter.notifyDataSetChanged();
                Log.e("TAG", "onDataChange: "+borrowBookList.size() );   Log.e("TAGdd", "onDataChange: "+orderList.size() );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}