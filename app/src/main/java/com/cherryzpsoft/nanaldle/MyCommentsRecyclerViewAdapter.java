package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyCommentsRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<WriteItem> items;

    public MyCommentsRecyclerViewAdapter(Context context, ArrayList<WriteItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_comments, parent, false);
        RecyclerView.ViewHolder holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{
        public VH(View itemView) {
            super(itemView);

        }
    }

}
