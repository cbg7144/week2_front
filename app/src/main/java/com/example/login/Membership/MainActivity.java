package com.example.login.Membership;

// Q. Difference between login() and accountlogin()

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.login.Membership.OurUser.OurUser;
import com.example.login.Membership.OurUser.OurUserAPI;
import com.example.login.Membership.kakao.KakaoUser;
import com.example.login.Membership.kakao.KakaoUserAPI;
import com.example.login.Membership.kakao.KakaoUserInfo;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.Membership.membershipRetrofit.LoginResponse;
import com.example.login.Membership.membershipRetrofit.RetrofitClient;
import com.example.login.R;
import com.example.login.ActualActivities.RealMainActivity;
import com.example.login.databinding.ActivityMainBinding;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;

// 내가 설정한 class 또는 interface 가져오는 방법
import com.example.login.Membership.membershipRetrofit.initMyApi;
import com.example.login.Membership.membershipRetrofit.LoginRequest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/////////////////////dskfajlqkwe
public class MainActivity extends AppCompatActivity {

    // 로그인 기능 구현
    private RetrofitClient retrofitClient;
    private initMyApi initMyApi;

    // Saving
    private static final String DATA_STORE = "myDataStore";


    private ActivityMainBinding binding;
    public boolean logInGrant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("KeyHash", getKeyHash());

        // Button 생성
        ImageButton kakao_login_button = (ImageButton)findViewById(R.id.kakao_login_button);
        Button signin_button = findViewById(R.id.signin) ;
        Button self_Login_button = findViewById(R.id.loginbtn);

        // Edit Text
        EditText idText, passwordText;
        idText = findViewById(R.id.idText);
        passwordText = findViewById(R.id.passwordText);

        // 회원가입 창으로 이동하는 버튼
        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SigninActivity 실행
                Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        // Kakao 로그인 창으로 이동하는 버튼
        kakao_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    //카카오톡 로그인 ??
                    login();
                }
                else{
                    // 왜 두개지 ???
                    accountLogin();
                }
            }
        });

        self_Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String password = passwordText.getText().toString();
                loginOurMember(id, password);
            }
        });

    }

    private void loginOurMember(String id, String password){
        OurUser ourUser = new OurUser();
        ourUser.setId(id);
        ourUser.setPwd(password);

        RetrofitService retrofitService = new RetrofitService();
        OurUserAPI ourUserAPI = retrofitService.getRetrofit().create(OurUserAPI.class);

        ourUserAPI.login(id, password).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null){
                    logInGrant = response.body();
                    if(logInGrant){
                        Intent intent = new Intent(MainActivity.this, RealMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(MainActivity.this, "Not Our Member", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("OurUserAPI", "Response not successful or body is null, Response Code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("OurUserAPI", "Failed to communicate with server: " + t.getMessage());
            }
        });

    }


    // 닉네임만 가져올 수 있음 - 권한 설정이 거기 까지....
    public void login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
                setLogin(true);
                sendTokenToServer(oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
                setLogin(true);
            }
            return null;
        });
    }

    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                Account user1 = user.getKakaoAccount();
                System.out.println("사용자 계정" + user1);
            }
            return null;
        });
    }

    public String getKeyHash(){
        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            if(packageInfo == null) return null;
            for(Signature signature: packageInfo.signatures){
                try{
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                }catch (NoSuchAlgorithmException e){
                    Log.w("getKeyHash", "Unable to get MessageDigest. signature="+signature, e);
                }
            }
        }catch(PackageManager.NameNotFoundException e){
            Log.w("getPackageInfo", "Unable to getPackageInfo");
        }
        return null;
    }

    private void setLogin(boolean bool){
        if (bool) {
            // 이게 true값일때

            // RealMainActivity class 실행...
            Intent intent = new Intent(this, RealMainActivity.class);
            startActivity(intent);

            // MainActivity를 종료 (메모리에서 제거)
            finish();

        } else {
        }
    }

    private void sendTokenToServer(String token){
        RetrofitService retrofitService = new RetrofitService();
        KakaoUserAPI kakaoUserAPI = retrofitService.getRetrofit().create(KakaoUserAPI.class);

        KakaoUser kakaoUser = new KakaoUser();
        kakaoUser.setToken(token);

        kakaoUserAPI.saveKakaoUserInfo(kakaoUser)
                .enqueue(new Callback<KakaoUserInfo>() {
                    @Override
                    public void onResponse(Call<KakaoUserInfo> call, Response<KakaoUserInfo> response) {
                        if (response.isSuccessful()) {
                            // Log for successful reception of data from the server
                            Log.i("KakaoUserAPI", "Successfully received user info from server.");
                        } else {
                            // Log for a response from the server, but not successful
                            Log.i("KakaoUserAPI", "Received response from server, but not successful. Response code: " + response.code());
                        }
                        Toast.makeText(MainActivity.this, "Save Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<KakaoUserInfo> call, Throwable t) {
                        // Log for failure in communication with the server
                        Log.e("KakaoUserAPI", "Failed to communicate with server: " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Save failed", Toast.LENGTH_LONG).show();
                    }
                });

        // Log for successful sending of data to the server
        Log.i("KakaoUserAPI", "Token sent to server successfully.");
    }


}


