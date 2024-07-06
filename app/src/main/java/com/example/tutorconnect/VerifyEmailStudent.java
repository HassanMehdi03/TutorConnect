package com.example.tutorconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmailStudent extends AppCompatActivity {

    private TextView tvResend;
    private Button btnSave;
    private SharedPreferences sPref;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_student);

        init();

        btnSave.setOnClickListener(v->moveToStudentHomePage());
        tvResend.setOnClickListener(v->resendEmail());

    }

    private void resendEmail()
    {
        String email=sPref.getString("key_resend_email_student","");

        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(VerifyEmailStudent.this, R.string.email_sent, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VerifyEmailStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void moveToStudentHomePage()
    {
        startActivity(new Intent(VerifyEmailStudent.this,LoginStudent.class));
        finish();
    }

    private void init() {
        tvResend = findViewById(R.id.tvResend);
        btnSave = findViewById(R.id.btnSave);
        sPref=getSharedPreferences("UserPasswordReset",MODE_PRIVATE);

        auth= FirebaseAuth.getInstance();


    }

}