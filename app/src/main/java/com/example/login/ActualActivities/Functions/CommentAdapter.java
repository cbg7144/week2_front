package com.example.login.ActualActivities.Functions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.login.ActualActivities.Movie.MovieComment;
import com.example.login.R;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

    private List<MovieComment> commentList;

    public CommentAdapter(List<MovieComment> movieCommentList) {
        this.commentList = movieCommentList;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comment_movie_item, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        MovieComment movieComment = commentList.get(position);
        holder.oneLineComment.setText(movieComment.getLinecomment());
        // If you have a rating bar in your MovieComment, bind it here
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
