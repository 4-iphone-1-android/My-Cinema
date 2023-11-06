package com.example.mycinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullName, email, dateOfBirth, phoneNumber, password, confirmPassword;
    private RadioGroup genderGroup;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button registerButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        genderGroup = findViewById(R.id.genderGroup);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        registerButton = findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String fullNameText = fullName.getText().toString();
        String emailText = email.getText().toString();
        String dateOfBirthText = dateOfBirth.getText().toString();
        String phoneNumberText = phoneNumber.getText().toString();
        String passwordText = password.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String genderText = selectedGenderRadioButton.getText().toString();

        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Add user details to Firebase Realtime Database if needed
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            // Enter user details to Firebase Realtime Database
                            ReadWriteUserDetails readWriteUserDetails = new ReadWriteUserDetails(fullNameText, dateOfBirthText, phoneNumberText, genderText);

                            // Extracting User reference for Database for "Registered Users"
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(readWriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Send verification email
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(RegisterActivity.this,
                                                "Registration successful. Please verify your email.", Toast.LENGTH_SHORT).show();

                                        // Navigate to the login activity
                                        Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this,
                                                "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            // If registration fails, display a message to the user.
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException weakPassword) {
                                password.setError("Password must be at least 6 characters");
                                password.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                email.setError("Invalid email");
                                email.requestFocus();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                email.setError("Email already registered");
                                email.requestFocus();
                            } catch (Exception e) {
                                Toast.makeText(RegisterActivity.this,
                                        "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
