package com.cherryzpsoft.nanaldle;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

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
        vh.likeBtn.setChecked(item.isLiked());
        vh.bookmarkBtn.setChecked(item.isBookmarked());
        vh.tvDate.setText(item.getDate());
        vh.ivEmoticon.setImageResource(R.drawable.emoticon_01 + Integer.parseInt(item.getEmoticon()));
        vh.tvLike.setText(item.getLikeCount() + " 좋아요");
        vh.tvComments.setText(item.getCommentCount() + " 댓글");
        if (item.getImg() != null) {
            Glide.with(context).load(item.getImg()).into(vh.ivContent);
            vh.ivContent.setVisibility(View.VISIBLE);
        } else {
            vh.ivContent.setVisibility(View.GONE);
        }
        vh.tvContent.setText(item.getContent());
        if (item.getTag() != null) {
            vh.tvTag.setText("#" + item.getTag());
            vh.tvTag.setVisibility(View.VISIBLE);
        } else {
            vh.tvTag.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tvDate;
        ImageView ivEmoticon;
        ImageView optionBtn;
        ImageView ivContent;
        TextView tvContent;
        TextView tvTag;
        TextView tvLike;
        TextView tvComments;
        ImageView commentsBtn;
        ToggleButton likeBtn;
        ToggleButton bookmarkBtn;
        RelativeLayout relativeLayoutCommentsBtn;

        public VH(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.date_card_feed);
            ivEmoticon = itemView.findViewById(R.id.emoticon_card_feed);
            optionBtn = itemView.findViewById(R.id.option_btn);
            ivContent = itemView.findViewById(R.id.img_content_feed);
            tvContent = itemView.findViewById(R.id.text_content_feed);
            tvTag = itemView.findViewById(R.id.text_tag_feed);
            tvLike = itemView.findViewById(R.id.text_like_feed);
            commentsBtn = itemView.findViewById(R.id.btn_comments);
            likeBtn = itemView.findViewById(R.id.btn_like);
            tvComments = itemView.findViewById(R.id.text_comments_feed);
            bookmarkBtn = itemView.findViewById(R.id.btn_bookmark);
            relativeLayoutCommentsBtn = itemView.findViewById(R.id.btn_comments_relativelayout);

            optionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //todo: 삭제버튼 연동 email을 통한 자료 검증
//                    PopupMenu popupMenu = new PopupMenu(context, v);
//                    ((Activity)context).getMenuInflater().inflate(R.menu.popup_option_menu, popupMenu.getMenu());
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//
//                            switch (item.getItemId()){
//                                case R.id.delete_item:
//
//                                    break;
//                            }
//                            return false;
//                        }
//                    });
                }
            });

            relativeLayoutCommentsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("date", items.get(getLayoutPosition()).getDate());

                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter_in_activity, R.anim.enter_out_activity);
                }
            });

            commentsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommentsActivity.class);
                    intent.putExtra("date", items.get(getLayoutPosition()).getDate());

                    context.startActivity(intent);
                    ((AppCompatActivity) context).overridePendingTransition(R.anim.enter_in_activity, R.anim.enter_out_activity);
                }
            });

            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(context.getSharedPreferences("LoginData", context.MODE_PRIVATE).getString("email", "null").toString().equals("null")){
                        Toast.makeText(context, "로그인 이후에 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (likeBtn.isChecked()) {

                            String serverUrl = "http://win9101.dothome.co.kr/nanaldle/likeCheckDB.php";

                            SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        tvLike.setText(jsonObject.getString("like_count") + " 좋아요");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
//                                        new AlertDialog.Builder(context).setMessage(response).setPositiveButton("예", null).create().show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            multiPartRequest.addStringParam("date", items.get(getLayoutPosition()).getDate());
                            multiPartRequest.addStringParam("email", context.getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(multiPartRequest);

                        } else {

                            String serverUrl = "http://win9101.dothome.co.kr/nanaldle/likeUncheckDB.php";

                            SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        tvLike.setText(jsonObject.getString("like_count") + " 좋아요");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
//                                        new AlertDialog.Builder(context).setMessage(response).setPositiveButton("예", null).create().show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            multiPartRequest.addStringParam("date", items.get(getLayoutPosition()).getDate());
                            multiPartRequest.addStringParam("email", context.getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(multiPartRequest);

                        }

                    }

                }

            });

            bookmarkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(context.getSharedPreferences("LoginData", context.MODE_PRIVATE).getString("email", "null").toString().equals("null")){
                        Toast.makeText(context, "로그인 이후에 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (bookmarkBtn.isChecked()) {
                            String serverUrl = "http://win9101.dothome.co.kr/nanaldle/bookmarkCheckDB.php";

                            SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    new AlertDialog.Builder(context).setMessage(response).setPositiveButton("예", null).create().show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            multiPartRequest.addStringParam("date", items.get(getLayoutPosition()).getDate());
                            multiPartRequest.addStringParam("email", context.getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(multiPartRequest);
                        } else {
                            String serverUrl = "http://win9101.dothome.co.kr/nanaldle/bookmarkUncheckDB.php";

                            SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    new AlertDialog.Builder(context).setMessage(response).setPositiveButton("예", null).create().show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            multiPartRequest.addStringParam("date", items.get(getLayoutPosition()).getDate());
                            multiPartRequest.addStringParam("email", context.getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            requestQueue.add(multiPartRequest);
                        }
                    }

                }
            });

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
