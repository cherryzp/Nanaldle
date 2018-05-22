package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailContentActivity extends AppCompatActivity {

    TextView tvDate;
    ImageView ivEmoticon;
    ImageView ivContent;
    TextView tvContent;
    TextView tvTag;
    TextView tvLike;
    TextView tvComments;

    ToggleButton likeBtn;
    ToggleButton bookmarkBtn;
    ImageView commentsBtn;

    String jsonConents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);

        tvDate = findViewById(R.id.date_card_detail);
        ivEmoticon = findViewById(R.id.emoticon_card_detail);
        ivContent = findViewById(R.id.img_content_detail);
        tvContent = findViewById(R.id.text_content_detail);
        tvTag = findViewById(R.id.text_tag_detail);
        tvLike = findViewById(R.id.text_like_detail);
        tvComments = findViewById(R.id.text_comments_detail);

        loadItem();

        likeBtn = findViewById(R.id.btn_like);
        likeBtn.setOnClickListener(likeClickListener);

        commentsBtn = findViewById(R.id.btn_comments);
        commentsBtn.setOnClickListener(commentsListener);

        bookmarkBtn = findViewById(R.id.btn_bookmark);
        bookmarkBtn.setOnClickListener(bookmarkClickListener);
    }

    View.OnClickListener likeClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if (likeBtn.isChecked()) {
                //선택됨
                insertLike();
            } else {
                //선택되지 않음
                deleteLike();
            }

        }
    };

    View.OnClickListener commentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DetailContentActivity.this, CommentsActivity.class);

            intent.putExtra("date", tvDate.getText());
            startActivity(intent);
            overridePendingTransition(R.anim.enter_write_activity, R.anim.exit_write_activity);
        }
    };

    View.OnClickListener bookmarkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(bookmarkBtn.isChecked()){
                //선택됨
                insertBookmark();
            }else{
                //선택되지 않음
                deleteBookmark();
            }
        }
    };

    public void insertLike() {

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

//                new AlertDialog.Builder(DetailContentActivity.this).setMessage(response).setPositiveButton("예", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("date", tvDate.getText().toString());
        multiPartRequest.addStringParam("email", getSharedPreferences("LoginData", MODE_PRIVATE).getString("email", "null"));

        RequestQueue requestQueue = Volley.newRequestQueue(DetailContentActivity.this);
        requestQueue.add(multiPartRequest);

    }

    public void deleteLike() {
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
//                new AlertDialog.Builder(DetailContentActivity.this).setMessage(response).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("date", tvDate.getText().toString());
        multiPartRequest.addStringParam("email", getSharedPreferences("LoginData", MODE_PRIVATE).getString("email", "null"));

        RequestQueue requestQueue = Volley.newRequestQueue(DetailContentActivity.this);
        requestQueue.add(multiPartRequest);

    }

    public void insertBookmark(){
        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/bookmarkCheckDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(DetailContentActivity.this).setMessage(response).setPositiveButton("예", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("date", getIntent().getStringExtra("date"));
        multiPartRequest.addStringParam("email", getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

        RequestQueue requestQueue = Volley.newRequestQueue(DetailContentActivity.this);
        requestQueue.add(multiPartRequest);
    }

    public void deleteBookmark(){
        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/bookmarkUncheckDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(DetailContentActivity.this).setMessage(response).setPositiveButton("예", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("date", getIntent().getStringExtra("date"));
        multiPartRequest.addStringParam("email", getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

        RequestQueue requestQueue = Volley.newRequestQueue(DetailContentActivity.this);
        requestQueue.add(multiPartRequest);
    }

    public void loadItem() {

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadDetailDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonConents = response;
                jsonParser();
                new AlertDialog.Builder(DetailContentActivity.this).setMessage(response).setPositiveButton("예", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("date", getIntent().getStringExtra("date"));
        multiPartRequest.addStringParam("email", getSharedPreferences("LoginData", MODE_PRIVATE).getString("email", "로그인 정보 없음"));

        RequestQueue requestQueue = Volley.newRequestQueue(DetailContentActivity.this);
        requestQueue.add(multiPartRequest);

    }

    public void jsonParser() {

        try {

            JSONObject jsonObject = new JSONObject(jsonConents);
            tvDate.setText(jsonObject.getString("date"));
            ivEmoticon.setImageResource(R.drawable.emoticon_01 + Integer.parseInt(jsonObject.getString("emoticon")));
            tvContent.setText(jsonObject.getString("content"));
            tvLike.setText(jsonObject.getString("like_count") + " 좋아요");
            tvComments.setText(jsonObject.getString("comment_count") + " 댓글");
            likeBtn.setChecked(jsonObject.getString("is_liked").equals("0") ? false : true);
            bookmarkBtn.setChecked(jsonObject.getString("isbookmarked").equals("0") ? false : true);

            if (!jsonObject.getString("img").toString().equals("null")) {
                String img = "http://win9101.dothome.co.kr/nanaldle/" + jsonObject.getString("img");
                Glide.with(this).load(img).into(ivContent);
                ivContent.setVisibility(View.VISIBLE);
            } else {
                ivContent.setVisibility(View.GONE);
            }

            if (!jsonObject.getString("tag").toString().equals("null")) {
                tvTag.setText("#" + jsonObject.getString("tag"));
                tvTag.setVisibility(View.VISIBLE);
            } else {
                tvTag.setVisibility(View.GONE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivContent.setTransitionName("IMG");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
