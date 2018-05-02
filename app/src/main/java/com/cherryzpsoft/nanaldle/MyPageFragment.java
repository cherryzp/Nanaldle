package com.cherryzpsoft.nanaldle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPageFragment extends Fragment {

    View view;
    Toolbar toolbar;

    ViewPager viewPager;
    ConnectFragmentPagerAdapter connectAdapter;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        toolbar = view.findViewById(R.id.toolbar_mypage);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        tabLayout = view.findViewById(R.id.layout_tab_mypage);

        viewPager = view.findViewById(R.id.pager_view_mypage);
        connectAdapter = new ConnectFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(connectAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
