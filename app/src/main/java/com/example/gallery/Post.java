//package com.example.gallery;
//
//import android.app.ProgressDialog;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
//import com.theartofdev.edmodo.cropper.CropImage;
//
//import java.util.HashMap;
//
//public class Upload_activity extends AppCompatActivity {
//
//    Uri imageUri;
//    String myUri = "";
//    StorageTask uploadTask;
//    StorageReference storageReference;
//
//
//
//    private String userID;
//    private FirebaseUser user;
//    private Button uploadBtn;
//    private ImageView imageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_activity);
//
//        user= FirebaseAuth.getInstance().getCurrentUser();
//        userID= user.getUid();
//        uploadBtn = findViewById(R.id.upload_btn);
//        imageView = findViewById(R.id.imageView4);
//        storageReference = FirebaseStorage.getInstance().getReference("posts");
//
//
//
//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage();
//            }
//        });
//
//
//        CropImage.activity()
//                .setAspectRatio(1,1)
//                .start(Upload_activity.this);
//
//    }
//
//    private String getFileExtension(Uri uri){
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mime= MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
//    }
//    private void uploadImage(){
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Posting");
//        progressDialog.show();
//        if(imageUri !=null){
//            StorageReference filereference = storageReference.child(System.currentTimeMillis()
//                    + "." + getFileExtension(imageUri));
//            uploadTask = filereference.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return  filereference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//                        myUri = downloadUri.toString();
//
//                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Posted");
//                        String postid = reference.push().getKey();
//
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("postid", postid);
//                        hashMap.put("imageURL",myUri);
//                        hashMap.put("Publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        reference.push().child("Images").setValue(hashMap);
//
//                        progressDialog.dismiss();
////                        getSupportFragmentManager().beginTransaction()
////                                .add(android.R.id.content, new uploadfragment ()).commit();
//                        Toast.makeText(Upload_activity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
//
//
//                    }else{
//                        Toast.makeText(Upload_activity.this, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Upload_activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else{
//            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//            imageView.setImageURI(imageUri);
//        }else{
//            Toast.makeText(this,"Something gone wrong!",Toast.LENGTH_LONG).show();
////                getSupportFragmentManager().beginTransaction()
////                        .add(android.R.id.content, new uploadfragment ()).commit();
//
//        }
//    }
//}
