package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FullscreenActivity extends AppCompatActivity {
    DatabaseReference reference;
    private ActionMode mActionMode;
    private String userID;
    private FirebaseUser user;
    private TextView stamp,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);



        user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        stamp = findViewById(R.id.stamp);
        content = findViewById(R.id.content);



        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String imageURL = bundle.getString("IMAGES");
        int position = bundle.getInt("POSITION");


        reference.child("Posted").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                String[] imageSlide = new String[(int) datasnapshot.getChildrenCount()];
                String[] con = new String[(int) datasnapshot.getChildrenCount()];
                String[] time = new String[(int) datasnapshot.getChildrenCount()];

                int counter = imageSlide.length-1;

                for (DataSnapshot child: datasnapshot.getChildren()) {

                    imageSlide[counter] = child.child("Images").child("imageURL").getValue().toString();
                    con[counter] = child.child("Images").child("Caption").getValue().toString();
                    time[counter] = child.child("Images").child("Date").getValue().toString()+" "+child.child("Images").child("Time").getValue().toString();

                    counter--;

                }


                ViewPager viewPager = findViewById(R.id.viewPager);
                FullSizeAdapter imageAdapter = new FullSizeAdapter(FullscreenActivity.this,imageSlide);
                viewPager.setAdapter(imageAdapter);
                viewPager.setCurrentItem(position);
                content.setText(con[position]);
                stamp.setText(time[position]);

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        //Toast.makeText(FullscreenActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                        content.setText(con[position]);
                        stamp.setText(time[position]);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ViewPager viewPager = findViewById(R.id.viewPager);
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(mActionMode !=null){
//                    return false;
//                }
//
//
//                mActionMode = startSupportActionMode(mActionModeCallBack);
//
//                return true;
//            }
//        });
//
//



    }
//
//    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            mode.getMenuInflater().inflate(R.menu.deletemenu, menu);
//            mode.setTitle("Choose option");
//            return false;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false;
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch(item.getItemId()){
//                case R.id.deletebtn:
//                    Toast.makeText(FullscreenActivity.this, "Delete button", Toast.LENGTH_LONG).show();
//                    mode.finish();
//                    return true;
//
//                case R.id.sharebtn:
//                    Toast.makeText(FullscreenActivity.this, "SHARE button", Toast.LENGTH_LONG).show();
//                    mode.finish();
//                    return true;
//                default:
//                    return false;
//
//
//            }
//
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//
//        }
//    };






}