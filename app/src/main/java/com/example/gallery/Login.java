package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity implements  View.OnClickListener{
    private TextView gotoRegister;
    private TextView forgotPw;
    private EditText textEmail, textPassword;
    LazyLoader lazyLoader;
    private FirebaseAuth mAuth;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // PROGRESS BAR //
        lazyLoader = findViewById(R.id.progressBar);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);

        gotoRegister = findViewById(R.id.gotoregister);
        gotoRegister.setOnClickListener(this);
        forgotPw = findViewById(R.id.resetpassword);
        forgotPw.setOnClickListener(this);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(this);
        textEmail=findViewById(R.id.editTextEmailAddress);
        textPassword=findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();


    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.gotoregister:
                startActivity(new Intent(this, register.class));
                break;
            case R.id.resetpassword:
                startActivity(new Intent(this, resetpw.class));
                break;
            case R.id.login_button:
                userLogin();
                break;

        }
    }


    private void userLogin() {
        String valEmail = textEmail.getText().toString().trim();
        String valPassword = textPassword.getText().toString().trim();

        if (valEmail.isEmpty()) {
            textEmail.setError("Email is required!");
            textEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(valEmail).matches()) {
            textEmail.setError("Invalid Email!");
            textEmail.requestFocus();
            return;
        }
        if (valPassword.isEmpty()) {
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
            return;
        }

        lazyLoader.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(valEmail,valPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {
                        startActivity(new Intent(Login.this, HomeGallery.class));
                        finish();
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(Login.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                    }
                }else{
                    textPassword.setText("");
                    textPassword.setError("Password is incorrect");
                    textPassword.requestFocus();
                }
                lazyLoader.setVisibility(View.GONE);


            }
        });

    }


    @Override
    public void onBackPressed() {
     ///   super.onBackPressed();
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(Login.this);
        alertdialog.setMessage("Do want to exit?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertdialog.create();
        alert.show();

    }



}

