package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Editprofile extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private ImageView changedp;
    private static final int pick=2;
    private Button btnUpdate;
    private Uri resultUri;
    private StorageReference str;
    private DatabaseReference reference;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference db;
    private EditText inputAddress, inputUsername, inputPhone;
    LazyLoader lazyLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        str= FirebaseStorage.getInstance().getReference();
        db = FirebaseDatabase.getInstance().getReference().child("Image");
        reference= FirebaseDatabase.getInstance().getReference("Users");
        user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();
        inputUsername = findViewById(R.id.updateUsername);
        inputPhone = findViewById(R.id.updatePhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        inputAddress = findViewById(R.id.updateAddress);
        changedp = findViewById(R.id.imageDP);
        Information();
        updateInformation();

        // PROGRESS BAR //
        lazyLoader = findViewById(R.id.progressBar);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);



        reference.child(userID).child("Account").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image1= snapshot.child("myImage").getValue().toString();
                Picasso.with(Editprofile.this)
                        .load(image1)
                        .resize(100, 100)
                        .centerCrop()
                        .into(changedp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        changedp.setOnClickListener(v -> {
            Permission();
        });

    }

    @AfterPermissionGranted(123)
    private void Permission() {
        String[] perms = {Manifest.permission.CAMERA};
        if(EasyPermissions.hasPermissions(this, perms)){
            Intent gallery=new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(gallery,pick);
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
        Intent gallery=new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery,pick);

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }







    private void Information() {
        reference.child(userID).child("Account").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String usernameData = userProfile.valUsername;
                    String phoneData = userProfile.valNumber;
                    String addressData = userProfile.valAddress;

                    inputUsername.setText(usernameData);
                    inputPhone.setText(phoneData);
                    inputAddress.setText(addressData);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Editprofile.this, "Something Wrong Happened", Toast.LENGTH_LONG).show();
            }
        });

    }




    private void updateInformation() {
        btnUpdate.setOnClickListener(v -> {
            String newUsername = inputUsername.getText().toString().trim();
            String newPhone = inputPhone.getText().toString().trim();
            String newAddress = inputAddress.getText().toString().trim();
            if(newUsername.length()<3){
                inputUsername.setError("Min Username length should be 6 characters");
                inputUsername.requestFocus();
                return;
            }
            if(newPhone.length()<11){
                inputPhone.setError("Min Phone Number length not below 10 characters");
                inputPhone.requestFocus();
                return;
            }
            if(newAddress.length()<5){
                inputAddress.setError("Please Enter Your Complete Address");
                inputAddress.requestFocus();
                return;
            }
            reference.child(userID).child("Account").child("valUsername").setValue(newUsername);
            reference.child(userID).child("Account").child("valNumber").setValue(newPhone);
            reference.child(userID).child("Account").child("valAddress").setValue(newAddress);
            lazyLoader.setVisibility(View.VISIBLE);
            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    lazyLoader.setVisibility(View.GONE);
                    Toast.makeText(Editprofile.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                   // getSupportFragmentManager().beginTransaction()
                   //         .add(android.R.id.content, new profilefragment ()).commit();
                }
            }.start();


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lazyLoader.setVisibility(View.VISIBLE);

        if(requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE){

        }


        if(requestCode==pick && resultCode==RESULT_OK && data!=null){
            Uri changedp=data.getData();
            CropImage.activity(changedp)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                str.child(userID).putFile(resultUri).addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String,Object> data = new HashMap<>();
                                String newUri = uri.toString();
                                data.put("myImage",newUri);
                                reference.child(userID).child("Account").updateChildren(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        lazyLoader.setVisibility(View.GONE);
                                        Toast.makeText(Editprofile.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                                    }

                                });

                            }

                        });

                    }

                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                lazyLoader.setVisibility(View.VISIBLE);
                Exception error = result.getError();
            }
        }
    }


}