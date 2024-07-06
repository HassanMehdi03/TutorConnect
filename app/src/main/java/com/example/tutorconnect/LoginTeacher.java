package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginTeacher extends AppCompatActivity {

    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        init();

        tvRegister.setOnClickListener(v->registerTeacher());

    }

    private void registerTeacher()
    {
        startActivity(new Intent(LoginTeacher.this,RegisterTeacher.class));
    }

    private void init()
    {
        tvRegister=findViewById(R.id.tvRegister);
    }
}