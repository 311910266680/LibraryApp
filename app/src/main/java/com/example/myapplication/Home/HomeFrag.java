package com.example.myapplication.Home;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.myapplication.Home.BookAdapter;
import com.example.myapplication.Home.SearchActivity;
import com.example.myapplication.Home.TypeAdapter;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.Type;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
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
    private BookAdapter mBookAdapter;
    private TypeAdapter mTypeAdapter;
    private RecyclerView rcvBook,recPick,recBorrowed;
    private List<Book> mListBook,listvalid;
    private List<Type> ListType;
    private CardView search;
    private TextView nameHome;
    private FirebaseAuth firebaseAuth;
    private CircleImageView imghome;
    private ImageView notifi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        notifi = view.findViewById(R.id.notifi);
        notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotificationsActivity.class));
            }
        });
        nameHome= view.findViewById(R.id.nameHome);
        rcvBook = view.findViewById(R.id.rec1);
        recPick = view.findViewById(R.id.recsearch);
        recBorrowed = view.findViewById(R.id.rec3);
        search = view.findViewById(R.id.search);
        mListBook = new ArrayList<>();
        ListType= new ArrayList<>();
        listvalid= new ArrayList<>();
        Singleton.getInstance().listBookmain = listvalid;
        mBookAdapter = new BookAdapter(mListBook,getContext());
        mTypeAdapter = new TypeAdapter(ListType,getContext());
        Singleton.getInstance().ListBook = listvalid;
        rcvBook.setAdapter(mBookAdapter);
        LinearLayoutManager hori = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvBook.setLayoutManager(hori);
        firebaseAuth = FirebaseAuth.getInstance();
        imghome = view.findViewById(R.id.imghome);
        recPick.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recBorrowed.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recPick.setAdapter(mTypeAdapter);
        recBorrowed.setAdapter(mBookAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        getBookAvailable();
        getType();
        loadUserInfo();
        getBook();
        return view;
    }
    private void getBook() {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("book");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listvalid.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book shop = dataSnapshot.getValue(Book.class);
                    listvalid.add(shop);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getBookAvailable() {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("book");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListBook.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book shop = dataSnapshot.getValue(Book.class);

                    if (shop.getQuantity()!=0){
                        mListBook.add(shop);
                    }

                }
                mBookAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getType() {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("type");
        myRef.addValueEventListener(new ValueEventListener() {
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ""+snapshot.child("name").getValue();
                nameHome.setText("Hello "+name+"!");
                String profileImage = ""+snapshot.child("profileImage").getValue();
                if (!profileImage.isEmpty()){
                    Picasso.get().load(profileImage).into(imghome);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}