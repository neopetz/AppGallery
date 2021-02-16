package com.example.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;

public class FullSizeAdapter extends PagerAdapter {

    Context mContext;
    String[] imageURL;
    LayoutInflater inflater;
    public FullSizeAdapter(Context context, String[] imageURL){
        this.mContext = context;
        this.imageURL = imageURL;
    }
    @Override
    public int getCount() {
        return imageURL.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

/*        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.full_item,null);
        ImageView imageView = v.findViewById(R.id.img);
        Glide.with(context).load(imageURL[position]).apply(new RequestOptions().centerInside())
                .into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(v,0);
        return v;*/

        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).load(imageURL[position]).apply(new RequestOptions().centerInside())
                .into(imageView);
        container.addView(imageView,0);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);

      /*  ViewPager viewPager = (ViewPager) container;
        View v = (View)object;
        viewPager.removeView(v);*/


    }
}