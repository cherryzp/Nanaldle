package com.cherryzpsoft.nanaldle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;

public class MyCommentsFragment extends Fragment {

    ArrayList<WriteItem> items = new ArrayList<>();
    WriteItem item;

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

        return view;
    }

    public void loadMyComments(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/loadMyCommentsDB.php";

        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(getActivity()).setMessage(response);
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

}
