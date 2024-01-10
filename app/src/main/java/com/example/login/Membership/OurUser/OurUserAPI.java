package com.example.login.Membership.OurUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OurUserAPI {

    // 회원가입
    @POST("/userinfo/signup")
    Call<Boolean> signup(@Body OurUser ourUser);

    // 로그인
    @GET("/userinfo/login")
    Call<Boolean> login(OurUser ourUser);


}
