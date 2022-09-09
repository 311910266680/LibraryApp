package com.example.myapplication.Borrow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.BorrowBook;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentBorrowDialogBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BorrowDialog extends DialogFragment{

    private ClickUpdateBorrow mclickupdate;
    int id, price, duration, pricetotal;
    long tmp1 , tmp2 ;

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
        id = getArguments().getInt("id");
        int n = getArguments().getInt("quantity");
        String datestart = getArguments().getString("datestart");
        price = getArguments().getInt("price");
        String s = getArguments().getString("date");
        binding.btnquantity.setText(String.valueOf(n));
        binding.tvdate.setText(s);


        binding.tvdate.setOnClickListener(new View.OnClickListener() {
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
                                binding.tvdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
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
                int quantity = Integer.parseInt(binding.btnquantity.getText().toString());
                String date = binding.tvdate.getText().toString();

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    Date date1 = df.parse(datestart);
                    Date date2 = df.parse(date);
                    tmp1 = Math.abs(date2.getTime() - date1.getTime());
                    tmp2 = TimeUnit.DAYS.convert(tmp1, TimeUnit.MILLISECONDS);

                    if(tmp2 == 0.0f){
                        tmp2 = 1;
                    }
                    duration = Math.toIntExact(tmp2);
                    pricetotal = price * quantity * duration;

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.e("DDD",String.valueOf(tmp2));


                for(int i = 0; i < Singleton.getListBookBorrow().size(); i++){
                    if(Singleton.getListBookBorrow().get(i).getId() == id){
                        Singleton.getListBookBorrow().get(i).setCount(quantity);
                        Singleton.getListBookBorrow().get(i).setExpirationdate(date);
                        Singleton.getListBookBorrow().get(i).setDuration(tmp2);
                        Singleton.getListBookBorrow().get(i).setPricetotal(pricetotal);
                    }
                }
                mclickupdate.clickupdateborrow(id);
                dismiss();
            }
        });

        return binding.getRoot();
    }
}
