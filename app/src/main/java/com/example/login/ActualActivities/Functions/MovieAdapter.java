package com.example.login.ActualActivities.Functions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.ActualActivities.Movie.Movie;
import com.example.login.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{

    private List<Movie> movieList;


    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // Listener variable
    private OnItemClickListener listener;

    // Method to set the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_searched_movie_item, parent, false);
        return new MovieHolder(view, listener); // Pass the listener to the MovieHolder
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


    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position){
        Movie movie = movieList.get(position);
        holder.runtime.setText(Integer.toString(movie.getRuntime())+"(분)");
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());

        // Load the poster image using Picasso
        String imageUrl = movie.getPosterUrl(); // Assuming getPosterUrl() is a method in your Movie class
        Picasso.get().load(imageUrl).into(holder.poster);


        String inputFormat = "yyyyMMdd";
        String outputFormat = "yyyy/MM/dd";
        String outputString = convertDateFormat(movie.getReRlsDate(), inputFormat, outputFormat);
        holder.reRlsDate.setText(outputString);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    } // movieList 크기 변환
}
