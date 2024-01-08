package com.example.login.MovieInfoAndComment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.login.R;

public class WritingMovieComment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_movie_comment);

        // Retrieve the docid from the intent
        String docid = getIntent().getStringExtra("tossDocid");
        Log.i("WritingMovieComment", docid);


        // 영화평 작성하는 버튼 누르면 다시 RealMainActivity로 돌아가게 만들어야 할 듯
    }
}