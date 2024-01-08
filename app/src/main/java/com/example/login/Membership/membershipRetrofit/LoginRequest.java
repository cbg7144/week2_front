package com.example.login.Membership.membershipRetrofit;

import com.google.gson.annotations.SerializedName;

// 서버에 보낼 데이터이며 입력한 id와 password를 서버에 보내줌
public class LoginRequest {

    // @SerializedName : 이것의 value는 객체를 직렬화 및 역직렬화 할때 이름으로 사용됨 /  음 column이라고 보면 되나??
    @SerializedName("input_id")
    public String inputId;

    @SerializedName("input_pw")
    public String inputPw;

    public String getInputId() {
        return inputId;
    }

    public void setInputId(String inputId) {
        this.inputId = inputId;
    }

    public String getInputPw() {
        return inputPw;
    }

    public void setInputPw(String inputPw) {
        this.inputPw = inputPw;
    }

    public LoginRequest(String inputId, String inputPw){
        this.inputId = inputId;
        this.inputPw = inputPw;
    }
}
