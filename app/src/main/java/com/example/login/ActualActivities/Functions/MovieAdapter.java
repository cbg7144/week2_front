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


    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position){
        Movie movie = movieList.get(position);
        holder.docid.setText(movie.getDocid());
        holder.title.setText(movie.getTitle());
        holder.directorNm.setText(movie.getDirectorNm());
        holder.genre.setText(movie.getGenre());

        // Load the poster image using Picasso
        String imageUrl = movie.getPosterUrl(); // Assuming getPosterUrl() is a method in your Movie class
        Picasso.get().load(imageUrl).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    } // movieList 크기 변환
}
