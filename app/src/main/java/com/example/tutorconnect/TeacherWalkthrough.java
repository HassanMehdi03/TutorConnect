package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherWalkthrough extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Button btnGetStarted;
    private TextView tvWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_walkthrough);

        init();

        setName();
        btnGetStarted.setOnClickListener(v->moveToTeacherHomePage());

    }

    private void setName()
    {
        reference.child("TeacherAccount").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.exists())
                        {
                            tvWelcome.setText("Welcome "+snapshot.child("name").getValue().toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    private void moveToTeacherHomePage()
    {
        startActivity(new Intent(this,TeacherHomePage.class));
        finish();
    }

    private void init()
    {
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();
        btnGetStarted=findViewById(R.id.btnGetStarted);
        tvWelcome =findViewById(R.id.tvWelcome);

    }

}