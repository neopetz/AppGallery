//package com.example.gallery;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//
//import java.util.List;
//
//public class FullSizeAdapter extends PagerAdapter {
//
//    Context context;
//    private List<String> images;
//    LayoutInflater inflater;
//    public FullSizeAdapter(Context context, List<String> images){
//        this.context = context;
//        this.images = images;
//    }
//
//
//    @Override
//    public int getCount() {
//        return images.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        ImageView imageView;
//        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.full_item,null);
//        imageView =  v.findViewById(R.id.img);
//        Glide.with(context).load(images.get(position)).apply(new RequestOptions().centerInside())
//                .into(imageView);
//        ViewPager vp =(ViewPager)container;
//        vp.addView(v,0);
//        return v;
//
//
//
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//     //   super.destroyItem(container, position, object);
//        ViewPager viewPager = (ViewPager)container;
//        View v = (View)object;
//        viewPager.removeView(v);
//
//    }
//}
