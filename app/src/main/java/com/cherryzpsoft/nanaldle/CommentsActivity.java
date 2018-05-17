package com.cherryzpsoft.nanaldle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<>();
    String comments;

    RecyclerView recyclerView;
    CommentsRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        recyclerView = findViewById(R.id.recyclerview_comments);
        recyclerViewAdapter = new CommentsRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

}
