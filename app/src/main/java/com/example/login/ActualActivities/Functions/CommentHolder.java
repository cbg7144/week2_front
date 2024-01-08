package com.example.login.ActualActivities.Functions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

public class CommentHolder extends RecyclerView.ViewHolder {
    TextView oneLineComment;

    public CommentHolder(@NonNull View itemView){
        super(itemView);
        oneLineComment = itemView.findViewById(R.id.oneLineCommentShowMovieInfo);
        // 음 Rating Bar는 어떻게 해야할까???
    }

}
