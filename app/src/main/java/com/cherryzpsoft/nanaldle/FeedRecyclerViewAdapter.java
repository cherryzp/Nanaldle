package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<WriteItem> items;

    public FeedRecyclerViewAdapter(Context context, ArrayList<WriteItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_feed, parent, false);
        RecyclerView.ViewHolder holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        WriteItem item = items.get(position);
        vh.tvDate.setText(item.getDate());
        vh.ivEmoticon.setImageResource(R.drawable.emoticon_01);
        if(item.getImg()!=null){
            Glide.with(context).load(item.getImg()).into(vh.ivContent);
        }
        vh.tvContent.setText(item.getContent());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvDate;
        ImageView ivEmoticon;
        ImageView ivContent;
        TextView tvContent;

        public VH(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.date_card_feed);
            ivEmoticon = itemView.findViewById(R.id.emoticon_card_feed);
            ivContent = itemView.findViewById(R.id.img_content_feed);
            tvContent = itemView.findViewById(R.id.text_content_feed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
