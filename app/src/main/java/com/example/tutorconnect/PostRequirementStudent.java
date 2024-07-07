package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PostRequirementStudent extends AppCompatActivity {

    private Button btnPost;
    private TextInputEditText etLocation, etPhone, etDetailsOfReq, etSubject, etBudget, etLanguage;
    private RadioGroup rgGenderPref, rgTutor, rgTime;
    private RadioButton selectedButtonGender, selectedButtonTutor, selectedButtonTime;
    private Spinner spLevel, spWant;
    private ProgressBar pbLoading;

    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_requirement_student);

        init();
        btnPost.setOnClickListener(v -> postRequirement());
    }

    private void postRequirement() {
        String location = etLocation.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String details = etDetailsOfReq.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String budget = etBudget.getText().toString().trim();
        String language = etLanguage.getText().toString().trim();
        int selectedIdGender = rgGenderPref.getCheckedRadioButtonId();
        int selectedIdTutor = rgTutor.getCheckedRadioButtonId();
        int selectedIdTime = rgTime.getCheckedRadioButtonId();

        selectedButtonGender = findViewById(selectedIdGender);
        selectedButtonTutor = findViewById(selectedIdTutor);
        selectedButtonTime = findViewById(selectedIdTime);

        String level = spLevel.getSelectedItem().toString();
        String want = spWant.getSelectedItem().toString();

        boolean emptyInputCheck = checkEmptyInput(location, phone, details, subject
                , budget, language, selectedIdGender, selectedIdTutor, selectedIdTime, level, want);

        if (emptyInputCheck) {
            String gender = selectedButtonGender.getText().toString();
            String tutor = selectedButtonTutor.getText().toString();
            String time = selectedButtonTime.getText().toString();

            pbLoading.setVisibility(View.VISIBLE);

            reference.child("StudentAccount")
                    .child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String email = snapshot.child("email").getValue().toString();

                            HashMap<String, Object> records = new HashMap<>();
                            records.put("location", location);
                            records.put("phone", phone);
                            records.put("email", email);
                            records.put("details", details);
                            records.put("subject", subject);
                            records.put("budget", budget);
                            records.put("language", language);
                            records.put("gender", gender);
                            records.put("tutor", tutor);
                            records.put("time", time);
                            records.put("level", level);
                            records.put("want", want);

                            reference.child("PostRequirement")
                                    .child(user.getUid())
                                    .setValue(records)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pbLoading.setVisibility(View.GONE);
                                            Toast.makeText(PostRequirementStudent.this, R.string.requirement_posted, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(PostRequirementStudent.this, StudentHomePage.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pbLoading.setVisibility(View.GONE);
                                            Toast.makeText(PostRequirementStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            pbLoading.setVisibility(View.GONE);
                            Toast.makeText(PostRequirementStudent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean checkEmptyInput(String location, String phone, String details,
                                    String subject, String budget, String language,
                                    int selectedIdGender, int selectedIdTutor, int selectedIdTime,
                                    String level, String want) {
        if (location.isEmpty()) {
            etLocation.setError(getString(R.string.please_enter_location));
            etLocation.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            etPhone.setError(getString(R.string.please_enter_your_phone_number));
            etPhone.requestFocus();
            return false;
        }
        if (details.isEmpty()) {
            etDetailsOfReq.setError(getString(R.string.please_enter_details_of_requirement));
            etDetailsOfReq.requestFocus();
            return false;
        }
        if (subject.isEmpty()) {
            etSubject.setError(getString(R.string.please_enter_your_subject));
            etSubject.requestFocus();
            return false;
        }
        if (budget.isEmpty()) {
            etBudget.setError(getString(R.string.please_enter_your_budget));
            etBudget.requestFocus();
            return false;
        }
        if (language.isEmpty()) {
            etLanguage.setError(getString(R.string.please_enter_language));
            etLanguage.requestFocus();
            return false;
        }
        if (selectedIdGender == -1) {
            Toast.makeText(this, R.string.please_select_gender_preference, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedIdTutor == -1) {
            Toast.makeText(this, R.string.please_select_tutors_wanted, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (selectedIdTime == -1) {
            Toast.makeText(this, R.string.please_select_time_preference, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (level.equals("-- Select Level --")) {
            Toast.makeText(this, R.string.please_select_your_level, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (want.equals("-- Select --")) {
            Toast.makeText(this, R.string.please_select_your_want, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init() {
        btnPost = findViewById(R.id.btnPost);
        etLocation = findViewById(R.id.etLocation);
        etPhone = findViewById(R.id.etPhone);
        etDetailsOfReq = findViewById(R.id.etDetails);
        etSubject = findViewById(R.id.etSubject);
        etBudget = findViewById(R.id.etBudget);
        etLanguage = findViewById(R.id.etLanguage);
        spLevel = findViewById(R.id.spLevel);
        spWant = findViewById(R.id.spWant);
        rgGenderPref = findViewById(R.id.radioGroup_gender);
        rgTutor = findViewById(R.id.radioGroup_tutor);
        rgTime = findViewById(R.id.radioGroup_time);
        pbLoading = findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.GONE);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
}
