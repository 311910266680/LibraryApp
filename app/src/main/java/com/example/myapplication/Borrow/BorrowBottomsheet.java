package com.example.myapplication.Borrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.FragmentBorrowBottomsheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BorrowBottomsheet extends BottomSheetDialogFragment {

    CLickQuantity mclickquantity;
    public BorrowBottomsheet(CLickQuantity clickquantity) {
        this.mclickquantity = clickquantity;
    }
    private FragmentBorrowBottomsheetBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentBorrowBottomsheetBinding.inflate(inflater,container,false);
        binding.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(binding.btnquantity.getText().toString());
                int k = n - 1;
                if(k < 1){
                    k = 1;
                }
                binding.btnquantity.setText(String.valueOf(k));
            }
        });
        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(binding.btnquantity.getText().toString());
                int k = n + 1;
                binding.btnquantity.setText(String.valueOf(k));


            }
        });
        binding.btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(binding.btnquantity.getText().toString());
                mclickquantity.ClickQuantityBorrow(n);
                dismiss();
            }
        });
        binding.btndissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return binding.getRoot();
    }
}
