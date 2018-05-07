package com.cherryzpsoft.nanaldle;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class DetailContentActivity extends AppCompatActivity {

    TextView tvDate;
    ImageView ivEmoticon;
    ImageView ivContent;
    TextView tvContent;
    TextView tvTag;

    ToggleButton likeBtn;
    ImageView commentsBtn;

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
        likeBtn.setOnCheckedChangeListener(likeChangeListener);

        commentsBtn = findViewById(R.id.btn_comments);
        commentsBtn.setOnClickListener(commentsListener);
    }

    CompoundButton.OnCheckedChangeListener likeChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            switch (buttonView.getId()){
                case R.id.btn_like:
                    if(isChecked){
                        //선택됨
                        new AlertDialog.Builder(DetailContentActivity.this).setMessage("선택됨").setPositiveButton("예", null).create().show();
                    }else{
                        //선택되지 않음
                        new AlertDialog.Builder(DetailContentActivity.this).setMessage("노놉").setPositiveButton("예", null).create().show();

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
