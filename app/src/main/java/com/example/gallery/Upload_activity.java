package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Upload_activity extends AppCompatActivity {

    private Button uploadBtn,showAllBtn;
    private ImageView imageView;
//    private ProgressBar progressBar;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private StorageReference reference = FirebaseStorage.getInstance().getReference("posts");
    private Uri imageUri;
    private String userID;
    private FirebaseUser user;
    private TextView cancel;
    EditText caption;
    LazyLoader lazyLoader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_activity);

        uploadBtn = findViewById(R.id.upload_btn);
        imageView = findViewById(R.id.imageView4);
//        progressBar = findViewById(R.id.progressBar);
        caption = findViewById(R.id.caption);
        cancel = findViewById(R.id.cancel);
        user= FirebaseAuth.getInstance().getCurrentUser();
        userID= user.getUid();
        lazyLoader = findViewById(R.id.progressBar);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Upload_activity.this,HomeGallery.class));

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Caption = caption.getText().toString();
                if (Caption.isEmpty()) {
                    caption.setError("Capstion is Required");
                    caption.requestFocus();
                    return;
                }else {
                    if (imageUri != null) {
                        uploadToFirebase(imageUri);
                        imageUri = null;
                    } else {
                        Toast.makeText(Upload_activity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CropImage.activity()
                .setAspectRatio(2,2)
                .start(Upload_activity.this);
    }


    private String getcurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
    private String getcurrentDate(){
        return new SimpleDateFormat("LLL dd yyyy", Locale.getDefault()).format(new Date());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){
        final StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // String modelId = root.push().getKey();
                        // root.child(modelId).setValue(model);
                        // Model model = new Model(uri.toString());
                            String url = uri.toString();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("imageURL", url);
                            hashMap.put("Time", getcurrentTime());
                            hashMap.put("Date", getcurrentDate());
                            hashMap.put("Caption",caption.getText().toString());
                            root.child("Users").child(userID).child("Posted").push().child("Images").setValue(hashMap);
                            Toast.makeText(Upload_activity.this, "TIME " + caption.getText().toString(), Toast.LENGTH_LONG).show();
                             lazyLoader.setVisibility(View.INVISIBLE);
                            Toast.makeText(Upload_activity.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Upload_activity.this, HomeGallery.class));
                            finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                lazyLoader.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lazyLoader.setVisibility(View.INVISIBLE);
                Toast.makeText(Upload_activity.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Upload_activity.this,HomeGallery.class));
    }
}



