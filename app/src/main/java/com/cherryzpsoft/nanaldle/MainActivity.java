package com.cherryzpsoft.nanaldle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    final int MENU_FEED = 0;
    final int MENU_SEARCH = 1;
    final int MENU_WRITE = 2;
    final int MENU_ALERT = 3;
    final int MENU_MYPAGE = 4;

    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationBar = findViewById(R.id.nav_bar);

        setBottomNavigationBar();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, new FeedFragment()).commit();

        selectedBottomNavigationBar();

    }

    public void setBottomNavigationBar(){

        bottomNavigationBar.addItem(new BottomNavigationItem(android.R.drawable.ic_dialog_dialer, null));
        bottomNavigationBar.addItem(new BottomNavigationItem(android.R.drawable.ic_menu_search, null));
        bottomNavigationBar.addItem(new BottomNavigationItem(android.R.drawable.ic_input_add, null));
        bottomNavigationBar.addItem(new BottomNavigationItem(android.R.drawable.ic_popup_reminder, null));
        bottomNavigationBar.addItem(new BottomNavigationItem(android.R.drawable.ic_menu_myplaces, null));
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED_NO_TITLE);
        bottomNavigationBar.setActiveColor(R.color.color_black);
        bottomNavigationBar.initialise();

    }

    public void selectedBottomNavigationBar(){

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position) {

                Fragment selectedFragment = null;

                switch (position){
                    case MENU_FEED:
                        selectedFragment = new FeedFragment();
                        break;

                    case MENU_SEARCH:
                        selectedFragment = new SearchFragment();
                        break;

                    case MENU_WRITE:
                        startActivity(new Intent(MainActivity.this, WriteActivity.class));
                        overridePendingTransition(R.anim.enter_write_activity, R.anim.exit_write_activity);
                        return;

                    case MENU_ALERT:
                        selectedFragment = new AlertFragment();
                        break;

                    case MENU_MYPAGE:
                        selectedFragment = new MyPageFragment();
                        break;
                }

                if(selectedFragment!=null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, selectedFragment).commit();
                }

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

                if(position==MENU_WRITE) {
                    startActivity(new Intent(MainActivity.this, WriteActivity.class));
                    overridePendingTransition(R.anim.enter_write_activity, R.anim.exit_write_activity);
                }

            }
        });

    }
}
