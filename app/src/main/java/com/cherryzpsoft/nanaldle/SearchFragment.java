package com.cherryzpsoft.nanaldle;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ArrayList<String> items = new ArrayList<>();
    String tagItem;

    EditText editSearch;
    ImageView searchBtn;

    RecyclerView recyclerView;
    SearchRecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_search);
        recyclerViewAdapter = new SearchRecyclerViewAdapter(getActivity(), items);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        editSearch = view.findViewById(R.id.edit_search);
        searchBtn = view.findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(searchClickListener);

        return view;
    }

    View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!editSearch.getText().toString().equals("")){
                searchTagItem();
            }else {
                Toast.makeText(getActivity(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void searchTagItem(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/searchTagItemDB.php";

        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tagItem = response;
                new AlertDialog.Builder(getActivity()).setMessage(response).create().show();
                jsonParser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        simpleMultiPartRequest.addStringParam("tag", editSearch.getText().toString());

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(simpleMultiPartRequest);

    }

    public void jsonParser(){
        try {
            JSONArray jsonArray = new JSONArray(tagItem);
            JSONObject jsonObject;

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                items.add(jsonObject.getString("tag_item"));
            }
            recyclerViewAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
