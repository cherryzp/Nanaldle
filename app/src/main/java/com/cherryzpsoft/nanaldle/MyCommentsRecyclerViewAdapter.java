package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCommentsRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MyCommentsItem> items;

    public MyCommentsRecyclerViewAdapter(Context context, ArrayList<MyCommentsItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mypage_comments, parent, false);
        RecyclerView.ViewHolder holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        MyCommentsItem item = items.get(position);
        vh.tvContents.setText(item.getContents());
        vh.tvComments.setText(item.getComments());
        vh.tvDate.setText(item.getcDate());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvContents;
        TextView tvComments;
        TextView tvDate;

        public VH(View itemView) {
            super(itemView);

            tvContents = itemView.findViewById(R.id.tv_contents);
            tvComments = itemView.findViewById(R.id.tv_comments);
            tvDate = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailContentActivity.class);
                    intent.putExtra("date", items.get(getLayoutPosition()).getmDate());

                    context.startActivity(intent);
                }
            });

        }
    }

}
