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

public class MyBookmarkRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MyBookmarkItem> items;

    public MyBookmarkRecyclerViewAdapter(Context context, ArrayList<MyBookmarkItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_mypage_mybookmark, parent, false);
        RecyclerView.ViewHolder holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        MyBookmarkItem item = items.get(position);
        vh.tvDate.setText(item.getbDate());
        vh.tvContent.setText(item.getContent());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvContent;
        TextView tvDate;

        public VH(View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.tv_contents);
            tvDate = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailContentActivity.class);

                    intent.putExtra("date", items.get(getLayoutPosition()).getcDate());

                    context.startActivity(intent);
                }
            });

        }

    }

}
