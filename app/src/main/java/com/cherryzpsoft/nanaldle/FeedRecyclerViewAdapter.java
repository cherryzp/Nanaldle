package com.cherryzpsoft.nanaldle;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
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
        vh.ivEmoticon.setImageResource(R.drawable.emoticon_01+Integer.parseInt(item.getEmoticon()));
        if(item.getImg()!=null){
            Glide.with(context).load(item.getImg()).into(vh.ivContent);
            vh.ivContent.setVisibility(View.VISIBLE);
        } else {
            vh.ivContent.setVisibility(View.GONE);
        }
        vh.tvContent.setText(item.getContent());
        vh.tvTag.setText("#"+item.getTag());

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
        TextView tvTag;
        ToggleButton likeBtn;

        public VH(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.date_card_feed);
            ivEmoticon = itemView.findViewById(R.id.emoticon_card_feed);
            ivContent = itemView.findViewById(R.id.img_content_feed);
            tvContent = itemView.findViewById(R.id.text_content_feed);
            tvTag = itemView.findViewById(R.id.text_tag_feed);

            likeBtn = itemView.findViewById(R.id.btn_like);
            likeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    switch (buttonView.getId()){
                        case R.id.btn_like:
                            if(isChecked){

                                String serverUrl = "http://win9101.dothome.co.kr/nanaldle/likeCheckDB.php";

                                SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        new AlertDialog.Builder(context).setMessage(response).setPositiveButton("예", null).create().show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                                multiPartRequest.addStringParam("date", items.get(getLayoutPosition()).getDate());
                                multiPartRequest.addStringParam("email", context.getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null") );

                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(multiPartRequest);

                            } else {

                                String serverUrl = "http://win9101.dothome.co.kr/nanaldle/likeUncheckDB.php";

                                SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        new AlertDialog.Builder(context).setMessage(response).setPositiveButton("예", null).create().show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                                multiPartRequest.addStringParam("date", items.get(getLayoutPosition()).getDate());

                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                requestQueue.add(multiPartRequest);

                            }

                            break;
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DetailContentActivity.class);

                    intent.putExtra("date", items.get(getLayoutPosition()).getDate());
                    intent.putExtra("emoticon", items.get(getLayoutPosition()).getEmoticon());
                    intent.putExtra("img_content", items.get(getLayoutPosition()).getImg());
                    intent.putExtra("tv_content", items.get(getLayoutPosition()).getContent());
                    intent.putExtra("tag", items.get(getLayoutPosition()).getTag());
                    intent.putExtra("like_count", items.get(getLayoutPosition()).getLikeCount());

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)context, new Pair<View, String>(ivContent, "IMG"));
                        context.startActivity(intent, options.toBundle());
                    } else {
                        context.startActivity(intent);
                    }

                }
            });

        }

    }

}
