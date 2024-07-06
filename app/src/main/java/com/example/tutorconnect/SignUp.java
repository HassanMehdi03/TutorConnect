package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUp extends AppCompatActivity {

    View ivStudent,ivTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        ivStudent.setOnClickListener(v->student());
        ivTeacher.setOnClickListener(v->teacher());

    }

    private void student()
    {
        startActivity(new Intent(SignUp.this,LoginStudent.class));
    }

    private void teacher()
    {
        startActivity(new Intent(SignUp.this,LoginTeacher.class));
    }

    private void init()
    {
        ivStudent=findViewById(R.id.student_pic);
        ivTeacher=findViewById(R.id.teacher_pic);
    }
}