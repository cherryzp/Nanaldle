package com.cherryzpsoft.nanaldle;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class FeedFragment extends Fragment {

    ArrayList<WriteItem> items = new ArrayList<>();
    WriteItem item;

    RecyclerView recyclerView;
    FeedRecyclerViewAdapter recyclerViewAdapter;

    ImageView iv;

    String jsonConents=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_feed);
        recyclerViewAdapter = new FeedRecyclerViewAdapter(getActivity(), items);
        recyclerView.setAdapter(recyclerViewAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        loadFeedContents();

        iv = view.findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               loadFeedContents();

            }
        });

        return view;
    }

    public void loadFeedContents(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadContentDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonConents = response;
                jsonParser();
                new AlertDialog.Builder(getActivity()).setMessage(jsonConents).setPositiveButton("예", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(multiPartRequest);

    }

    public void jsonParser(){

        try {

            JSONArray jsonArray = new JSONArray(jsonConents);
            JSONObject jsonObject;
            for(int i=0; i<jsonArray.length(); i++){
                item = null;
                item = new WriteItem();
                jsonObject = jsonArray.getJSONObject(i);
                item.setEmoticon(jsonObject.getString("emoticon"));
                item.setDate(jsonObject.getString("date"));
                if(!jsonObject.getString("img").toString().equals("null")) {
                    item.setImg("http://win9101.dothome.co.kr/nanaldle/" + jsonObject.getString("img"));
                }
                item.setContent(jsonObject.getString("content"));
                item.setTag(jsonObject.getString("tag"));
                item.setLikeCount(jsonObject.getString("like_count"));
                items.add(item);

            }
            recyclerViewAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}