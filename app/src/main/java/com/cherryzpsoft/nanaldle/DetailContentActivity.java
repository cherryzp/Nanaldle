package com.cherryzpsoft.nanaldle;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailContentActivity extends AppCompatActivity {

    TextView tvDate;
    ImageView ivEmoticon;
    ImageView ivContent;
    TextView tvContent;
    TextView tvTag;

    ImageView likeBtn, commentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);

        tvDate = findViewById(R.id.date_card_detail);
        ivEmoticon = findViewById(R.id.emoticon_card_detail);
        ivContent = findViewById(R.id.img_content_detail);
        tvContent = findViewById(R.id.text_content_detail);
        tvTag = findViewById(R.id.text_tag_detail);

        setItem();

        likeBtn = findViewById(R.id.btn_like);
        likeBtn.setOnClickListener(likeListener);

        commentsBtn = findViewById(R.id.btn_comments);
        commentsBtn.setOnClickListener(commentsListener);

    }

    View.OnClickListener likeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener commentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void setItem(){

        Intent intent = getIntent();

        tvDate.setText(intent.getStringExtra("date"));
        ivEmoticon.setImageResource(R.drawable.emoticon_01+Integer.parseInt(intent.getStringExtra("emoticon")));
        tvContent.setText(intent.getStringExtra("tv_content"));
        tvTag.setText(intent.getStringExtra("tag"));

        if(intent.getStringExtra("img_content")!=null){
            Glide.with(this).load(intent.getStringExtra("img_content")).into(ivContent);
            ivContent.setVisibility(View.VISIBLE);
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            ivContent.setTransitionName("IMG");
        }


    }
}
