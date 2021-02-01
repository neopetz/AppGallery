package com.example.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class register extends AppCompatActivity implements View.OnClickListener {

    private TextView goBackLogin;
    private TextView btnRegisterUser;
    private EditText textFullName, textEmail, textPassword, textPasswordConfirm;
    private FirebaseAuth mAuth;
    String valFullName, valEmail, valPassword, valConfirmPassword,valUsername="",valAddress="",valNumber="", myImage="https://firebasestorage.googleapis.com/v0/b/auth-12c84.appspot.com/o/255-2552596_anime-clipart-aesthetic-cute-korean-png.png?alt=media&token=35eb90ff-b239-4884-a49f-441902f7c4bd";
    LazyLoader lazyLoader;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        // PROGRESS BAR //
        lazyLoader = findViewById(R.id.progressBar);
        LazyLoader loader = new LazyLoader(this, 30, 20, ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected),
                ContextCompat.getColor(this, R.color.loader_selected));
        loader.setInterpolator(new LinearInterpolator());
        lazyLoader.addView(loader);


        btnRegisterUser = findViewById(R.id.Register);
        btnRegisterUser.setOnClickListener(this);
        goBackLogin=findViewById(R.id.back);
        goBackLogin.setOnClickListener(this);
        textFullName = findViewById(R.id.inputfullname);
        textEmail = findViewById(R.id.inputemail);
        textPassword = findViewById(R.id.inputpw);
        textPasswordConfirm = findViewById(R.id.inputconfirmpw);




    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.Register:
                Register();
                break;

        }
    }

    private void Register() {
        valFullName = textFullName.getText().toString().trim();
        valEmail = textEmail.getText().toString().trim();
        valPassword = textPassword.getText().toString().trim();
        valConfirmPassword = textPasswordConfirm.getText().toString().trim();

        if(valFullName.isEmpty()){
            textFullName.setError("Fullname is required!");
            textFullName.requestFocus();
            return;
        }

        if(valEmail.isEmpty()){
            textEmail.setError("Email is required!");
            textEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(valEmail).matches()){
            textEmail.setError("Invalid Email!");
            textEmail.requestFocus();
            return;
        }
        if(valPassword.isEmpty()){
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
            return;
        }

        if(valPassword.length()<6){
            textPassword.setError("Min password length should be 6 characters");
            textPassword.requestFocus();
            return;
        }

        if(!valConfirmPassword.equals(valPassword)){
            textPasswordConfirm.setError("Password did not Matched");
            textPasswordConfirm.requestFocus();
            return;
        }

        lazyLoader.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(valEmail,valPassword)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        User user = new User(valFullName, valEmail, valUsername, valNumber, valAddress, myImage);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("Account")
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    String text = "Registered Successfully \n Please log in to verify your email";
                                    Spannable centeredText = new SpannableString(text);
                                    centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                                            0, text.length() - 1,
                                            Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                                    Toast.makeText(register.this, centeredText, Toast.LENGTH_LONG).show();
                                    lazyLoader.setVisibility(View.GONE);

                                    timer = new Timer();
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(register.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 3000);


                                } else {
                                    Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    lazyLoader.setVisibility(View.GONE);
                                }
                            }
                        });


                    } else {
                        Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        lazyLoader.setVisibility(View.GONE);
                    }
                });
    }
}

