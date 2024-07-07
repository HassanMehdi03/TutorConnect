package com.example.tutorconnect;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindTutorStudent extends AppCompatActivity {

    private RecyclerView rvList;
    private FindTutorStudentAdapter adapter;
    private ArrayList<TeacherInfo> teacherInfos;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor_student);

        init();
        loadData();
        setAdapter();
    }

    private void setAdapter()
    {
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new FindTutorStudentAdapter(this,teacherInfos);
        rvList.setAdapter(adapter);
    }

    private void loadData()
    {
        reference.child("TeacherAccount")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if(snapshot.exists())
                        {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                String name=dataSnapshot.child("name").getValue().toString();
                                String email=dataSnapshot.child("email").getValue().toString();
                                String speciality=dataSnapshot.child("speciality").getValue().toString();
                                String location=dataSnapshot.child("location").getValue().toString();
                                String postal=dataSnapshot.child("postal").getValue().toString();
                                String phone=dataSnapshot.child("phone").getValue().toString();
                                String subject=dataSnapshot.child("subject").getValue().toString();
                                String institute_name=dataSnapshot.child("institute_name").getValue().toString();
                                String degree=dataSnapshot.child("degree").getValue().toString();
                                String experience=dataSnapshot.child("experience").getValue().toString();
                                String cgpa=dataSnapshot.child("cgpa").getValue().toString();
                                String organization=dataSnapshot.child("organization").getValue().toString();
                                String job_description=dataSnapshot.child("job_description").getValue().toString();
                                String fee=dataSnapshot.child("fee").getValue().toString();
                                String profile=dataSnapshot.child("profile").getValue().toString();
                                String gender=dataSnapshot.child("gender").getValue().toString();
                                String dob=dataSnapshot.child("dob").getValue().toString();
                                String grade=dataSnapshot.child("grade").getValue().toString();

                                teacherInfos.add(new TeacherInfo(name,email,speciality,location,postal,
                                        phone,subject,institute_name,degree,experience,cgpa,organization
                                        ,job_description,fee,profile,gender,dob,grade));

                            }
                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FindTutorStudent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void init()
    {
        rvList = findViewById(R.id.rvList);
        teacherInfos = new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference();
    }

}