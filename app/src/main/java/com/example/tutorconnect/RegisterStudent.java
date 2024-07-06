package com.example.tutorconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterStudent extends AppCompatActivity {

    private TextInputEditText etName,etEmail,etPassword,etCPassword,etPhone,etAddress;
    private TextView tvLogin;
    private Button btnSignUp;
    private ProgressBar pbLoading;
    private DatabaseReference reference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        init();

        btnSignUp.setOnClickListener(v->singUp());
        tvLogin.setOnClickListener(v->moveToLoginPage());


    }

    private void singUp()
    {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String cPassword = etCPassword.getText().toString();
        String phone=etPhone.getText().toString().trim();
        String address=etAddress.getText().toString().trim();

        boolean emptyInputCheck = checkEmptyInput(name, email, password, cPassword, phone, address);

        if(emptyInputCheck)
        {
            HashMap<String, Object> records = new HashMap<>();
            records.put("name", name);
            records.put("phone", phone);
            records.put("address", address);

            pbLoading.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult)
                        {
                            FirebaseUser currentUser = auth.getCurrentUser();
                            if (currentUser != null) {
                                reference.child("StudentAccount")
                                        .child(currentUser.getUid())
                                        .setValue(records)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                pbLoading.setVisibility(View.GONE);
                                                Toast.makeText(RegisterStudent.this, R.string.registered_successfully, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterStudent.this, StudentWalkthrough.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                pbLoading.setVisibility(View.GONE);
                                                Toast.makeText(RegisterStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                pbLoading.setVisibility(View.GONE);
                                Toast.makeText(RegisterStudent.this, "User not authenticated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            pbLoading.setVisibility(View.GONE);
                            Toast.makeText(RegisterStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }


    }

    private boolean checkEmptyInput(String name, String email, String password, String cPassword, String phone, String address)
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
        if(phone.isEmpty()) {
            etPhone.setError(getString(R.string.please_enter_your_phone_number));
            etPhone.requestFocus();
            return false;
        }
        if(address.isEmpty()) {
            etAddress.setError(getString(R.string.please_enter_your_address));
            etAddress.requestFocus();
            return false;
        }

        if(!password.equals(cPassword))
        {
            Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void moveToLoginPage()
    {
        startActivity(new Intent(RegisterStudent.this,LoginStudent.class));
        finish();
    }

    private void init()
    {
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etCPassword=findViewById(R.id.etCPassword);
        etPhone=findViewById(R.id.etPhone);
        etAddress=findViewById(R.id.etAddress);
        tvLogin=findViewById(R.id.tvLogin);
        btnSignUp=findViewById(R.id.btnSignUp);
        pbLoading=findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.GONE);

        reference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
    }

}