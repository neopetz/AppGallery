package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    TextView NameApp, wel;
    LazyLoader lazyLoader;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NameApp = findViewById(R.id.textView1);
        wel = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        // PROGRESS BAR //
        lazyLoader = findViewById(R.id.progressBar);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(lazyLoader);
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(NameApp);
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(wel);
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(imageView);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, Login.class));
             finish();
            }
        }, SPLASH_TIME_OUT);

    }
}