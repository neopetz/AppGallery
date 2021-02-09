//package com.example.gallery;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.ViewPager;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.ViewParent;
//
//import java.util.List;
//
//public class FullscreenActivity extends Activity {
//
//    ViewPager viewPager;
//    List<String> images;
//    int position;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fullscreen);
//
//        if(savedInstanceState==null){
//            Intent i = getIntent();
//            images = i.getStringArrayListExtra("IMAGES");
//            position = i.getIntExtra("POSITION",0);
//        }
//
//        viewPager = findViewById(R.id.viewPager);
//
//        FullSizeAdapter fullSizeAdapter = new FullSizeAdapter (this,images);
//        viewPager.setAdapter(fullSizeAdapter);
//        viewPager.setCurrentItem(position,true);
//    }
//
//
//}

package com.example.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewParent;

import java.util.List;

public class FullscreenActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

    }
}



