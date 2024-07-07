package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailTeacher extends AppCompatActivity
{
    private TextView tvNotVerified,tvDontReceiveEmail,tvVerified,tvResend;
    private Button btnSave;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email_teacher);

        init();

        tvResend.setOnClickListener(v->resendEmail());
        btnSave.setOnClickListener(v->moveToTeacherHomePage());

    }

    private void moveToTeacherHomePage()
    {
        startActivity(new Intent(VerifyEmailTeacher.this,TeacherHomePage.class));
        finish();
    }

    private void resendEmail()
    {
        user.sendEmailVerification();
        Toast.makeText(this, R.string.email_sent, Toast.LENGTH_SHORT).show();
    }

    private void init()
    {
        tvNotVerified=findViewById(R.id.tvNotVerified);
        tvVerified=findViewById(R.id.tvVerified);
        tvDontReceiveEmail=findViewById(R.id.tvEmailNotReceived);
        tvResend=findViewById(R.id.tvResend);
        btnSave=findViewById(R.id.btnSave);

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        if(user.isEmailVerified())
        {
            tvVerified.setVisibility(View.VISIBLE);
            tvNotVerified.setVisibility(View.GONE);
            tvResend.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
            tvDontReceiveEmail.setVisibility(View.GONE);
        }
        else
        {
            tvVerified.setVisibility(View.GONE);
            tvNotVerified.setVisibility(View.VISIBLE);
            tvResend.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
        }

    }

}