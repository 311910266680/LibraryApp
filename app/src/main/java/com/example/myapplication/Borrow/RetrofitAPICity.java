package com.example.myapplication.Borrow;

import com.example.myapplication.Model.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPICity {
    @GET("api/?depth=3")
    Call<List<Province>> getCity();
}
