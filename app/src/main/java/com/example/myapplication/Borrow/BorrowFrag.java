package com.example.myapplication.Borrow;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Discount;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentBorrowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BorrowFrag extends Fragment implements ClickDialogDelete,ClickShowDialog, ClickUpdateBorrow{
    private FragmentBorrowBinding binding;
    private BorrowAdapter adapter;
    private int subtotal, total;
    private FirebaseDatabase firebaseDatabase;
    private List<Discount> discountslist;
    private float discount, discountdisp;
    private List<BorrowBook> borrowBookList;
    private FirebaseAuth mauth;
    private String k;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBorrowBinding.inflate(inflater,container,false);

        mauth = FirebaseAuth.getInstance();

        getlistborrow();

        adapter = new BorrowAdapter(borrowBookList,this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvborrow.setLayoutManager(linearLayoutManager);
        binding.rcvborrow.setAdapter(adapter);
        getpricetotal();
        discountslist = new ArrayList<>();

        getdiscount();



        binding.btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.edtdiscount.getText().toString().isEmpty()){
                    binding.edtdiscount.setError("You have not entered the discount code");
                }
                else {
                    for(int i = 0; i< discountslist.size(); i++){
                        if(discountslist.get(i).getCode().equals(binding.edtdiscount.getText().toString().trim())){
                            discount = (float) discountslist.get(i).getPercent() ;
                            discountdisp = (discount/100 ) * subtotal;
                            removedot(discountdisp);
                            binding.discount.setText("- "+ k);
                            total = subtotal - (Integer.parseInt(k));
                            binding.total.setText(String.valueOf(total));
                        }
                    }
                }
            }
        });


        return binding.getRoot();
    }

    @Override
    public void ClickDialogdelete(BorrowBook book) {
        for (int i = 0; i< Singleton.getListBookBorrow().size(); i++){
            if(Singleton.getListBookBorrow().get(i).getId() == book.getId()){
                Singleton.getListBookBorrow().remove(Singleton.getListBookBorrow().get(i));
            }
        }
        adapter.notifyDataSetChanged();

    }

    private void getpricetotal(){
        for(int i = 0; i< Singleton.getListBookBorrow().size(); i++){
           subtotal += Singleton.getListBookBorrow().get(i).getPricetotal();
        }
        binding.subtotal.setText(String.valueOf(subtotal));
        binding.total.setText(String.valueOf(subtotal));

    }
    private void getdiscount() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("discountcode");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                discountslist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Discount discount = dataSnapshot.getValue(Discount.class);
                    discountslist.add(discount);
                }
                Log.e("check", String.valueOf(discountslist.size()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getlistborrow() {
        String uid = mauth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users");
        myRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                borrowBookList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BorrowBook borrowBook = dataSnapshot.getValue(BorrowBook.class);
                    borrowBookList.add(borrowBook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void removedot(float x){
        k = String.valueOf(x);
        String [] n  = k.split("\\.");
        if(n.length > 1){
            if(n[1].equals("0")){
                k = n[0];
            }
        }
    }



    @Override
    public void clickShowdialog(BorrowBook borrowBook) {
        Bundle bundle = new Bundle();
        bundle.putInt("id",borrowBook.getId());
        bundle.putInt("quantity",borrowBook.getCount());
        bundle.putString("datestart",borrowBook.getDatestart());
        bundle.putInt("price",borrowBook.getBook().getPrice());
        bundle.putString("date",borrowBook.getExpirationdate());
        BorrowDialog dialog = new BorrowDialog(this);
        dialog.setArguments(bundle);

        dialog.show(getParentFragmentManager(),"ok");
    }
    @Override
    public void clickupdateborrow(int id) {
        getpricetotal();
        adapter.notifyDataSetChanged();
    }
}