package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyPageFragment extends Fragment {

    TextView logoutBtn;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        tabLayout = view.findViewById(R.id.layout_tab_mypage);

        logoutBtn = view.findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(logoutClickListener);

        viewPager = view.findViewById(R.id.pager_view_mypage);
        connectAdapter = new ConnectFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(connectAdapter);

        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    View.OnClickListener logoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity()).setMessage("로그아웃 하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                }
            }).setNegativeButton("아니오", null).create().show();
        }
    };

}
