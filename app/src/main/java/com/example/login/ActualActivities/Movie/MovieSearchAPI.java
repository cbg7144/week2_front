package com.example.login.ActualActivities.Movie;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MovieSearchAPI {

    @GET("/movie/list")
    Call<List<Movie>> getAllMovies();

    @GET("/movie/search")
    Call<List<Movie>> searchMovie(@Query("searchString") String searchString); //////////////////////
//    @GetMapping ("/movie/search")
//    public List<Movie> searchMovie(@RequestParam Map<String, String> body) { // Map<String, String> 빼먹어서 5시간 날림
//        return movieService.searchMovies(body.get("searchString"));
//        ////// movie repository에서 검색기능하는 함수 쓰기
//    }




    //Docid를 주면, movie class 한개 받아오는 함수 만들기
    @GET("/movie/view")
    Call<Movie> getMovie(@Query("docid") String docid);

    //title을 주면, movie comment list 가져오는 함수
    @GET("/comment/load")
    Call<List<MovieComment>> getAllMovieComment(@Query("searchTitle") String title);

    @POST("/comment/save")
    Call<MovieComment> saveComment(@Body SendingMovieComment sendingMovieComment);

    // GameId를 주면, 해당 movie에 대한 정보를 가져옴
    @GET("/game/id")
    Call<MovieGame> getMovieGameInfo(@Query("id") String GameId);
}
