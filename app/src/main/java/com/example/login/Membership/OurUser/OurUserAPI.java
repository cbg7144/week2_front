package com.example.login.Membership.OurUser;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OurUserAPI {

    // 회원가입
    @POST("/userinfo/signup")
    Call<Boolean> signup(@Body OurUser ourUser);

    // 로그인
    @GET("/userinfo/login")
    Call<Boolean> login(@Query("id") String id, @Query("pwd") String password);


}
