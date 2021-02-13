package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collection;

public class FullscreenActivity extends AppCompatActivity {
    DatabaseReference reference;
    private String userID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String imageURL = bundle.getString("IMAGES");
        int position = bundle.getInt("POSITION");

        reference.child("Posted").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                String[] imageSlide = new String[(int) datasnapshot.getChildrenCount()];

                int counter = imageSlide.length-1;

                for (DataSnapshot child: datasnapshot.getChildren()) {

                    imageSlide[counter] = child.child("Images").child("imageURL").getValue().toString();
                    counter--;

                }


                ViewPager viewPager = findViewById(R.id.viewPager);
                FullSizeAdapter imageAdapter = new FullSizeAdapter(FullscreenActivity.this,imageSlide);
                viewPager.setAdapter(imageAdapter);
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}