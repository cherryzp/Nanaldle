package com.cherryzpsoft.nanaldle;

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

    ToggleButton likeBtn;
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

        loadItem();

//        setItem();

        likeBtn = findViewById(R.id.btn_like);
        likeBtn.setOnCheckedChangeListener(likeChangeListener);

        commentsBtn = findViewById(R.id.btn_comments);
        commentsBtn.setOnClickListener(commentsListener);
    }

    CompoundButton.OnCheckedChangeListener likeChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()) {
                case R.id.btn_like:
                    if (isChecked) {
                        //선택됨
                        insertLike();
                    } else {
                        //선택되지 않음
                        deleteLike();
                    }

                    break;
            }

        }
    };

    View.OnClickListener commentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setItem() {

        Intent intent = getIntent();

        tvDate.setText(intent.getStringExtra("date"));
        ivEmoticon.setImageResource(R.drawable.emoticon_01 + Integer.parseInt(intent.getStringExtra("emoticon")));
        tvContent.setText(intent.getStringExtra("tv_content"));
        if (intent.getStringExtra("tag") == null) {
            tvTag.setText(intent.getStringExtra("like_count") + "좋아요");
        } else {
            tvTag.setText(intent.getStringExtra("like_count") + "좋아요   #" + intent.getStringExtra("tag"));
        }


        if (intent.getStringExtra("img_content") != null) {
            Glide.with(this).load(intent.getStringExtra("img_content")).into(ivContent);
            ivContent.setVisibility(View.VISIBLE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivContent.setTransitionName("IMG");
        }

    }

    public void insertLike() {

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/likeCheckDB.php";

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
                new AlertDialog.Builder(DetailContentActivity.this).setMessage(response).setPositiveButton("예", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("date", tvDate.getText().toString());

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

        RequestQueue requestQueue = Volley.newRequestQueue(DetailContentActivity.this);
        requestQueue.add(multiPartRequest);

    }

    public void jsonParser() {

        try {

            JSONObject jsonObject = new JSONObject(jsonConents);
            tvDate.setText(jsonObject.getString("date"));
            ivEmoticon.setImageResource(R.drawable.emoticon_01 + Integer.parseInt(jsonObject.getString("emoticon")));
            tvContent.setText(jsonObject.getString("content"));

            if (!jsonObject.getString("img").toString().equals("null")) {
                String img = "http://win9101.dothome.co.kr/nanaldle/" + jsonObject.getString("img");
                Glide.with(this).load(img).into(ivContent);
                ivContent.setVisibility(View.VISIBLE);
            } else {
                ivContent.setVisibility(View.GONE);
            }

            if (!jsonObject.getString("tag").toString().equals("null")) {
                tvTag.setText(jsonObject.getString("like_count") + "좋아요   #" + jsonObject.getString("tag"));
            } else {
                tvTag.setText(jsonObject.getString("like_count") + "좋아요");
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivContent.setTransitionName("IMG");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
