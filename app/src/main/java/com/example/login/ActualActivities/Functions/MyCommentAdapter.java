package com.example.login.ActualActivities.Functions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.ActualActivities.Movie.MovieComment;
import com.example.login.R;

import java.util.List;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentHolder> {

    private List<MovieComment> myCommentList;

    public MyCommentAdapter(List<MovieComment> movieCommentList){
        this.myCommentList = movieCommentList;
    }

    @NonNull
    @Override
    public MyCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_movie_comment_item, parent, false);
        return new MyCommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCommentHolder holder, int position){
        MovieComment movieComment = myCommentList.get(position);
        holder.linecomment.setText(movieComment.getLinecomment());
        holder.longcomment.setText(movieComment.getLongcomment());
        float ratingValue = movieComment.getScore();
        RatingBar ratingBar = holder.itemView.findViewById(R.id.ratingBarcomment);
        ratingBar.setIsIndicator(true);
        ratingBar.setRating(ratingValue);
    }

    @Override
    public int getItemCount() {return myCommentList.size();}

}
