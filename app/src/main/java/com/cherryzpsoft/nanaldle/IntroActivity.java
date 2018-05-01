package com.cherryzpsoft.nanaldle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    ImageView ivIntro;
    Animation animation;

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ivIntro = findViewById(R.id.iv_intro);
        animation = AnimationUtils.loadAnimation(this, R.anim.intro_main_anim);
        ivIntro.setAnimation(animation);

        timer.schedule(task, 3000);

    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));

            finish();
        }
    };

}
