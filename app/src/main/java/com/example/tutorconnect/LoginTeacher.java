package com.example.tutorconnect;

import android.content.Intent;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginTeacher extends AppCompatActivity {

    private ProgressBar pbLoading;
    private Button btnLogin;
    private TextView tvRegister,tvForgetPassword;
    private TextInputEditText etEmail,etPassword;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ImageView ivFingerprint;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        init();

        tvRegister.setOnClickListener(v->registerTeacher());
        btnLogin.setOnClickListener(v->login());
        tvForgetPassword.setOnClickListener(v->moveToForgetPasswordTeacher());
        ivFingerprint.setOnClickListener(v->userAuthentication());

    }

    private void userAuthentication()
    {
        BiometricManager biometricManager=BiometricManager.from(this);

        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
            Toast.makeText(this, "No biometric features available on this device.", Toast.LENGTH_SHORT).show();
            return;
        }

        Executor executor= ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt=new BiometricPrompt(LoginTeacher.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                moveToTeacherHomePage();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setNegativeButtonText("Cancel")
                .build();
        biometricPrompt.authenticate(promptInfo);

    }

    private void moveToForgetPasswordTeacher()
    {
        startActivity(new Intent(LoginTeacher.this, ForgetPasswordTeacher.class));
    }

    private void login()
    {
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString();

        if(email.isEmpty()) {
            etEmail.setError(getString(R.string.please_enter_your_email));
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            etPassword.setError(getString(R.string.please_enter_your_password));
            etPassword.requestFocus();
            return;
        }

        pbLoading.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(LoginTeacher.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                        moveToTeacherHomePage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(LoginTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void moveToTeacherHomePage()
    {
        startActivity(new Intent(LoginTeacher.this,TeacherHomePage.class));
        finish();
    }

    private void registerTeacher()
    {
        startActivity(new Intent(LoginTeacher.this,RegisterTeacher.class));
    }

    private void init()
    {
        tvRegister=findViewById(R.id.tvRegister);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        pbLoading=findViewById(R.id.pbLoading);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        pbLoading.setVisibility(View.GONE);
        ivFingerprint=findViewById(R.id.ivFingerprint);
        
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
    }
}