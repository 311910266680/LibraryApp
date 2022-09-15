package com.example.myapplication.Borrow;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.myapplication.Login.RegisterActivity;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Discount;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentBorrowBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class BorrowFrag extends Fragment implements ClickDialogDelete,ClickShowDialog, ClickUpdateBorrow {
    private FragmentBorrowBinding binding;
    private BorrowAdapter adapter;
    private int subtotal, total;
    private FirebaseDatabase firebaseDatabase;
    private List<Discount> discountslist;
    private float discount, discountdisp;
    private List<BorrowBook> borrowBookList;
    private List<String> listBookBorrowID;
    private FirebaseAuth mauth;

    private String k;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBorrowBinding.inflate(inflater,container,false);

        mauth = FirebaseAuth.getInstance();

        getListBorrow();


        adapter = new BorrowAdapter(borrowBookList ,this,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvborrow.setLayoutManager(linearLayoutManager);
        binding.rcvborrow.setAdapter(adapter);

        discountslist = new ArrayList<>();
        getpricetotal();
        getdiscount();


        binding.btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applydiscount();
            }
        });
        binding.btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),FragmentBorrowActivityOrder.class);

                i.putStringArrayListExtra("listidbookborrow", (ArrayList<String>) listBookBorrowID);
                i.putExtra("subtotal",subtotal);
                i.putExtra("discount",k);
                i.putExtra("total",total);

                startActivity(i);
            }
        });


        return binding.getRoot();
    }


    public void getListBorrow(){
        borrowBookList = new ArrayList<>();
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(mauth.getCurrentUser().getUid()).child("borrowbook").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                borrowBookList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BorrowBook item = dataSnapshot.getValue(BorrowBook.class);
                    borrowBookList.add(item);
                }

                addBookTolistBorrow(borrowBookList);
                adapter.notifyDataSetChanged();
                getpricetotal();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }) ;
    }

    public void addBookTolistBorrow(List<BorrowBook> listborrowbook){

        for(int i = 0; i< Singleton.getInstance().listBookmain.size(); i++){
            for(int j = 0; j<listborrowbook.size(); j++){
                if(listborrowbook.get(j).getBookid() == Singleton.getInstance().listBookmain.get(i).getId()){
                    listborrowbook.get(j).setBook(Singleton.getInstance().listBookmain.get(i));
                }
            }
        }
    }

    @Override
    public void ClickDialogdelete(BorrowBook book) {
        deleteborrowbook(book.getIdbookborrow());
        adapter.notifyDataSetChanged();

    }
    private void deleteborrowbook(String id){
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myf = firebaseDatabase.getReference("Users");
        myf.child(mauth.getUid()).child("borrowbook").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(),"Delete ok", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Delete fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getpricetotal(){
        listBookBorrowID = new ArrayList<>();
        for(int i = 0; i< borrowBookList.size(); i++){
            // Use add to list stringid book - khong lien quan o duoi
            listBookBorrowID.add(borrowBookList.get(i).getIdbookborrow());


            subtotal += borrowBookList.get(i).getPricetotal();
        }
        if(borrowBookList.size() == 0){
            binding.subtotal.setText("0");
            binding.total.setText("0");
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void applydiscount(){
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
        bundle.putString("id",borrowBook.getIdbookborrow());
        bundle.putInt("bookid",borrowBook.getBookid());
        bundle.putInt("count",borrowBook.getCount());
        bundle.putInt("price",borrowBook.getBook().getPrice());
        bundle.putInt("pricetotal",borrowBook.getPricetotal());
        bundle.putString("datestart",borrowBook.getDatestart());
        bundle.putString("expirationdate",borrowBook.getExpirationdate());
        BorrowDialog dialog = new BorrowDialog(this);
        dialog.setArguments(bundle);

        dialog.show(getParentFragmentManager(),"ok");
    }

    @Override
    public void clickupdateborrow(String id) {
        adapter.notifyDataSetChanged();
    }
}