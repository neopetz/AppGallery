package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FullscreenActivity extends AppCompatActivity {
    DatabaseReference reference;
    private String userID;
    private FirebaseUser user;
    private ImageView imagePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        imagePhoto = findViewById(R.id.imagePhoto);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String imageURL = bundle.getString("IMAGES");

        reference.child("Posted").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot child: datasnapshot.getChildren()) {
                    if(imageURL.equals(child.child("Images").child("imageURL").getValue().toString())){
                        Picasso.with(FullscreenActivity.this)
                                .load(imageURL)
                                .fit()
                                .centerCrop()
                                .into(imagePhoto);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}