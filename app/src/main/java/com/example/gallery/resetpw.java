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
import android.widget.TextView;
import android.widget.Toast;
import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpw extends AppCompatActivity {

    private TextView backLogin;
    LazyLoader lazyLoader;
    private EditText textEmail;
    private Button Send;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpw);

        // PROGRESS BAR //
        lazyLoader = findViewById(R.id.progressBar);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);


        textEmail = findViewById(R.id.textEmail);
        Send = findViewById(R.id.Send);
        auth = FirebaseAuth.getInstance();
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }
        });

        backLogin = findViewById(R.id.back);
        backLOGIN();
    }

    private void resetPassword(){
        String email = textEmail.getText().toString().trim();

        if(email.isEmpty()){
            textEmail.setError("Email is required!");
            textEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Invalid email");
            textEmail.requestFocus();
            return;
        }
        lazyLoader.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(resetpw.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                    lazyLoader.setVisibility(View.GONE);
                }else{
                    Toast.makeText(resetpw.this,"Try again! Something wrong happened!",Toast.LENGTH_LONG).show();
                    lazyLoader.setVisibility(View.GONE);
                }
            }
        });

    }

    private void backLOGIN() {
        backLogin.setOnClickListener(v -> {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(resetpw.this);
            alertdialog.setMessage("Are you sure you want to cancel?").setCancelable(false)
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

        });


    }

    @Override
    public void onBackPressed() {
        ///   super.onBackPressed();
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(resetpw.this);
        alertdialog.setMessage("Are you sure you want to cancel?").setCancelable(false)
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