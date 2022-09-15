package com.example.myapplication.Borrow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Login.RegisterActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.District;
import com.example.myapplication.Model.Province;
import com.example.myapplication.Model.Ward;
import com.example.myapplication.R;
import com.example.myapplication.Singleton;
import com.example.myapplication.databinding.FragmentBorrowActivityOrderBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBorrowActivityOrder extends AppCompatActivity{
    private FragmentBorrowActivityOrderBinding binding;
    private String[] courses;
    private List<Province> provinceList;
    private List<String> nameprovincelist;

    private List<District> districtList;
    private List<String> namedistrictList;

    private List<Ward> wardList;
    private List<String> namewardList;

    private int t1hour, t1minute;
    private FirebaseAuth mauth;


    private List<String> listIdBorrow;
    private int subTotal, discount, total;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentBorrowActivityOrderBinding.inflate(getLayoutInflater());

        courses = new String[]{"Pick up at the library", "Shipping"};

        ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,courses);
        binding.spndelivery.setAdapter(ad);
        binding.spndelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickSpiner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvhour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHour();
            }
        });
        callAPI();


        binding.btnorderr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrderFirebase();
                startActivity(new Intent(FragmentBorrowActivityOrder.this,MainActivity.class));
            }
        });

        setContentView(binding.getRoot());

    }





    private void pickSpiner(int i){
        if(courses[i].equals("Pick up at the library")){
            binding.edtprovince.setVisibility(View.GONE);
            binding.edtdistrict.setVisibility(View.GONE);
            binding.edtward.setVisibility(View.GONE);

            binding.tvhouse1.setVisibility(View.VISIBLE);
            binding.tvhour.setVisibility(View.VISIBLE);
        }
        else {
            binding.edtprovince.setVisibility(View.VISIBLE);
            binding.edtdistrict.setVisibility(View.VISIBLE);
            binding.edtward.setVisibility(View.VISIBLE);

            binding.tvhouse1.setVisibility(View.GONE);
            binding.tvhour.setVisibility(View.GONE);
        }
    }
    private void callAPI(){
        RetrofitAPICity retrofitAPICity = Singleton.getInstance().getRetrofit("https://provinces.open-api.vn/").create(RetrofitAPICity.class);
        Call<List<Province>> call = retrofitAPICity.getCity();
        call.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if(response.body() != null){
                    nameprovincelist = new ArrayList<>();
                    provinceList = new ArrayList<>();

                    for(int i = 0; i< response.body().size(); i++){
                        nameprovincelist.add(response.body().get(i).getName());
                        provinceList.add(response.body().get(i));
                    }
                    ArrayAdapter add = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,nameprovincelist);
                    binding.edtprovince.setAdapter(add);

                    callDistrict();
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {

            }
        });
    }
    private void callDistrict(){
        binding.edtprovince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namedistrictList = new ArrayList<>();
                districtList = new ArrayList<>();

                for(int i = 0; i< provinceList.size(); i++){
                    if(nameprovincelist.get(position).toString().equals(provinceList.get(i).getName())){
                        for(int j = 0; j< provinceList.get(i).getDistricts().size(); j++){
                            namedistrictList.add(provinceList.get(i).getDistricts().get(j).getName());
                            districtList.add(provinceList.get(i).getDistricts().get(j));

                        }
                    }
                }
                ArrayAdapter addt = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,namedistrictList);
                binding.edtdistrict.setAdapter(addt);
                callWard();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void callWard(){
        binding.edtdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                namewardList = new ArrayList<>();
                wardList = new ArrayList<>();

                for(int i = 0; i< districtList.size(); i++){
                    if(namedistrictList.get(position).toString().equals(districtList.get(i).getName())){
                        for(int j = 0; j< districtList.get(i).getWards().size(); j++){
                            namewardList.add(districtList.get(i).getWards().get(j).getName());
                            wardList.add(districtList.get(i).getWards().get(j));

                        }
                    }
                }
                ArrayAdapter addto = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,namewardList);
                binding.edtward.setAdapter(addto);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void showHour(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(FragmentBorrowActivityOrder.this, android.R.style.Theme_Holo_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                t1hour = hourOfDay;
                t1minute = minute;

                String time = t1hour + ":" + t1minute;
                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");

                try {
                    Date date = f24Hours.parse(time);

                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                    binding.tvhour.setText(f12Hours.format(date));
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        },12,0,false
        );

        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(t1hour,t1minute);
        timePickerDialog.show();
    }


    private void getDataFromIntent(){
        listIdBorrow = getIntent().getStringArrayListExtra("listidbookborrow");
        subTotal = getIntent().getIntExtra("subtotal",1);
        total = getIntent().getIntExtra("total",1);
        discount = getIntent().getIntExtra("discount",1);
    }
    private void addOrderFirebase(){

        if(binding.edtreceivename.getText().toString().isEmpty()){
            binding.edtreceivename.setError("Enter Receivename");
        }
        else if(binding.edtnote.getText().toString().isEmpty()){
            binding.edtnote.setError("Enter note");
        }
        else {
            Random rand = new Random();
            String uniqueID = UUID.randomUUID().toString();
            mauth = FirebaseAuth.getInstance();
            String iduser = mauth.getUid();

            getDataFromIntent();

            String note = binding.edtnote.getText().toString();
            String name = binding.edtreceivename.getText().toString();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Order");

            if(binding.spndelivery.getSelectedItem().toString().equals("Pick up at the library") ){
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id",uniqueID);
                hashMap.put("borrowbookid",listIdBorrow);
                hashMap.put("Receivename",name);
                hashMap.put("hours",binding.tvhour.getText().toString());
                hashMap.put("iduser",iduser);
                hashMap.put("note",note);

                databaseReference.child("Pick up at the library").child(uniqueID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(FragmentBorrowActivityOrder.this,"sucessfull", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FragmentBorrowActivityOrder.this,"Fail !!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                String address = binding.edtprovince.getSelectedItem().toString() + binding.edtdistrict.getSelectedItem().toString() + binding.edtward.getSelectedItem().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id",uniqueID);
                hashMap.put("borrowbookid",listIdBorrow);
                hashMap.put("Receivename",name);
                hashMap.put("address",address);
                hashMap.put("iduser",iduser);
                hashMap.put("note",note);

                databaseReference.child("Shipping").child(uniqueID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(FragmentBorrowActivityOrder.this,"sucessfull", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FragmentBorrowActivityOrder.this,"Fail !!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}