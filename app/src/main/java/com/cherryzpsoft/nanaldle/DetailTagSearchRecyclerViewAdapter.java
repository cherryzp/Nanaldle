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

public class DetailTagSearchRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<WriteItem> items;

    public DetailTagSearchRecyclerViewAdapter(Context context, ArrayList<WriteItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_detail_tag_search, parent, false);
        RecyclerView.ViewHolder holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        WriteItem item = items.get(position);
        vh.tvContents.setText(item.getContent());
        vh.tvDate.setText(item.getDate());
        vh.tvComments.setText(item.getCommentCount() + " 댓글");
        vh.tvLike.setText(item.getLikeCount() + " 좋아요");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvContents;
        TextView tvDate;
        TextView tvComments;
        TextView tvLike;

        public VH(View itemView) {
            super(itemView);

            tvContents = itemView.findViewById(R.id.tv_contents);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvComments = itemView.findViewById(R.id.tv_comments);
            tvLike = itemView.findViewById(R.id.tv_like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailContentActivity.class);
                    intent.putExtra("date", items.get(getLayoutPosition()).getDate());

                    context.startActivity(intent);
                }
            });

        }
    }

}
