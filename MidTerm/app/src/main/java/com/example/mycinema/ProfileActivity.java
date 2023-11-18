package com.example.mycinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewWelcome, textViewFullName, textViewEmail, textViewDoB, textViewPhone, textViewGender;
    private ProgressBar progressBar;
    private String fullName, email, doB, gender, phone;
    private ImageView imageViewProfile;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewWelcome = findViewById(R.id.textView_show_welcome);
        textViewFullName = findViewById(R.id.textView_show_full_name);
        textViewEmail = findViewById(R.id.textView_show_email);
        textViewDoB = findViewById(R.id.textView_show_dob);
        textViewPhone = findViewById(R.id.textView_show_phone);
        textViewGender = findViewById(R.id.textView_show_gender);
        progressBar = findViewById(R.id.progress_bar);
        imageViewProfile = findViewById(R.id.imageView_profile_dp);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "Oops! Something went wrong. User's detail are not available at this moment ", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String UID = firebaseUser.getUid();

        // Extracting user's detail from Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        databaseReference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails != null){
                    fullName = readWriteUserDetails.fullName;
                    email = firebaseUser.getEmail();
                    doB = readWriteUserDetails.dateOfBirth;
                    gender = readWriteUserDetails.gender;
                    phone = readWriteUserDetails.phoneNumber;

                    textViewWelcome.setText("Welcome, " + fullName);
                    textViewFullName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewDoB.setText(doB);
                    textViewGender.setText(gender);
                    textViewPhone.setText(phone);
                }
                progressBar.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Oops! Something went wrong. User's detail are not available at this moment ", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(ProgressBar.GONE);

            }
        });
    }
}