package com.example.myapplication.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Account.NotificationsActivity;
import com.example.myapplication.Constant;
import com.example.myapplication.Home.BookAdapter;
import com.example.myapplication.Home.SearchActivity;
import com.example.myapplication.Home.TypeAdapter;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Order;
import com.example.myapplication.Model.Type;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFrag extends Fragment {
    private FragmentHomeBinding binding;
    private BookAdapter mBookAdapter,mBookBorrowedAdapter;
    private TypeAdapter mTypeAdapter;
    private List<Type> ListType;
    private List<Book> listBookmain,listBor;
    private List<BorrowBook> listBorrowed;

    private List<Order> orderList;
    FirebaseAuth mauth;
    private DatabaseReference DB_USER;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        listBookmain = new ArrayList<>();
        ListType = new ArrayList<>();
        listBor = new ArrayList<>();
        listBorrowed = new ArrayList<>();
        orderList = new ArrayList<>();
        mauth  = FirebaseAuth .getInstance();
        DB_USER = FirebaseDatabase.getInstance().getReference("Users");
        binding.notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotificationsActivity.class));
            }
        });
        mBookAdapter = new BookAdapter(listBookmain, getContext());

        mBookBorrowedAdapter = new BookAdapter(listBor, getContext());
        mTypeAdapter = new TypeAdapter(ListType,getContext());

        LinearLayoutManager hori = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        binding.rec1.setLayoutManager(hori);
        binding.recsearch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rec3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rec1.setAdapter(mBookAdapter);
        binding.recsearch.setAdapter(mTypeAdapter);
        binding.rec3.setAdapter(mBookBorrowedAdapter);
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        getType();
        loadUserInfo();
        Singleton.getInstance().getBookAvailable(listBookmain,mBookAdapter);
        Singleton.getInstance().getListBook();
        Singleton.getInstance().getBorrowed(orderList,listBorrowed,listBor,mBookBorrowedAdapter);
        return  binding.getRoot();
    }


    private void getType() {
        Constant.DB_TYPE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListType.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Type shop = dataSnapshot.getValue(Type.class);
                    ListType.add(shop);
                }
                mTypeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadUserInfo() {

        DB_USER.child(mauth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                binding.nameHome.setText("Hello "+name+"!");
                String profileImage = ""+snapshot.child("profileImage").getValue();
                if (!profileImage.isEmpty()){
                    Picasso.get().load(profileImage).into(binding.imghome);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
