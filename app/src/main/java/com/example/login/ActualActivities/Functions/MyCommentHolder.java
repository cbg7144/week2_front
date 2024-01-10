package com.example.login.ActualActivities.Functions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;

public class MyCommentHolder extends RecyclerView.ViewHolder {

    TextView linecomment, longcomment;

    public MyCommentHolder(@NonNull View itemView){
        super(itemView);
        linecomment = itemView.findViewById(R.id.oneLineMyComment);
        longcomment = itemView.findViewById(R.id.longLineMyComment);

    }


}
