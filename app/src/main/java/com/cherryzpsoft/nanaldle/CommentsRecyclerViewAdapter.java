package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<String> items;

    public CommentsRecyclerViewAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);


        }

    }
}
