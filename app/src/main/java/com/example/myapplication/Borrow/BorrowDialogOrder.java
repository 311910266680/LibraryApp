package com.example.myapplication.Borrow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Home.PickActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentBorrowDialogOrderBinding;


public class BorrowDialogOrder extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null)
            return;
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getTheme() {
        return R.style.CustomAlertDialog;

    }

    public BorrowDialogOrder(){
    }

    private FragmentBorrowDialogOrderBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBorrowDialogOrderBinding.inflate(inflater,container,false);


        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PickActivity.class);
                startActivity(i);
                dismiss();
            }
        });
        binding.btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                dismiss();
            }
        });


        return binding.getRoot();
    }

}
