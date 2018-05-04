package com.cherryzpsoft.nanaldle;

import android.Manifest;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

public class WriteActivity extends AppCompatActivity {

    final int ADD_IMG_REQUEST_CODE = 100;
    final int IMG_CALL_REQUEST_CODE = 200;

    ImageView addImg, addEmoticon, addTag;
    ImageView selectedImg;
    EditText textContent;
    TextView btnSave, btnCancel;

    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        addImg = findViewById(R.id.img_add);
        addEmoticon = findViewById(R.id.emoticon_add);
        addTag = findViewById(R.id.tag_add);

        selectedImg = findViewById(R.id.selected_img);
        textContent = findViewById(R.id.text_content);

        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        addImg.setOnClickListener(addImgListener);
        addEmoticon.setOnClickListener(addEmoticonListener);
        addTag.setOnClickListener(addTagListener);

        btnSave.setOnClickListener(btnSaveListener);
        btnCancel.setOnClickListener(btnCancelListener);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ADD_IMG_REQUEST_CODE );
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case ADD_IMG_REQUEST_CODE:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "이미지 선택이 불가능합니다..", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    View.OnClickListener addImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMG_CALL_REQUEST_CODE);
        }
    };

    View.OnClickListener addEmoticonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener addTagListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    View.OnClickListener btnSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
            builder.setMessage("글을 저장하시겠습니까?");

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String serverUrl = "http://win9101.dothome.co.kr/nanaldle/insertContentDB.php";

                    SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    multiPartRequest.addStringParam("content", "콘텐츠입니다.");
                    if(imgPath!=null)
                        multiPartRequest.addFile("img", imgPath);
                    multiPartRequest.addStringParam("tag", "태그입니다.");
                    multiPartRequest.addStringParam("emoticon", "이모티콘");

                    RequestQueue requestQueue = Volley.newRequestQueue(WriteActivity.this);
                    requestQueue.add(multiPartRequest);


                }
            });

            builder.setNegativeButton("아니오", null);
            builder.create().show();
        }
    };

    View.OnClickListener btnCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case IMG_CALL_REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if(uri!=null){
                        selectedImg.setImageURI(uri);
                        selectedImg.setVisibility(View.VISIBLE);
                        imgPath = getRealPathFromUri(uri);

                    }
                }
                break;
        }

    }

    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
