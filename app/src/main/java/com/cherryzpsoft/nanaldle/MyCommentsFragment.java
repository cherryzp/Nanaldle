package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MyCommentsFragment extends Fragment {

    ArrayList<MyCommentsItem> items = new ArrayList<>();
    MyCommentsItem item;

    RecyclerView recyclerView;
    MyCommentsRecyclerViewAdapter recyclerViewAdapter;

    String jsonContents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_mypage_comments);
        recyclerViewAdapter = new MyCommentsRecyclerViewAdapter(getActivity(), items);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadMyComments();

        return view;
    }

    public void loadMyComments(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadMyCommentsDB.php";

        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonContents = response;
//                new AlertDialog.Builder(getActivity()).setMessage(response).create().show();
                jsonParser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        simpleMultiPartRequest.addStringParam("email", getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE).getString("email", "로그인 정보 없음"));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(simpleMultiPartRequest);

    }

    public void jsonParser(){
        try {
            JSONArray jsonArray = new JSONArray(jsonContents);
            JSONObject jsonObject;
            for(int i=0; i<jsonArray.length(); i++){
                item = null;
                item = new MyCommentsItem();
                jsonObject = jsonArray.getJSONObject(i);
                item.setContents(jsonObject.getString("content"));
                item.setComments(jsonObject.getString("comment"));
                item.setcDate(jsonObject.getString("date"));
                item.setmDate(jsonObject.getString("m_date"));
                items.add(item);
            }

            recyclerViewAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
