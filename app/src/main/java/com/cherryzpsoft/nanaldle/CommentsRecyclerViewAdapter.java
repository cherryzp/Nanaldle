package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        View view = LayoutInflater.from(context).inflate(R.layout.cardview_comments, parent, false);
        RecyclerView.ViewHolder holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        vh.tvComments.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvComments;

        public VH(View itemView) {
            super(itemView);
            tvComments = itemView.findViewById(R.id.text_comments);
        }
    }
}
