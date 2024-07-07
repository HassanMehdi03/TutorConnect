package com.example.tutorconnect;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class RegisterTeacher extends AppCompatActivity {

    private TextInputEditText etName,etEmail,etPassword,etCPassword,etSpeciality,
            etLocation,etPostal,etPhone,etSubject,etInstituteName,etDegree,etExperience,
            etCgpa,etOrganization,etJobDescription,etFee,etProfileDescription;

    private ProgressBar pbLoading;
    private Button btnDOB,btnSignUp;
    private TextView tvLogin;
    private RadioGroup rgGender;
    private RadioButton selectButton;
    private Spinner spGrade;
    private int finalDay,finalMonth,finalYear;
    private DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_teacher);

        init();

        btnSignUp.setOnClickListener(v->singUp());
        btnDOB.setOnClickListener(v->showDateDialog());
        tvLogin.setOnClickListener(v->moveToLoginPage());
    }

    private void moveToLoginPage()
    {
        startActivity(new Intent(RegisterTeacher.this,LoginTeacher.class));
        finish();
    }

    private void showDateDialog()
    {
        final Calendar c=Calendar.getInstance();

        int day=c.get(Calendar.DAY_OF_MONTH);
        int month=c.get(Calendar.MONTH);
        int year=c.get(Calendar.YEAR);

        DatePickerDialog datePicker=new DatePickerDialog(RegisterTeacher.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                finalYear = selectedYear;
                finalMonth = selectedMonth + 1; // Month is zero-indexed in Calendar class
                finalDay = selectedDay;
            }
        }, year, month, day);

        datePicker.show();
    }

    private void singUp() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String cPassword = etCPassword.getText().toString();
        String speciality = etSpeciality.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String postal = etPostal.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String instituteName = etInstituteName.getText().toString().trim();
        String degree = etDegree.getText().toString().trim();
        String experience = etExperience.getText().toString().trim();
        String cgpa = etCgpa.getText().toString().trim();
        String organization = etOrganization.getText().toString().trim();
        String jobDescription = etJobDescription.getText().toString().trim();
        String fee = etFee.getText().toString().trim();
        String profile = etProfileDescription.getText().toString().trim();
        int selectedID = rgGender.getCheckedRadioButtonId();

        selectButton = findViewById(selectedID);
        String grade = spGrade.getSelectedItem().toString();

        boolean emptyInputCheck = checkEmptyInput(name, email, password, cPassword, speciality, location
                , postal, phone, subject, instituteName, degree, experience, cgpa,
                organization, jobDescription, fee, profile, selectedID, grade);

        if (emptyInputCheck) {
            String gender = selectButton.getText().toString();
            String dob = finalDay + "/" + finalMonth + "/" + finalYear;

            HashMap<String, Object> records = new HashMap<>();
            records.put("name", name);
            records.put("email", email);
            records.put("speciality", speciality);
            records.put("location", location);
            records.put("postal", postal);
            records.put("phone", phone);
            records.put("subject", subject);
            records.put("institute_name", instituteName);
            records.put("degree", degree);
            records.put("experience", experience);
            records.put("cgpa", cgpa);
            records.put("organization", organization);
            records.put("job_description", jobDescription);
            records.put("fee", fee);
            records.put("profile", profile);
            records.put("gender", gender);
            records.put("dob", dob);
            records.put("grade", grade);

            pbLoading.setVisibility(View.VISIBLE);

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null) {
                                reference.child("TeacherAccount")
                                        .child(currentUser.getUid())
                                        .setValue(records)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pbLoading.setVisibility(View.GONE);
                                                Toast.makeText(RegisterTeacher.this, R.string.registered_successfully, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterTeacher.this, TeacherWalkthrough.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pbLoading.setVisibility(View.GONE);
                                                Toast.makeText(RegisterTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(RegisterTeacher.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pbLoading.setVisibility(View.GONE);
                            Toast.makeText(RegisterTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean checkEmptyInput(String name, String email, String password, String cPassword,
                                    String speciality, String location, String postal, String phone,
                                    String subject, String instituteName, String degree, String experience,
                                    String cgpa, String organization, String jobDescription, String fee,
                                    String profile, int selectedID, String grade)
    {
        if(name.isEmpty()) {
            etName.setError(getString(R.string.please_enter_your_name));
            etName.requestFocus();
            return false;
        }
        if(email.isEmpty()) {
            etEmail.setError(getString(R.string.please_enter_your_email));
            etEmail.requestFocus();
            return false;
        }
        if(password.isEmpty()) {
            etPassword.setError(getString(R.string.please_enter_your_password));
            etPassword.requestFocus();
            return false;
        }
        if(cPassword.isEmpty()) {
            etCPassword.setError(getString(R.string.please_confirm_your_password));
            etCPassword.requestFocus();
            return false;
        }

        if(selectedID == -1)
        {
            Toast.makeText(this, R.string.please_select_your_gender, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(speciality.isEmpty()) {
            etSpeciality.setError(getString(R.string.please_enter_your_speciality));
            etSpeciality.requestFocus();
            return false;
        }
        if(location.isEmpty()) {
            etLocation.setError(getString(R.string.please_enter_your_location));
            etLocation.requestFocus();
            return false;
        }
        if(postal.isEmpty()) {
            etPostal.setError(getString(R.string.please_enter_your_postal_code));
            etPostal.requestFocus();
            return false;
        }
        if(phone.isEmpty()) {
            etPhone.setError(getString(R.string.please_enter_your_phone_number));
            etPhone.requestFocus();
            return false;
        }
        if(subject.isEmpty()) {
            etSubject.setError(getString(R.string.please_enter_your_subject));
            etSubject.requestFocus();
            return false;
        }

        if(grade.equals("Select Grade"))
        {
            Toast.makeText(this, R.string.please_select_your_grade, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(instituteName.isEmpty()) {
            etInstituteName.setError(getString(R.string.please_enter_your_institute_name));
            etInstituteName.requestFocus();
            return false;
        }
        if(degree.isEmpty()) {
            etDegree.setError(getString(R.string.please_enter_your_degree));
            etDegree.requestFocus();
            return false;
        }
        if(experience.isEmpty()) {
            etExperience.setError(getString(R.string.please_enter_your_experience));
            etExperience.requestFocus();
            return false;
        }
        if(cgpa.isEmpty()) {
            etCgpa.setError(getString(R.string.please_enter_your_cgpa));
            etCgpa.requestFocus();
            return false;
        }
        if(organization.isEmpty()) {
            etOrganization.setError(getString(R.string.please_enter_your_organization));
            etOrganization.requestFocus();
            return false;
        }
        if(jobDescription.isEmpty()) {
            etJobDescription.setError(getString(R.string.please_enter_your_job_description));
            etJobDescription.requestFocus();
            return false;
        }
        if(fee.isEmpty()) {
            etFee.setError(getString(R.string.please_enter_your_fee));
            etFee.requestFocus();
            return false;
        }
        if(profile.isEmpty()) {
            etProfileDescription.setError(getString(R.string.please_enter_your_profile));
            etProfileDescription.requestFocus();
            return false;
        }
        if(!password.equals(cPassword)) {
            Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(finalDay==0 && finalMonth==0 && finalYear==0)
        {
            Toast.makeText(this, R.string.please_select_your_date_of_birth, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void init()
    {
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etCPassword=findViewById(R.id.etCPassword);
        etSpeciality=findViewById(R.id.etSpeciality);
        etLocation=findViewById(R.id.etLocation);
        etPostal=findViewById(R.id.etPostal);
        etPhone=findViewById(R.id.etPhone);
        etSubject=findViewById(R.id.etSubject);
        etInstituteName=findViewById(R.id.etInstituteName);
        etDegree=findViewById(R.id.etDegree);
        etExperience=findViewById(R.id.etExperience);
        etCgpa=findViewById(R.id.etCgpa);
        etOrganization=findViewById(R.id.etOrganization);
        etJobDescription=findViewById(R.id.etJobDescription);
        etFee=findViewById(R.id.etFee);
        etProfileDescription=findViewById(R.id.etProfile);
        btnDOB=findViewById(R.id.btnDOB);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvLogin=findViewById(R.id.tvLogin);
        spGrade=findViewById(R.id.spGrade);
        rgGender=findViewById(R.id.radioGroup_gender);
        pbLoading=findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.GONE);

        reference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();

    }

}