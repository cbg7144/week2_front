package com.example.login.Membership;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login.ActualActivities.RealMainActivity;
import com.example.login.R;
import com.example.login.databinding.ActivitySigninBinding;

// 회원가입 끝나면, RealMainActivity로 이동해야 함

public class SigninActivity extends AppCompatActivity {

    private ActivitySigninBinding binding;

    // 생성자가 끝나면 자동으로 onCreate 실행
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button okbtn = findViewById(R.id.okbutton);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SigninActivity 실행
                Intent intent = new Intent(SigninActivity.this, RealMainActivity.class);
                startActivity(intent);
                // 나머지 종료
                finish();
            }
        });

    }
}