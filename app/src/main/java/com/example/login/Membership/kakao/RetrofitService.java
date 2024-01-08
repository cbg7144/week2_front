package com.example.login.Membership.kakao;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public  RetrofitService() { initalizeRetrofit(); }

    private void initalizeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.249.30.226:8080")  // 나의 컴퓨터의 config로 IPV4 입력하고 8080 삽입
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {return retrofit;}
}
