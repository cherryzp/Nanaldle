package com.cherryzpsoft.nanaldle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

    EditText editHashtag;
    String hashtag;

    int emoticonNum = 0;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ADD_IMG_REQUEST_CODE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case ADD_IMG_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
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
        @SuppressLint("RestrictedApi")
        @Override
        public void onClick(View v) {

            MenuBuilder menuBuilder = new MenuBuilder(WriteActivity.this);
            MenuInflater inflater = new MenuInflater(WriteActivity.this);
            inflater.inflate(R.menu.popup_emoticon, menuBuilder);
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(WriteActivity.this, menuBuilder, v);
            menuPopupHelper.setForceShowIcon(true);

            menuBuilder.setCallback(emoticonSelectedCallback);

            menuPopupHelper.show();

        }
    };

    MenuBuilder.Callback emoticonSelectedCallback = new MenuBuilder.Callback() {
        @Override
        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.emoticon_01:
                    addEmoticon.setImageResource(R.drawable.emoticon_01);
                    emoticonNum = 0;
                    break;

                case R.id.emoticon_02:
                    addEmoticon.setImageResource(R.drawable.emoticon_02);
                    emoticonNum = 1;
                    break;

                case R.id.emoticon_03:
                    addEmoticon.setImageResource(R.drawable.emoticon_03);
                    emoticonNum = 2;
                    break;

                case R.id.emoticon_04:
                    addEmoticon.setImageResource(R.drawable.emoticon_04);
                    emoticonNum = 3;
                    break;

                case R.id.emoticon_05:
                    addEmoticon.setImageResource(R.drawable.emoticon_05);
                    emoticonNum = 4;
                    break;

                case R.id.emoticon_06:
                    addEmoticon.setImageResource(R.drawable.emoticon_06);
                    emoticonNum = 5;
                    break;

                case R.id.emoticon_07:
                    addEmoticon.setImageResource(R.drawable.emoticon_07);
                    emoticonNum = 6;
                    break;

                case R.id.emoticon_08:
                    addEmoticon.setImageResource(R.drawable.emoticon_08);
                    emoticonNum = 8;
                    break;

                case R.id.emoticon_09:
                    addEmoticon.setImageResource(R.drawable.emoticon_09);
                    emoticonNum = 9;
                    break;

            }

            return false;
        }

        @Override
        public void onMenuModeChange(MenuBuilder menu) {

        }
    };



    View.OnClickListener addTagListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder hashtagBuilder = new AlertDialog.Builder(WriteActivity.this);

            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.hashtag_dialog, null);
            editHashtag = dialogView.findViewById(R.id.dialog_edittext_hashtag);
            if(hashtag!=null) editHashtag.setText(hashtag);
            hashtagBuilder.setPositiveButton("작성", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!editHashtag.getText().toString().equals("")){
                        Toast.makeText(WriteActivity.this, "작성됨.", Toast.LENGTH_SHORT).show();
                        hashtag = editHashtag.getText().toString();
                    } else{
                        Toast.makeText(WriteActivity.this, "글을 작성해주세요.", Toast.LENGTH_SHORT).show();
                        hashtag = null;
                    }
                }
            });

            hashtagBuilder.setNegativeButton("취소", null);

            hashtagBuilder.setView(dialogView).create().show();

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
                            new AlertDialog.Builder(WriteActivity.this).setMessage(response).setPositiveButton("ㅇㅋ", null).create().show();
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    multiPartRequest.addStringParam("content", textContent.getText().toString());
                    if (imgPath != null)
                        multiPartRequest.addFile("img", imgPath);
                    multiPartRequest.addStringParam("tag", hashtag);
                    multiPartRequest.addStringParam("emoticon", emoticonNum+"");
                    multiPartRequest.addStringParam("email", getSharedPreferences("LoginData", MODE_PRIVATE).getString("email", "null"));
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
            new AlertDialog.Builder(WriteActivity.this).setMessage("작성을 취소하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("아니오", null).create().show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case IMG_CALL_REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        selectedImg.setImageURI(uri);
                        selectedImg.setVisibility(View.VISIBLE);
                        imgPath = getRealPathFromUri(uri);
                    }
                }
                break;
        }

    }

    String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}
