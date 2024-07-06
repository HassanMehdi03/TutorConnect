package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginStudent extends AppCompatActivity {

    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        init();

        tvRegister.setOnClickListener(v->registerStudent());

    }

    private void registerStudent()
    {
        startActivity(new Intent(this,RegisterStudent.class));
    }

    private void init()
    {
        tvRegister=findViewById(R.id.tvRegister);
    }

}

