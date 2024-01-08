package com.example.login.Membership.kakao;

import com.kakao.sdk.user.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface KakaoUserAPI {

    @POST("/member/login/do") // token 넘겨줘서 저장

    // 주는 것은 KakaoUser 형태, 받는 것은 KakaoUserInfo 형태
    Call<KakaoUserInfo> saveKakaoUserInfo(@Body KakaoUser kakaouser);

}
