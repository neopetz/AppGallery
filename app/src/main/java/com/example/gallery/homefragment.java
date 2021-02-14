package com.example.gallery;


import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class homefragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    RecyclerView convoRecycler;
    GalleryAdapter adapter;
    DatabaseReference reference;
    private String userID;
    private FirebaseUser user;
    String imgx;
    List<String> imageList = new ArrayList<>();
    List<String> img = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;

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

        FloatingActionButton floatingbtnGallery = view.findViewById(R.id.floatOpenGallery);
        floatingbtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permission();
//
            }
        });

        return view;
    }
    @AfterPermissionGranted(123)
    private void Permission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(getActivity(), perms)){
            startActivity(new Intent(getContext(),Upload_activity.class));
        }else{
            EasyPermissions.requestPermissions(this,"We need permission!",
                    123, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {


    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }

    }

    //    // ** REQUEST PERMISSION
//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
//            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
//            ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) ){
//            new AlertDialog.Builder(getActivity())
//                    .setMessage("This lets you share photos from your library")
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(getActivity(), new String[]{
//                                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.CAMERA},
//                                    STORAGE_PERMISSION);
//
//
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).create().show();
//        }else{
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == STORAGE_PERMISSION){
//            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

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
                Collections.reverse(img);
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