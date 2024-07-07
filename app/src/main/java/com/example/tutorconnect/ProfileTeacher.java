package com.example.tutorconnect;

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileTeacher extends AppCompatActivity {

    private TextView tvEmail,tvName,tvPhone,tvDegree,tvDescription,tvExperience,tvGender,tvLocation,tvSpeciality,tvSubject,tvInstitute;
    private Button btnLogout;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);

        init();
        loadData();
        btnLogout.setOnClickListener(v->logout());
    }

    private void logout()
    {
        auth.signOut();
        startActivity(new Intent(ProfileTeacher.this,LoginTeacher.class));
        finish();
    }

    private void init()
    {
        tvEmail=findViewById(R.id.tvEmail);
        tvName=findViewById(R.id.tvName);
        tvPhone=findViewById(R.id.tvPhone);
        tvDegree=findViewById(R.id.tvDegree);
        tvDescription=findViewById(R.id.tvDescription);
        tvExperience=findViewById(R.id.tvExperience);
        tvGender=findViewById(R.id.tvGender);
        tvLocation=findViewById(R.id.tvLocation);
        tvSpeciality=findViewById(R.id.tvSpeciality);
        tvSubject=findViewById(R.id.tvSubject);
        tvInstitute=findViewById(R.id.tvInstitute);
        btnLogout=findViewById(R.id.btnLogout);

        reference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
    }

    private void loadData()
    {
        reference.child("TeacherAccount")
                .child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        tvEmail.setText(snapshot.child("email").getValue().toString());
                        tvName.setText(snapshot.child("name").getValue().toString());
                        tvPhone.setText(snapshot.child("phone").getValue().toString());
                        tvDegree.setText(snapshot.child("degree").getValue().toString());
                        tvDescription.setText(snapshot.child("job_description").getValue().toString());
                        tvExperience.setText(snapshot.child("experience").getValue().toString()+" years");
                        tvGender.setText(snapshot.child("gender").getValue().toString());
                        tvLocation.setText(snapshot.child("location").getValue().toString());
                        tvSpeciality.setText(snapshot.child("speciality").getValue().toString());
                        tvSubject.setText(snapshot.child("subject").getValue().toString());
                        tvInstitute.setText(snapshot.child("institute_name").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(ProfileTeacher.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}