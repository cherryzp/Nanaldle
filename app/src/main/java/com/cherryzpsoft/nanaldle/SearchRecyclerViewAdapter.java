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

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<TagItem> items;

    public SearchRecyclerViewAdapter(Context context, ArrayList<TagItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardview_search_tag, parent, false);
        RecyclerView.ViewHolder holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        TagItem item = items.get(position);

        vh.tvTagItem.setText("#"+item.getTag());
        vh.tvTagCount.setText(item.getTagCount() + " 게시물");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvTagItem;
        TextView tvTagCount;

        public VH(View itemView) {
            super(itemView);

            tvTagItem = itemView.findViewById(R.id.tv_tag_item);
            tvTagCount = itemView.findViewById(R.id.tv_tag_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailTagSearchActivity.class);

                    intent.putExtra("tag", items.get(getLayoutPosition()).getTag());
                    context.startActivity(intent);
                }
            });

        }
    }

}
