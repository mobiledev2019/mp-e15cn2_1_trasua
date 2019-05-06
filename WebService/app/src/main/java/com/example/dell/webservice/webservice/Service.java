package com.example.dell.webservice.webservice;

import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*
  Dùng để định nghĩa các API
 */
public interface Service {

    @GET("/user/all")
    Call<List<User>> getUsers();

    @GET("/account/all")
    Call<List<Account>> getAccounts();

    @GET("user/detail")
    Call<User> getUserDetail(@Query("userId") String userId);

    @POST("/user/create")
    Call<User> createUser(@Query("name") String name);

    @POST("/user/createV2")
    Call<User> createUserV2(@Body User user);

    @POST("/account/checklogin")
    Call<User> checkLogin(@Body Account account);

    @POST("/account/registeraccount")
    Call<String> registerAccount(@Body Account account);

    @POST("account/update")
    Call<String> updateInfo(@Body User user);
    @GET("store/all")
    Call<List<Store>> getAllStore();
}
