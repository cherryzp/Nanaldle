package com.cherryzpsoft.nanaldle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordActivity extends AppCompatActivity {

    EditText editNum1, editNum2, editNum3, editNum4;
    TextView tvAccept;
    TextView tvCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        editNum1 = findViewById(R.id.edit_text_num_1);
        editNum2 = findViewById(R.id.edit_text_num_2);
        editNum3 = findViewById(R.id.edit_text_num_3);
        editNum4 = findViewById(R.id.edit_text_num_4);

        tvAccept = findViewById(R.id.password_accept);
        tvCancel = findViewById(R.id.password_cancel);

        numChange();
        passwordDecisionListener();
    }

    public void numChange() {

        editNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editNum2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editNum3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editNum4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    public void passwordDecisionListener() {

        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder acceptBuilder = new AlertDialog.Builder(PasswordActivity.this);
                acceptBuilder.setMessage("이 비밀번호로 하시겠습니까?");
                acceptBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PasswordActivity.this, MainActivity.class));

                        finish();
                    }
                });
                acceptBuilder.setNegativeButton("아니오", null);

                acceptBuilder.create().show();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder acceptBuilder = new AlertDialog.Builder(PasswordActivity.this);
                acceptBuilder.setMessage("비밀번호 설정을 취소하시겠습니까?");
                acceptBuilder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PasswordActivity.this, MainActivity.class));

                        finish();
                    }
                });
                acceptBuilder.setNegativeButton("아니오", null);

                acceptBuilder.create().show();
            }
        });

    }

}
