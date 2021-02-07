//package com.example.gallery;
//
//import android.app.ProgressDialog;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.MimeTypeMap;
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
//public class Post extends AppCompatActivity {
//
//    Uri imageUri;
//    String myUri = "";
//    StorageTask uploadTask;
//    StorageReference storageReference;
//
//    ImageView close, image_added;
//    TextView post;
//    EditText description;
//    private String userID;
//    private FirebaseUser user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_uploadfragment);
//
//        user= FirebaseAuth.getInstance().getCurrentUser();
//        userID= user.getUid();
//        close = findViewById(R.id.close);
//        image_added = findViewById(R.id.image_added);
//        post = findViewById(R.id.post);
//        description = findViewById(R.id.description);
//        storageReference = FirebaseStorage.getInstance().getReference("posts");
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new uploadfragment ()).commit();
//            }
//        });
//
//        post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadImage();
//            }
//        });
//
//
//        CropImage.activity()
//                .setAspectRatio(1,1)
//                .start(Post.this);
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
//            + "." + getFileExtension(imageUri));
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
//                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
//                        String postid = reference.push().getKey();
//
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("postid", postid);
//                        hashMap.put("postimage",myUri);
//                        hashMap.put("description", description.getText().toString());
//                        hashMap.put("Publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                        reference.child(userID).child("Account").child("Images").setValue(hashMap);
//
//                        progressDialog.dismiss();
//                        getSupportFragmentManager().beginTransaction()
//                                .add(android.R.id.content, new uploadfragment ()).commit();
//
//
//                    }else{
//                        Toast.makeText(Post.this, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Post.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
//            if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                imageUri = result.getUri();
//
//                image_added.setImageURI(imageUri);
//            }else{
//                Toast.makeText(this,"Something gone wrong!",Toast.LENGTH_LONG).show();
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new uploadfragment ()).commit();
//
//            }
//    }
//}
