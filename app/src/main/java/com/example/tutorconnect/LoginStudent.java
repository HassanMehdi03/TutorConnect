package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginStudent extends AppCompatActivity {

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
        setContentView(R.layout.activity_login_student);

        init();

        tvRegister.setOnClickListener(v->registerStudent());
        btnLogin.setOnClickListener(v->login());
        tvForgetPassword.setOnClickListener(v->moveToForgetPasswordStudent());
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

        BiometricPrompt biometricPrompt=new BiometricPrompt(LoginStudent.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                moveToStudentHomePage();
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

    private void moveToForgetPasswordStudent()
    {
        startActivity(new Intent(LoginStudent.this, ForgetPasswordStudent.class));
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
                        Toast.makeText(LoginStudent.this, R.string.login_successfully, Toast.LENGTH_SHORT).show();
                        moveToStudentHomePage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pbLoading.setVisibility(View.GONE);
                        Toast.makeText(LoginStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void moveToStudentHomePage()
    {
        startActivity(new Intent(LoginStudent.this,StudentHomePage.class));
        finish();
    }

    private void registerStudent()
    {
        startActivity(new Intent(this,RegisterStudent.class));
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

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
    }

}

