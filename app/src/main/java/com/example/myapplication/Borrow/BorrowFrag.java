package com.example.myapplication.Borrow;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.myapplication.Constant;
import com.example.myapplication.Login.RegisterActivity;
import com.example.myapplication.Model.Book;
import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.Model.Discount;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.ViewModels.Borrow.VMBorrowFrag;
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
    private VMBorrowFrag vmBorrowFrag;
    private String k ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBorrowBinding.inflate(inflater,container,false);
        mauth = FirebaseAuth.getInstance();

        vmBorrowFrag = new VMBorrowFrag();
        borrowBookList = new ArrayList<>();
        listBookBorrowID = new ArrayList<>();
        adapter = new BorrowAdapter(borrowBookList,this,this);
        getlistborrow();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvborrow.setLayoutManager(linearLayoutManager);
        binding.rcvborrow.setAdapter(adapter);
        discountslist = new ArrayList<>();
        vmBorrowFrag.getdiscount(discountslist);

        binding.btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applydiscount(discountslist);
                closeKeyboard(getActivity());
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

    public void getlistborrow(){
        Constant.DB_USER.child(Constant.ID_USER).child("borrowbook").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                borrowBookList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BorrowBook item = dataSnapshot.getValue(BorrowBook.class);
                    borrowBookList.add(item);
                }
                vmBorrowFrag.addBookTolistBorrow(borrowBookList);
                getpricetotal(borrowBookList);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        }) ;
    }
    private void getpricetotal(List<BorrowBook> listbr){
        for(int i = 0; i< listbr.size(); i++){
            listBookBorrowID.add(listbr.get(i).getIdbookborrow());
            subtotal += listbr.get(i).getPricetotal();
        }
        if(listbr.size() == 0){
            binding.subtotal.setText("0");
            binding.total.setText("0");
        }
        binding.subtotal.setText(String.valueOf(subtotal));
        binding.total.setText(String.valueOf(subtotal));

    }
    @Override
    public void ClickDialogdelete(BorrowBook book) {
        vmBorrowFrag.deleteBorrowBook(book.getIdbookborrow(),getContext());
        adapter.notifyDataSetChanged();

    }
    private void applydiscount(List<Discount> dclist){
        if(binding.edtdiscount.getText().toString().isEmpty()){
            binding.edtdiscount.setError("You have not entered the discount code");
        }
        else {
            for(int i = 0; i< dclist.size(); i++){
                if(dclist.get(i).getCode().equals(binding.edtdiscount.getText().toString().trim())){
                    discount = (float) dclist.get(i).getPercent() ;
                    discountdisp = (discount/100 ) * subtotal;
                    k = String.valueOf(discountdisp);
                    String [] n  = k.split("\\.");
                    if(n.length > 1){
                        if(n[1].equals("0")){
                            k = n[0];
                        }
                    }
                    binding.discount.setText("- "+ k);
                    total = subtotal - (Integer.parseInt(k));
                    binding.total.setText(String.valueOf(total));
                }
            }
        }
    }
    public void closeKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
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