package com.example.login.ActualActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.os.Bundle;
import android.view.View;

import com.example.login.ActualActivities.Functions.PagerAdapter;
import com.example.login.R;
import com.example.login.databinding.ActivityRealMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RealMainActivity extends AppCompatActivity {

    private ActivityRealMainBinding binding;
    private ViewPager2 viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;


    // 생성자가 끝나면 자동으로 onCreate 실행
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRealMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setViewPager();
        setTabLayout();
    }

    private void setViewPager() {
        viewPager = binding.pager;
        pagerAdapter = new PagerAdapter(this);
        pagerAdapter.createFragment(0);
        pagerAdapter.createFragment(1);
        pagerAdapter.createFragment(2);
        viewPager.setAdapter(pagerAdapter);

    }

    private void setTabLayout() {
        tabLayout = binding.tabLayout;
        String[] tabTitles = {"Search", "My List", "Game"};
        int[] tabIcons = {R.drawable.baseline_local_movies_24, R.drawable.baseline_person_2_24, R.drawable.baseline_videogame_asset_24};

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    tab.setText(tabTitles[position]); // 텍스트 설정
                    tab.setIcon(tabIcons[position]); // 이미지 설정
                }
                ).attach();
    }
}