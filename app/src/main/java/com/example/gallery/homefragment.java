package com.example.gallery;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;



public class homefragment extends Fragment {

    RecyclerView convoRecycler;
    GalleryAdapter adapter;
    DatabaseReference reference;
    private String userID;
    private FirebaseUser user;
    String imgx;
    List<String> imageList = new ArrayList<>();
    List<String> img = new ArrayList<>();
    FloatingActionsMenu floatingActionsMenu;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefragment, container, false);
        user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();
        convoRecycler = view.findViewById(R.id.convoRecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        getConvoImage();


        // FloatingActionButton -->
        FloatingActionButton floatingbtnCamera = view.findViewById(R.id.floatCamera);
        floatingbtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),CameraOpen.class));
            }
        });

        FloatingActionButton floatingbtnGallery = view.findViewById(R.id.floatOpenGallery);
        floatingbtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Upload_activity.class));
            }
        });


    return view;
    }




    private void getConvoImage() {
        reference.child("Posted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot child: datasnapshot.getChildren()) {

                    imgx = child.child("Images").child("imageURL").getValue().toString();
                    img.add(imgx);
                    String url = child.getKey();
                    imageList.add(url);
                }
                adapter = new GalleryAdapter(getContext(), img);
                convoRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                convoRecycler.setHasFixedSize(true);
                convoRecycler.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }




}