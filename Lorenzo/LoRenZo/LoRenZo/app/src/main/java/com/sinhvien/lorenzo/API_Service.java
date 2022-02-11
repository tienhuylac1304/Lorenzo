package com.sinhvien.lorenzo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API_Service {
    String baseURL="http://172.22.26.215:4000/";
    Gson gson=new GsonBuilder().setDateFormat("dd/MM/yyy HH:mm:ss").create();

    API_Service api=new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build().create(API_Service.class);

    @GET("product")
    Call<ListProducts>getAllProduct();

    @GET("product/type/{type}")
    Call<ListProducts>getProductWithType(@Path("type")int type);

    @GET("product/{id}")
    Call<Product>getProductWithId(@Path("id")int id);

    @GET("customer")
    Call<ListCustomer>getAllKhachHang();

    

    @POST("customer/{name}/{sex}/{date}/{adress}/{phone}/{email}/{account}/{password}")
    Call<Customer>postCustomer(@Path("name")String name,@Path("sex")int sex,@Path("date")String date,@Path("adress")String adress,@Path("phone")String phone,@Path("email")String email,@Path("account")String account,@Path("password")String pass);
}
