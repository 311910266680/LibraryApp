package com.example.myapplication.Borrow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.FragmentBorrowBottomsheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

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
                String s = binding.tvdate.getText().toString();
                mclickquantity.ClickQuantityBorrow(n,s);
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
        binding.pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.tvdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        return binding.getRoot();
    }
}
