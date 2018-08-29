package com.example.kim.finalprojecttrack1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.kim.finalprojecttrack1.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText name;
    private EditText password;
    private Button signup;
    private String splash_background;
    private ImageView profile;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(splash_background));
        }


        email = (EditText) findViewById(R.id.signupActivity_edittext_id);
        name = (EditText) findViewById(R.id.signupActivity_edittext_name);
        password = (EditText) findViewById(R.id.signupActivity_edittext_password);
        signup = (Button) findViewById(R.id.signupActivity_button_signup);
        signup.setBackgroundColor(Color.parseColor(splash_background));


        signup.setOnClickListener(view -> {
            String stEmail = email.getText().toString();
            String stName = name.getText().toString();
            String stPassword = password.getText().toString();

            if (stEmail == null || stName == null || stPassword == null) {
                return;
            } else {
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(stEmail, stPassword)
                        .addOnCompleteListener(SignUpActivity.this, task -> {
                            final String uid = task.getResult().getUser().getUid();

                            UserModel userModel = new UserModel();
                            userModel.userName = name.getText().toString();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name.getText().toString()).build();
                            task.getResult().getUser().updateProfile(userProfileChangeRequest);
                            userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(aVoid -> finish());


                        });
            }

        });



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
        finish();
    }


}
