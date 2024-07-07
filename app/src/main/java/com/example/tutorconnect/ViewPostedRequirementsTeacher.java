package com.example.tutorconnect;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewPostedRequirementsTeacher extends AppCompatActivity {

    private RecyclerView rvList;
    private ViewPostedRequirementAdapterTeacher adapter;
    private ArrayList<PostRequirement> postRequirements;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_posted_requirements_teacher);

        init();
        loadData();
        setAdapter();

    }

    private void setAdapter()
    {
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ViewPostedRequirementAdapterTeacher(this,postRequirements);
        rvList.setAdapter(adapter);
    }

    private void loadData()
    {
        reference.child("PostRequirement")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                String location=dataSnapshot.child("location").getValue().toString();
                                String phone=dataSnapshot.child("phone").getValue().toString();
                                String email=dataSnapshot.child("email").getValue().toString();
                                String details=dataSnapshot.child("details").getValue().toString();
                                String subject=dataSnapshot.child("subject").getValue().toString();
                                String budget=dataSnapshot.child("budget").getValue().toString();
                                String language=dataSnapshot.child("language").getValue().toString();
                                String gender=dataSnapshot.child("gender").getValue().toString();
                                String tutor=dataSnapshot.child("tutor").getValue().toString();
                                String time=dataSnapshot.child("time").getValue().toString();
                                String level=dataSnapshot.child("level").getValue().toString();
                                String want=dataSnapshot.child("want").getValue().toString();

                                postRequirements.add(new PostRequirement(location,phone,email,details,subject,budget,language,gender,tutor,time,level,want));
                            }
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ViewPostedRequirementsTeacher.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void init()
    {
        rvList = findViewById(R.id.rvList);
        postRequirements = new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference();
    }

}