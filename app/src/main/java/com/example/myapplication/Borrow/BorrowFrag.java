package com.example.myapplication.Borrow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentBorrowBinding;
import com.squareup.picasso.Picasso;


public class BorrowFrag extends Fragment {
    private FragmentBorrowBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBorrowBinding.inflate(inflater,container,false);

        BorrowAdapter adapter = new BorrowAdapter(Singleton.getListBookBorrow());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rcvborrow.setLayoutManager(linearLayoutManager);
        binding.rcvborrow.setAdapter(adapter);

        return binding.getRoot();
    }
}