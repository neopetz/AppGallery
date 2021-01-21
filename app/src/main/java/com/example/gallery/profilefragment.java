package com.example.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class profilefragment extends Fragment {

    private Button LogOut, btnUpdate;
    private TextView ViewFullName;
    private TextView ViewEmail, ViewUsername, ViewNumber, ViewAddress;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private DatabaseReference db;
    private StorageReference str;
    private static final int pick = 2;



     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        ViewFullName = view.findViewById(R.id.ViewFullName);
        ViewEmail = view.findViewById(R.id.ViewEmail);
        ViewUsername = view.findViewById(R.id.ViewUsername);
        ViewAddress = view.findViewById(R.id.ViewAddress);
        ViewNumber = view.findViewById(R.id.ViewNumber);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        db = FirebaseDatabase.getInstance().getReference().child("Image");
        str = FirebaseStorage.getInstance().getReference().child("Images");

        accountInformation();

         LogOut = view.findViewById(R.id.buttonSignOut);
         LogOut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 FirebaseAuth.getInstance().signOut();
                 Intent intent = new Intent(getActivity(),Login.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                 startActivity(intent);
             }
        });




             btnUpdate=view.findViewById(R.id.editProfile);
            btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
                 public void onClick(View v) {
                startActivity(new Intent(getActivity(), Editprofile.class));
               
            }
         });

        return view;

     }



    private void accountInformation(){
         reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 User userProfile = snapshot.getValue(User.class);
                 if(userProfile != null){

                     String fullnameData = userProfile.valFullName;
                     String emailData = userProfile.valEmail;
                     String usernameData= userProfile.valUsername;
                     String addressData = userProfile.valAddress;
                     String numberData = userProfile.valNumber;

                     ViewFullName.setText(fullnameData);
                     ViewEmail.setText(emailData);
                     ViewUsername.setText(usernameData);
                     ViewAddress.setText(addressData);
                     ViewNumber.setText(numberData);


                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(getActivity(),"Something wrong happened", Toast.LENGTH_LONG).show();

             }
         });



    }


    
}