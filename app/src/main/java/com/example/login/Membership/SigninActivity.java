package com.example.login.Membership;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.ActualActivities.RealMainActivity;
import com.example.login.Membership.OurUser.OurUser;
import com.example.login.Membership.OurUser.OurUserAPI;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.R;
import com.example.login.databinding.ActivitySigninBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 회원가입 끝나면, RealMainActivity로 이동해야 함

public class SigninActivity extends AppCompatActivity {

    // 회원 가입 가능, 불가능 Boolean type
    public boolean signInGrant;

    private ActivitySigninBinding binding;

    // 생성자가 끝나면 자동으로 onCreate 실행
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button okbtn = findViewById(R.id.okbutton);


        // Edit text 선언
        // 함수 만들고 반응 가져오기
        EditText idOurMember, pwOurMember;
        idOurMember = findViewById(R.id.newid);
        pwOurMember = findViewById(R.id.newpwd);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Server sign up function execution
                String id = idOurMember.getText().toString();
                String password = pwOurMember.getText().toString();

                // Perform the membership check and handle the response inside the method
                CheckOurMembership(id, password);
            }
        });
    }

    private void CheckOurMembership(String idOurMember,String pwOurMember){

        OurUser ourUser = new OurUser();
        ourUser.setId(idOurMember);
        ourUser.setPwd(pwOurMember);

        RetrofitService retrofitService = new RetrofitService();
        OurUserAPI ourUserAPI = retrofitService.getRetrofit().create(OurUserAPI.class);

        ourUserAPI.signup(ourUser).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    signInGrant = response.body();
                    if(signInGrant) {
                        // Move to RealMainActivity if signInGrant is true
                        Intent intent = new Intent(SigninActivity.this, RealMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Show a message if signInGrant is false
                        Toast.makeText(SigninActivity.this, "Already Our Member!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where the response is not successful or the body is null
                    Log.e("OurUserAPI", "Response not successful or body is null, Response Code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("OurUserAPI", "Failed to communicate with server: " + t.getMessage());
            }
        });
    }

}