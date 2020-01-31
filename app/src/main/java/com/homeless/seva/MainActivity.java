package com.homeless.seva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        final Thread thread = new Thread() {
            public void run() {
                try {
                    TextView textView = findViewById(R.id.seva);
                    textView.animate().alpha(1);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide);
                    textView.startAnimation(animSlide);

                    sleep(2300);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    // if (SharedPrefManager.getBooleanPreferences(getApplicationContext(), "IS_LOGIN")) {
                    startActivity(new Intent(MainActivity.this, Dashboard.class));


                }
            }
        };
        thread.start();

    }
}

