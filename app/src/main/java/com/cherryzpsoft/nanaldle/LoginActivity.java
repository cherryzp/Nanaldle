package com.cherryzpsoft.nanaldle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView postponeLoginBtn;

    CallbackManager callbackManager;
    LoginButton loginButton;

    JSONObject jsonData;

    String loginId, loginName, loginEmail;

    Object loginObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        setFacebookLoginBtn();

        postponeLoginBtn = findViewById(R.id.login_postpone_btn);
        postponeLogin();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void setFacebookLoginBtn(){
        loginButton.setReadPermissions("email", "public_profile");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                LoginActivity.this.loginObject = object;
                                jsonData = object;

                                jsonParser();

                                uploadDB();

                                Log.e("user profile",object.toString());                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    public void jsonParser(){

        try {
            loginId = jsonData.getString("id");
            loginName = jsonData.getString("name");
            loginEmail = jsonData.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void postponeLogin(){
        postponeLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("계정을 연동하지 않는경우 앱 이용에 제한이 있을 수 있습니다. 계정연동을 취소하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(LoginActivity.this, PasswordActivity.class));

                        finish();
                    }
                });
                builder.setNegativeButton("아니오", null);

                builder.create().show();

            }
        });
    }

    public void uploadDB(){

        String serverUrl = "http://win9101.dothome.co.kr/nanaldle/insertDB.php";

        SimpleMultiPartRequest multiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(LoginActivity.this).setMessage(response).setPositiveButton("OK", null).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        multiPartRequest.addStringParam("id", loginId);
        multiPartRequest.addStringParam("name", loginName);
        multiPartRequest.addStringParam("email", loginEmail);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(multiPartRequest);

    }

}