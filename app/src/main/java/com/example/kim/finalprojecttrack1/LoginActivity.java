package com.example.kim.finalprojecttrack1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kim.finalprojecttrack1.Fragment.ChatFragment;
import com.example.kim.finalprojecttrack1.model.UserModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText id;
    private EditText password;
    LoginButton facebooklogin;

    Button registerbtn;
    Button loginbtn;
    SignInButton googlebtn;

    FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseAuth firebaseAuth; //로그인관리
    private FirebaseAuth.AuthStateListener authStateListener;//로그인 되었는지 안되었는 채크를 해주는 역활



    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN = 10;
    private String TAG = "tiger";

    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText) findViewById(R.id.loginActivity_edittext_id);
        password = (EditText) findViewById(R.id.loginActivity_edittext_password);
        registerbtn = (Button) findViewById(R.id.registerbtn);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        googlebtn = (SignInButton) findViewById(R.id.google_register_btn);
        facebooklogin = (LoginButton) findViewById(R.id.facebook_button);

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        loginbtn.setOnClickListener(view -> {
            if (id.getText().toString().equals(" ") || password.getText().toString().equals(" ") || id.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            loginEvent();
        });


        mCallbackManager = CallbackManager.Factory.create();
        facebooklogin.setReadPermissions("email", "public_profile");
        facebooklogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

        registerbtn.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });
        //로그인 인터페이스 리스너

        googlebtn.setOnClickListener(view -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        authStateListener = firebaseAuth -> {
            //로그인이 되었거나 로그아웃이 되었을 때
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                //로그인


                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();//자기를 닫아주는 역활
            } else {
                //로그아웃
            }
        };
        // Initialize Facebook Login button
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        return;

                    } else {
                        final String uid =task.getResult().getUser().getUid();
                        UserModel userModel = new UserModel();
                        userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        userModel.userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).build();
                        task.getResult().getUser().updateProfile(userProfileChangeRequest);//

                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(aVoid -> {
                            startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                            finish();
                        });
                        Toast.makeText(LoginActivity.this, "FaceBook 아이디 연동 성공", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginEvent() {
        firebaseAuth.signInWithEmailAndPassword(id.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                //로그인이 실패한 부분
                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                //로그인이 정상적으로 처리가 되었는지 안되었는지 확인을 해주는 부분
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        final String uid = task.getResult().getUser().getUid();
                        UserModel userModel = new UserModel();
                        userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        userModel.userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).build();
                        task.getResult().getUser().updateProfile(userProfileChangeRequest);//

                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(aVoid -> {
                            startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                            finish();
                        });
                        Toast.makeText(LoginActivity.this, "FaceBook 아이디 연동 성공", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}

