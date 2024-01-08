package com.example.login.ActualActivities.Movie;

import java.util.List;

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

    //Docid를 주면, movie class 한개 받아오는 함수 만들기
    @GET("/movie/view")
    Call<Movie> getMovie(@Query("docid") String docid);

    //title을 주면, movie comment list 가져오는 함수
    @GET("/comment/load")
    Call<List<MovieComment>> getAllMovieComment(@Query("searchTitle") String title);

    @POST("/comment/save")
    Call<MovieComment> saveComment(@Body SendingMovieComment sendingMovieComment);

}
