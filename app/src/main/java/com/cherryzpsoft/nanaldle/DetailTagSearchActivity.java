package com.cherryzpsoft.nanaldle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailTagSearchActivity extends AppCompatActivity {

    ArrayList<WriteItem> items = new ArrayList<>();
    WriteItem item;

    String jsonContents;

    RecyclerView recyclerView;
    DetailTagSearchRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tag_search);

        recyclerView = findViewById(R.id.recyclerview_detail_tag_item);
        recyclerViewAdapter = new DetailTagSearchRecyclerViewAdapter(this, items);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        loadItem();
    }

    public void loadItem(){
        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadDetailTagSearchDB.php";

        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonContents = response;
                jsonParser();
                new AlertDialog.Builder(DetailTagSearchActivity.this).setMessage(response).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        simpleMultiPartRequest.addStringParam("tag", getIntent().getStringExtra("tag"));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(simpleMultiPartRequest);

    }

    public void jsonParser(){
        try {
            JSONArray jsonArray = new JSONArray(jsonContents);
            JSONObject jsonObject;

            for(int i=0; i<jsonArray.length(); i++){
                item = null;
                item = new WriteItem();
                jsonObject = jsonArray.getJSONObject(i);

                item.setContent(jsonObject.getString("content"));
                item.setDate(jsonObject.getString("date"));
                item.setLikeCount(jsonObject.getString("like_count"));
                item.setCommentCount(jsonObject.getString("comment_count"));
                items.add(item);
            }
            recyclerViewAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
