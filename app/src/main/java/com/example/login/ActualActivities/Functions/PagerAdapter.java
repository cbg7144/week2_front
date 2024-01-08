package com.example.login.ActualActivities.Functions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.login.ActualActivities.Fragment.OneFragment;
import com.example.login.ActualActivities.Fragment.ThreeFragment;
import com.example.login.ActualActivities.Fragment.TwoFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

    public PagerAdapter(AppCompatActivity activity){
        super(activity);
        // 프래그먼트를 미리 생성하여 리스트에 추가
        mFragmentList.add(new OneFragment());
        mFragmentList.add(new TwoFragment());
        mFragmentList.add(new ThreeFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        //해당 위치의 프래그먼트를 반환
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount(){return mFragmentList.size();}
}
