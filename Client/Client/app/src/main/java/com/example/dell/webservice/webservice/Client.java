package com.example.dell.webservice.webservice;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client instance;
    private static Service service;

    public static Service getService(){
        if(instance == null){
            instance = new Client();
        }
        return service;
    }

    private Client(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                        .addInterceptor(httpLoggingInterceptor)
                                        .build();
        service = new Retrofit.Builder()
                        .baseUrl("http://192.168.43.177:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build()
                        .create(Service.class);
    }


}
