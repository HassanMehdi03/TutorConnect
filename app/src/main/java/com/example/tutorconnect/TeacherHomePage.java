package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherHomePage extends AppCompatActivity {

    private TextView tvViewPost, tvGetReview,tvVerifyEmail, tvEarn, tvPromote,tvProfile,tvSetting;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_page);

        init();

        tvViewPost.setOnClickListener(v->moveToViewPostedRequirementsPage());
        tvProfile.setOnClickListener(v->moveToProfilePage());
        tvSetting.setOnClickListener(v->moveToSettingPage());
        tvVerifyEmail.setOnClickListener(v->moveToVerifyEmailPage());
    }

    private void moveToVerifyEmailPage()
    {
        if(!user.isEmailVerified())
        {
            user.sendEmailVerification();
        }
        startActivity(new Intent(TeacherHomePage.this, VerifyEmailTeacher.class));
    }

    private void moveToSettingPage() {
        startActivity(new Intent(TeacherHomePage.this,SettingTeacher.class));
    }

    private void moveToProfilePage() {
        startActivity(new Intent(TeacherHomePage.this,ProfileTeacher.class));
    }

    private void moveToViewPostedRequirementsPage()
    {
        startActivity(new Intent(TeacherHomePage.this,ViewPostedRequirementsTeacher.class));
    }

    private void init()
    {
        tvViewPost=findViewById(R.id.tvViewPost);
        tvGetReview=findViewById(R.id.tvGetReview);
        tvEarn=findViewById(R.id.tvEarn);
        tvPromote=findViewById(R.id.tvPromote);
        tvProfile=findViewById(R.id.tvProfile);
        tvSetting=findViewById(R.id.tvSetting);
        tvVerifyEmail=findViewById(R.id.tvVerifyEmail);

        auth= FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

    }
}