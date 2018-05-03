package com.cherryzpsoft.nanaldle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WriteActivity extends AppCompatActivity {

    ImageView addImg, addEmoticon, addTag;
    EditText textContent;
    TextView btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        addImg = findViewById(R.id.img_add);
        addEmoticon = findViewById(R.id.emoticon_add);
        addTag = findViewById(R.id.tag_add);

        textContent = findViewById(R.id.text_content);

        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(btnSaveListener);
        btnCancel.setOnClickListener(btnCancelListener);

    }

    View.OnClickListener btnSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener btnCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
