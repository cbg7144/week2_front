package com.example.login.ActualActivities.Functions;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

public class MovieHolder extends RecyclerView.ViewHolder {
    TextView docid, title, directorNm, genre;
    ImageView poster;

    public MovieHolder(@NonNull View itemView, final MovieAdapter.OnItemClickListener listener) {
        super(itemView);
        docid = itemView.findViewById(R.id.userListItem_ID);
        title = itemView.findViewById(R.id.userListItem_Password);
        directorNm = itemView.findViewById(R.id.userListItem_Name);
        genre = itemView.findViewById(R.id.oneLineCommentShowMovieInfo);
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
