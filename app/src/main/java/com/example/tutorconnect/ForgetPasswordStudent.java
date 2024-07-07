package com.example.tutorconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordStudent extends AppCompatActivity {

    private Button btnContinue;
    private TextInputEditText etEmail;
    private FirebaseAuth auth;
    private SharedPreferences sPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_student);

        init();
        btnContinue.setOnClickListener(v->forgetPassword());

    }


    private void forgetPassword()
    {
        String email=etEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            etEmail.setError(getString(R.string.please_enter_your_email));
            etEmail.requestFocus();
            return;
        }

        editor.putString("key_resend_email_student",email);
        editor.apply();

        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ForgetPasswordStudent.this, R.string.email_sent, Toast.LENGTH_SHORT).show();
                        moveToVerifyEmailPage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgetPasswordStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void moveToVerifyEmailPage()
    {
        startActivity(new Intent(ForgetPasswordStudent.this, VerifyEmailForPasswordResetStudent.class));
    }

    private void init()
    {
        btnContinue=findViewById(R.id.btnContinue);
        etEmail=findViewById(R.id.etEmail);
        auth=FirebaseAuth.getInstance();

        sPref=getSharedPreferences("UserPasswordReset",MODE_PRIVATE);
        editor=sPref.edit();
    }

}