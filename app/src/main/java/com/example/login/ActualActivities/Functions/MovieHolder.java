package com.example.login.ActualActivities.Functions;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

public class MovieHolder extends RecyclerView.ViewHolder {
    TextView runtime, title, reRlsDate, genre;
    ImageView poster;

    public MovieHolder(@NonNull View itemView, final MovieAdapter.OnItemClickListener listener) {
        super(itemView);
        // 상영시간
        runtime = itemView.findViewById(R.id.userListItem_ID);
        //개봉일자
        reRlsDate = itemView.findViewById(R.id.userListItem_Name);

        // 제목
        title = itemView.findViewById(R.id.userListItem_Password);
        // 장르
        genre = itemView.findViewById(R.id.oneLineCommentShowMovieInfo);
        // 포스터
        poster = itemView.findViewById(R.id.poster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, position);
                }
            }
        });


    }
}
