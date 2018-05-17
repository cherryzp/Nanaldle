package com.cherryzpsoft.nanaldle;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyWritingFragment extends Fragment {

    ArrayList<WriteItem> items = new ArrayList<>();
    WriteItem item;

    RecyclerView recyclerView;
    MyWritingRecyclerViewAdapter recyclerViewAdapter;

    String jsonConents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_writing, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_mypage_writing);
        recyclerViewAdapter = new MyWritingRecyclerViewAdapter(getActivity(), items);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadWritingContents();

        return view;
    }

    public void loadWritingContents(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadMyWritingContentsDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonConents = response;
//                new AlertDialog.Builder(getActivity()).setMessage(response).setPositiveButton("예", null).create().show();
                jsonParser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        multiPartRequest.addStringParam("email", getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "null"));

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
                item.setDate(jsonObject.getString("date"));
                item.setContent(jsonObject.getString("content"));
                items.add(item);
            }
            recyclerViewAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
