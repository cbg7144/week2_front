package com.example.login.MovieInfoAndComment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.ActualActivities.Movie.Movie;
import com.example.login.ActualActivities.Movie.MovieComment;
import com.example.login.ActualActivities.Movie.MovieSearchAPI;
import com.example.login.ActualActivities.Movie.SendingMovieComment;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingMovieComment extends AppCompatActivity {

    EditText oneLineComment, fullComment;
    RatingBar ratingBar;
    Button sendCommentBtn;
    int intRating = 0;
    String movieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_movie_comment);

        // Retrieve the docid from the intent
        String docid = getIntent().getStringExtra("tossDocid");
        Log.i("WritingMovieComment", docid);

        getMovieInfo(docid);

        // 영화평 작성하는 버튼 누르면
        oneLineComment = findViewById(R.id.OneLineComment);
        fullComment = findViewById(R.id.DetailedComment);
        ratingBar = findViewById(R.id.ratingBar);
        sendCommentBtn = findViewById(R.id.CommentEnterButton);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Convert the rating to an integer and store it in the member variable
                intRating = (int) rating;
                // Show the integer rating in a Toast message
                Toast.makeText(getApplicationContext(), "New Rating: " + intRating, Toast.LENGTH_SHORT).show();
            }
        });

        sendCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extracting string values from EditText objects
                String oneLineCommentText = oneLineComment.getText().toString();
                String fullCommentText = fullComment.getText().toString();

                // Passing string values instead of EditText objects
                sendCommentToServer(oneLineCommentText, fullCommentText, intRating, movieTitle, docid);
            }
        });


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

                            TextView titleTextView = findViewById(R.id.CommentTitle);
                            titleTextView.setText(movie.getTitle());

                            // Load the poster image using Picasso
                            String PosterimgURL = movie.getPosterUrl();
                            ImageView poster = findViewById(R.id.CommentPoster);
                            Picasso.get().load(PosterimgURL).into(poster);

                            movieTitle = movie.getTitle();

                        } else {
                            Log.e("ShowMovieInfo", "Movie data is null");
                        }
                    }
                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        Toast.makeText( WritingMovieComment.this, "Failed to load users", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void sendCommentToServer(String oneLineComment, String fullComment, int intRating, String movieTitle, String docid ){
        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        SendingMovieComment sendingMovieComment = new SendingMovieComment();
        sendingMovieComment.setMovieTitle(movieTitle);
        sendingMovieComment.setLinecomment(oneLineComment);
        sendingMovieComment.setScore(intRating);
        sendingMovieComment.setLongcomment(fullComment);

        movieSearchAPI.saveComment(sendingMovieComment)
                .enqueue(new Callback<MovieComment>() {
                    @Override
                    public void onResponse(Call<MovieComment> call, Response<MovieComment> response) {
                        if(response.body() != null){
                            Log.e("ShowMovieInfo", "MovieComment is not null");

                            Intent intent = new Intent(WritingMovieComment.this, ShowMovieInfo.class);
                            intent.putExtra("tossDocid", docid);
                            startActivity(intent);
                            finish();

                        } else {
                            Log.e("ShowMovieInfo", "MovieComment is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieComment> call, Throwable t) {
                        Toast.makeText( WritingMovieComment.this, "Failed to load users", Toast.LENGTH_LONG).show();
                    }
                });


    }
}