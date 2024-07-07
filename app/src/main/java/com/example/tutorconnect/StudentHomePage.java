package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentHomePage extends AppCompatActivity {

    private TextView tvPost,tvTutor,tvViewPost,tvProfile,tvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        init();

        tvPost.setOnClickListener(v->moveToPostRequirementPage());
        tvTutor.setOnClickListener(v->moveToFindATutorPage());
        tvViewPost.setOnClickListener(v->moveToViewPostedRequirementsPage());
        tvProfile.setOnClickListener(v->moveToProfilePage());
        tvSetting.setOnClickListener(v->moveToSettingPage());


    }

    private void moveToSettingPage() {
        startActivity(new Intent(StudentHomePage.this,SettingStudent.class));
    }

    private void moveToProfilePage()
    {
        startActivity(new Intent(StudentHomePage.this,ProfileStudent.class));
    }

    private void moveToViewPostedRequirementsPage() {
        startActivity(new Intent(StudentHomePage.this,ViewPostedRequirementsStudent.class));
    }

    private void moveToFindATutorPage() {
        startActivity(new Intent(StudentHomePage.this,FindTutorStudent.class));

    }

    private void moveToPostRequirementPage()
    {
        startActivity(new Intent(StudentHomePage.this,PostRequirementStudent.class));
    }

    private void init()
    {
        tvPost=findViewById(R.id.tvPost);
        tvTutor=findViewById(R.id.tvTutor);
        tvViewPost=findViewById(R.id.tvViewPost);
        tvProfile=findViewById(R.id.tvProfile);
        tvSetting=findViewById(R.id.tvSetting);

    }
}