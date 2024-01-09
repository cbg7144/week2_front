package com.example.login.ActualActivities.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.login.ActualActivities.Movie.MovieGame;
import com.example.login.ActualActivities.Movie.MovieSearchAPI;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.MovieInfoAndComment.ShowMovieInfo;
import com.example.login.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreeFragment extends Fragment {

    // 초기 세팅 불변 값
    Button ansBtn, nextBtn;
    EditText entBoard;

    //초기 세팅 변하는 값
    int numOfQuestion = 0;
    int numOfAns = 0;
    String answerOfQuestion ="";
    MovieGame movieGame;

    // 1] 정보 받아오기, 2] 필요한 정보 띄우기
    // 4] 다음 버튼 누르면, 새로운 영화 게임 가져오기  // with Visible to inVisible
    // 5] Terminal Point?? => 처음에 for구문 사용하면 되지 않을까?
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Layout 보여주기
        View view = inflater.inflate(R.layout.fragment_three, container, false);

        // 점수 창 - int to String //////


        // 정보 받아오기
        // 원래의 값 : numOfQuestion + 1
        getMovieQuestionInfo(numOfQuestion + 1, view);

        // 버튼 띄우기
        ansBtn = view.findViewById(R.id.AnsBtn);
        nextBtn = view.findViewById(R.id.NextBtn);

        // 입력창 띄우기
        entBoard = view.findViewById(R.id.EnterBoard);

        // 입력 버튼 누르고 Gone -> Visible로 그리고 Visible -> inVisible로
        ansBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               compareWithAnswer(v);
            }
        });


        // 다음 버튼 누르고 visibility 바꾸고 다음 질문으로 넘어가기
        nextBtn = view.findViewById(R.id.NextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });

        // 마지막 return
        return view;
    }

    private void getMovieQuestionInfo(int i, View v){
        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        String i_str = Integer.toString(i);
        Log.e("getMovieQuestionInfo", i_str);


        movieSearchAPI.getMovieGameInfo(i_str)
                .enqueue(new Callback<MovieGame>() {
                    @Override
                    public void onResponse(Call<MovieGame> call, Response<MovieGame> response) {
                        movieGame = response.body();
                        if(movieGame != null){
                            // 초성퀴즈 불러오기
                            TextView realQuizTextView = v.findViewById(R.id.RealQuiz);
                            realQuizTextView.setText(movieGame.getQuestion());

                            // 답_포스터 불러오기
                            String PosterImgURL = movieGame.getPosterUrl();
                            ImageView poster = v.findViewById(R.id.AnsPoster);
                            Picasso.get().load(PosterImgURL).into(poster);

                            // 답 변수 저장
                            answerOfQuestion = movieGame.getAnswer();
                            TextView ansTextView = v.findViewById(R.id.AnsTitle);
                            ansTextView.setText(answerOfQuestion);

                        } else {
                            Toast.makeText( getActivity(), "Failed to load movie game info", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<MovieGame> call, Throwable t) {
                        Toast.makeText( getActivity(), "Failed to load movie info", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void compareWithAnswer(View view) {
        String submissionMovie = entBoard.getText().toString().trim();
        if (submissionMovie.equals("")) {
            Toast.makeText(getActivity(), "Submission is empty, try again", Toast.LENGTH_SHORT).show();
        } else {
            // Gone -> Visible and Visible -> Invisible
            visibilityConverter();

            // Check answer and increment numOfAns if correct
            if (submissionMovie.equals(movieGame.getAnswer())) {
                numOfAns++;
                Log.e("Correct Answer", "Number of Correct Answers: " + numOfAns);
            }

            // Prepare for the next question
            if (numOfQuestion < 5) {
                numOfQuestion++;
                Log.e("Next Question", "Question Number: " + numOfQuestion);
            }
        }
    }


    private void loadNextQuestion() {
        // Increment question number and check if it's the end of the game
            // numOfQuestion++;
        if (numOfQuestion >= 5) { // Assuming there are 5 questions in total
            showTotalScore();
            showEndGame();
        } else {
            getMovieQuestionInfo(numOfQuestion, getView()); // Fetch next question
            visibilityConverter();
            showTotalScore();
        }
    }

    private void visibilityConverter(){
        ImageView ansPoster = getView().findViewById(R.id.AnsPoster);
        TextView ansTitle = getView().findViewById(R.id.AnsTitle);
        Button nextBtn = getView().findViewById(R.id.NextBtn);

        // Toggling visibility of AnsPoster
        if (ansPoster.getVisibility() == View.VISIBLE) {
            ansPoster.setVisibility(View.GONE);
        } else {
            ansPoster.setVisibility(View.VISIBLE);
        }
        // Toggling visibility of AnsTitle
        if (ansTitle.getVisibility() == View.VISIBLE) {
            ansTitle.setVisibility(View.GONE);
        } else {
            ansTitle.setVisibility(View.VISIBLE);
        }
        // Toggling visibility of NextBtn
        if (nextBtn.getVisibility() == View.VISIBLE) {
            nextBtn.setVisibility(View.GONE);
        } else {
            nextBtn.setVisibility(View.VISIBLE);
        }
    }

    private void showEndGame(){
        // 게임이 끝나면, 새로운 창을 실행? 아니면 retry 함수 실행 시킬까??
        Log.e("showEndGame", "");
        Toast.makeText(getActivity(), "Game Over! Your score: " + numOfAns, Toast.LENGTH_LONG).show();
        numOfAns = 0;
        numOfQuestion = 0;
    }

    private void showTotalScore(){
        TextView totalScore = getView().findViewById(R.id.ScoreBoard);
        totalScore.setText(Integer.toString(numOfAns)+"/"+Integer.toString(numOfQuestion));
    }
}
