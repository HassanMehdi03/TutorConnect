package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileStudent extends AppCompatActivity {

    private TextView tvEmail,tvName,tvPhone,tvLocation;
    private Button btnLogout;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);

        init();
        loadData();
        btnLogout.setOnClickListener(v->logout());

    }

    private void logout()
    {
        auth.signOut();
        startActivity(new Intent(ProfileStudent.this,LoginStudent.class));
        finish();
    }

    private void loadData()
    {
        reference.child("StudentAccount")
                .child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        tvEmail.setText(snapshot.child("email").getValue().toString());
                        tvName.setText(snapshot.child("name").getValue().toString());
                        tvPhone.setText(snapshot.child("phone").getValue().toString());
                        tvLocation.setText(snapshot.child("address").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(ProfileStudent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void init()
    {
        tvEmail=findViewById(R.id.tvEmail);
        tvName=findViewById(R.id.tvName);
        tvPhone=findViewById(R.id.tvPhone);
        tvLocation=findViewById(R.id.tvLocation);
        btnLogout=findViewById(R.id.btnLogout);

        reference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
    }

}