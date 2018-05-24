package com.cherryzpsoft.nanaldle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    EditText editComments;
    TextView addComments;

    ArrayList<String> items = new ArrayList<>();
    String comments;

    RecyclerView recyclerView;
    CommentsRecyclerViewAdapter recyclerViewAdapter;

    String jsonComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        editComments = findViewById(R.id.edit_comments);
        addComments = findViewById(R.id.add_comments_btn);

        addComments.setOnClickListener(commentsListener);

        recyclerView = findViewById(R.id.recyclerview_comments);
        recyclerViewAdapter = new CommentsRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadComments();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.exit_in_activity, R.anim.exit_out_activity);
    }

    View.OnClickListener commentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new AlertDialog.Builder(CommentsActivity.this).setMessage("작성하시겠습니까?").setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    insertComments();
                }
            }).setNegativeButton("아니오", null).create().show();

        }
    };

    public void insertComments(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/insertCommentsDB.php";

        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                new AlertDialog.Builder(CommentsActivity.this).setMessage(response).create().show();
                editComments.setText("");
                items.clear();
                loadComments();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        simpleMultiPartRequest.addStringParam("date", getIntent().getStringExtra("date"));
        simpleMultiPartRequest.addStringParam("email", getSharedPreferences("LoginData", MODE_PRIVATE).getString("email", "로그인 정보 없음"));
        simpleMultiPartRequest.addStringParam("comments", editComments.getText().toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpleMultiPartRequest);

    }

    public void loadComments(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadCommentsDB.php";

        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonComments = response;
                jsonParser();
//                new AlertDialog.Builder(CommentsActivity.this).setMessage(response).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        simpleMultiPartRequest.addStringParam("date", getIntent().getStringExtra("date"));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpleMultiPartRequest);

    }

    public void jsonParser(){
        try {
            JSONArray jsonArray = new JSONArray(jsonComments);
            JSONObject jsonObject;
            for(int i=0; i<jsonArray.length(); i++){
                comments = null;
                jsonObject = jsonArray.getJSONObject(i);
                comments = jsonObject.getString("comment");
                items.add(comments);
            }
            recyclerViewAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
