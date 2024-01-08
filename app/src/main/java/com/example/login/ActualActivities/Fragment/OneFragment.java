package com.example.login.ActualActivities.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.ActualActivities.Functions.MovieAdapter;
import com.example.login.ActualActivities.Movie.Movie;
import com.example.login.ActualActivities.Movie.MovieSearchAPI;
import com.example.login.ActualActivities.Movie.SearchMovie;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.MovieInfoAndComment.ShowMovieInfo;
import com.example.login.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneFragment extends Fragment {

    public RecyclerView recyclerView;
    public MovieAdapter adapter;
    EditText searchContact;


    @Override // 뷰 객체가 반환된 직후에 호출, 뷰가 완전히 생성되었음을 보장
    public void onViewCreated(@NonNull View view, Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        searchContact = view.findViewById(R.id.searchContact);

        getAllMovies();

        // 버튼을 클릭하지 말고 전 프로젝트에서 자동으로 반영해주는 기능 사용하면 어떨까??
        Button getButton = view.findViewById(R.id.btnGet);
        getButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            //{getAllMovies();}
            {showSearchedMovie(v);}
        });

        return view;
    }

    private void showSearchedMovie(View view){
        String searchword = searchContact.getText().toString().trim();
        getSearchedMovie(searchword);
    }

    private void getSearchedMovie(String searchword){
        // 검색명 출력하는 로그 만들어 보기
        Log.i("getSearchMovie", searchword);
        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        SearchMovie searchMovie = new SearchMovie();
        searchMovie.setSearchword(searchword);

        movieSearchAPI.searchMovie(searchword)
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        if (response.body() != null) {
                            populateListView(response.body());
                        } else {
                            // Handle null case, maybe log or show an error message
                            Log.e("MovieSearchAPI", "Response body is null");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        // Log for failure in communication with the server
                        Log.e("MovieSearchAPI", "Failed to communicate with server: " + t.getMessage());
                        Toast.makeText( getActivity(), "Save failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getAllMovies(){
        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        movieSearchAPI.getAllMovies()
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {
                        Toast.makeText( getActivity() , "Failed to load users", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void populateListView(List<Movie> movieList){
        MovieAdapter movieAdapter = new MovieAdapter(movieList);
        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Handle the click event for the movie item
                Movie clickedMovie = movieList.get(position);
                Toast.makeText(getActivity(), "Clicked on: " + clickedMovie.getTitle(), Toast.LENGTH_SHORT).show();
                String tossDocid = clickedMovie.getDocid();
                // You can also start a new activity or fragment here
                // For example, to show detailed information about the clicked movie

                Intent intent = new Intent(getActivity(), ShowMovieInfo.class);
                intent.putExtra("tossDocid", tossDocid);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
