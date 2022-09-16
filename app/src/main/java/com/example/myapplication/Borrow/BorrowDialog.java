package com.example.myapplication.Borrow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.ViewModels.Borrow.VMBorrowDialog;
import com.example.myapplication.databinding.FragmentBorrowDialogBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BorrowDialog extends DialogFragment{

    private ClickUpdateBorrow mclickupdate;
    VMBorrowDialog vmBorrowDialog;

    int count, price, duration, pricetotal, quantity;
    String id, datestart,expirationdate, date;

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

    public BorrowDialog(ClickUpdateBorrow mclickupdate) {
        this.mclickupdate = mclickupdate;
    }

    private FragmentBorrowDialogBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBorrowDialogBinding.inflate(inflater,container,false);
        vmBorrowDialog = new VMBorrowDialog();

        getExtraBR();

        binding.tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
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
        binding.btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateborrowbook();
                mclickupdate.clickupdateborrow(id);
                dismiss();
            }
        });
        return binding.getRoot();
    }


    private void updateborrowbook(){
        quantity = Integer.parseInt(binding.btnquantity.getText().toString());
        date = binding.tvdate.getText().toString();
        vmBorrowDialog.VMUpdateBorrowDialog(id, datestart,date,duration,pricetotal, price,quantity);
    }
    private void getExtraBR(){
        id = getArguments().getString("id");
        count = getArguments().getInt("count");
        datestart = getArguments().getString("datestart");
        expirationdate = getArguments().getString("expirationdate");
        price = getArguments().getInt("price",1);
        binding.btnquantity.setText(String.valueOf(count));
        binding.tvdate.setText(expirationdate);
    }
    private void showDatePicker(){
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
                        binding.tvdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }
}
