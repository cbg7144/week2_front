package com.example.login.MovieInfoAndComment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.ActualActivities.Functions.CommentAdapter;
import com.example.login.ActualActivities.Movie.Movie;
import com.example.login.ActualActivities.Movie.MovieComment;
import com.example.login.ActualActivities.Movie.MovieSearchAPI;
import com.example.login.Membership.MainActivity;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ShowMovieInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_info);
        // Retrieve the docid from the intent
        String docid = getIntent().getStringExtra("tossDocid");
        String userid = getIntent().getStringExtra("tossUserid");
        //Toast.makeText(ShowMovieInfo.this, userid, Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                int width = recyclerView.getWidth();
                int height = recyclerView.getHeight();
                Log.d("ShowMovieInfo", "RecyclerView Width: " + width + " Height: " + height);
            }
        });

        // 버튼 누르면 평 쓰는 창으로 넘어가기
        FloatingActionButton fab = findViewById(R.id.fadmovie);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMovieInfo.this, WritingMovieComment.class);
                intent.putExtra("tossDocid", docid);
                intent.putExtra("tossUserid", userid);
                startActivity(intent);
                finish();
            }
        });

        // 서버랑 통신해서 Movie에 관한 정보 담아오기
        getMovieInfo(docid);


    }

    // 서버랑 통신해서 Movie 관한 정보 담아오기
    private void getMovieInfo(String docid){
        //        @GET("/무엇을 입력할까요/")
        //        Call<Movie> getMovie(@Query("docidString") String docid);
        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        movieSearchAPI.getMovie(docid)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {

                        Movie movie = response.body();

                        if(movie != null){

                        TextView titleTextView = findViewById(R.id.titleShowMovieInfo);
                        titleTextView.setText(movie.getTitle());

                        TextView genreTextView = findViewById(R.id.genreShowMovieInfo);
                        genreTextView.setText("장르          "+movie.getGenre());

                        TextView runtimeTextView = findViewById(R.id.runtimeShowMovieInfo);
                        runtimeTextView.setText("상영시간   "+Integer.toString(movie.getRuntime())+"(분)");

                        TextView rlsDateTextView = findViewById(R.id.rlsDateShowMovieInfo);
                        String inputFormat = "yyyyMMdd";
                        String outputFormat = "yyyy/MM/dd";
                        String outputString = convertDateFormat(movie.getReRlsDate(), inputFormat, outputFormat);
                        rlsDateTextView.setText("개봉일자   "+outputString);

                        TextView ratingTextView = findViewById(R.id.ratingShowMovieInfo);
                        ratingTextView.setText("상영등급   "+movie.getRating());

                        TextView dirNmTextView = findViewById(R.id.dirNmShowMovieInfo);
                        dirNmTextView.setText("감독          "+movie.getDirectorNm());

                        TextView actorTextView = findViewById(R.id.actorShowMovieInfo);
                        actorTextView.setText("배우          "+movie.getActor1()+", "+movie.getActor2());

                        // Load the poster image using Picasso
                        String PosterimgURL = movie.getPosterUrl();
                        ImageView poster = findViewById(R.id.PosterShowMovieInfo);
                        Picasso.get().load(PosterimgURL).into(poster);

                        // Load the StillCut image using Picasso
                        String StillURL = movie.getStillUrl();
                        ImageView stillCut = findViewById(R.id.StealCutShowMovieInfo);
                        Picasso.get().load(StillURL).into(stillCut);

                        // 관련된 영화 comment 다 들고오는 함수 실행
                        String title = movie.getTitle();
                        bringAllComments(title);

                        } else {
                            Log.e("ShowMovieInfo", "Movie data is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Toast.makeText( ShowMovieInfo.this, "Failed to load movie info", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static String convertDateFormat(String inputDate, String inputFormat, String outputFormat) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if there's an error in parsing
        }
    }

    // 영화 comment 관련 모든 거 긁어오는 함수
    private void bringAllComments(String title){

        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        movieSearchAPI.getAllMovieComment(title)
                .enqueue(new Callback<List<MovieComment>>() {
                    @Override
                    public void onResponse(Call<List<MovieComment>> call, Response<List<MovieComment>> response) {
                        if (response.body() != null ){
                            Log.e("MovieSearchAPI", "Response body is not null");
                            populateListView(response.body());
                        } else {
                            Log.e("MovieSearchAPI", "Response body is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MovieComment>> call, Throwable t) {
                        Toast.makeText( ShowMovieInfo.this , "Get failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void populateListView(List<MovieComment> movieCommentList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if(recyclerView != null) {
            CommentAdapter commentAdapter = new CommentAdapter(movieCommentList);
            recyclerView.setAdapter(commentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.e("ShowMovieInfo", "RecyclerView is null");
        }
    }
}