package com.example.login.ActualActivities.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.ActualActivities.Functions.CommentAdapter;
import com.example.login.ActualActivities.Functions.MyCommentAdapter;
import com.example.login.ActualActivities.Movie.MovieComment;
import com.example.login.ActualActivities.Movie.MovieSearchAPI;
import com.example.login.Membership.OurUser.SharedViewModel;
import com.example.login.Membership.kakao.RetrofitService;
import com.example.login.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TwoFragment extends Fragment {

    public RecyclerView recyclerView;
    public CommentAdapter adapter;
    public String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView = view.findViewById(R.id.commentRecyclerView);
        // Do UI setup here but don't populate RecyclerView yet
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getTossUserid().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String userId) {
                userid = userId;
                // Now that userid is confirmed to be available, populate RecyclerView
                if (userid != null && !userid.isEmpty()) {
                    getAllmyComments(userid);
                }
            }
        });
    }



    private void getAllmyComments(String userid){
        RetrofitService retrofitService = new RetrofitService();
        MovieSearchAPI movieSearchAPI = retrofitService.getRetrofit().create(MovieSearchAPI.class);

        movieSearchAPI.loadCommentbyId(userid)
                .enqueue(new Callback<List<MovieComment>>() {
                    @Override
                    public void onResponse(Call<List<MovieComment>> call, Response<List<MovieComment>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<MovieComment>> call, Throwable t) {
                        Toast.makeText( getActivity() , "Failed to load users", Toast.LENGTH_LONG).show();
                    }
                }); //
    }

    private void populateListView(List<MovieComment> movieCommentList){
        MyCommentAdapter myCommentAdapter = new MyCommentAdapter(movieCommentList);
        recyclerView.setAdapter(myCommentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


}
