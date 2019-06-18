package com.example.dell.webservice.webservice;

import com.example.dell.webservice.model.Account;
import com.example.dell.webservice.model.Order1;
import com.example.dell.webservice.model.Registrator;
import com.example.dell.webservice.model.Statusorder;
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
    @POST("order/add")
    Call<Boolean> addOrder(@Body Order1 order);
    @POST("/account/updateRegister")
    Call<Boolean> updateRegister(@Body Registrator registrator);

    @POST("order/getNotDone")
    Call<List<Statusorder>> getNotDoneOrderById(@Body String userId);
    @POST("order/getDone")
    Call<List<Statusorder>> getDoneOrderById(@Body String userId);
    @POST("account/updateUserInfo")
    Call<Boolean> updateUserInfo(@Body User user);
}
