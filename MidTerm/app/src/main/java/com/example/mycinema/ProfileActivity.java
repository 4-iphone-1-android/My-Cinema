package com.example.mycinema;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.content.Context;
import android.Manifest;
import com.bumptech.glide.Glide;


import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewWelcome, textViewFullName, textViewEmail, textViewDoB, textViewPhone, textViewGender;
    private ProgressBar progressBar;
    private String fullName, email, doB, gender, phone;
    private ImageView imageViewProfile;
    private FirebaseAuth mAuth;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int CAMERA_PERMISSION_CODE = 1001;
    private Uri imageUri;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink)));

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

        String UID = mAuth.getCurrentUser().getUid();
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("Registered Users").child(UID);
        // Set an OnClickListener for the profile image view
        imageViewProfile.setOnClickListener(v -> {
            // Check if the CAMERA permission is already granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Camera permission is already granted, proceed with taking a picture
                showImageSelectionDialog();
            } else {
                // Request CAMERA permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed with taking a picture
                showImageSelectionDialog();
            } else {
                // Camera permission denied
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select New Image");
        String[] options = {"Take a picture", "Pick from gallery"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    dispatchTakePictureIntent();
                    break;
                case 1:
                    dispatchPickImageIntent();
                    break;
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchPickImageIntent() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    // Get the image URI from the captured image
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageUri = getImageUri(this, imageBitmap);
                    // Upload the image to Firebase Storage
                    uploadImageToFirebaseStorage();
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Get the image URI from the gallery
                imageUri = data.getData();
                // Upload the image to Firebase Storage
                uploadImageToFirebaseStorage();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        if (imageUri != null) {
            // Upload the image to Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                    .child("profile_images")
                    .child(mAuth.getCurrentUser().getUid() + ".jpg");

            storageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image uploaded successfully
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Update the user's profile with the image URL
                            userDatabaseRef.child("profileImageUrl").setValue(uri.toString());
                            // Update the image view with the selected image
                            imageViewProfile.setImageURI(imageUri);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Log.e("ProfileActivity", "Image upload failed: " + e.getMessage());
                        Toast.makeText(ProfileActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String UID = firebaseUser.getUid();

        // Extracting user's detail from Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
        databaseReference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails != null) {
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

                    // Load the profile image using Glide
                    String imageUrl = readWriteUserDetails.profileImageUrl;
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(ProfileActivity.this)
                                .load(imageUrl)
                                .into(imageViewProfile);
                    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int UID = item.getItemId();
        if (UID == R.id.menu_update_profile) {
            Intent intent = new Intent(this, UpdateProfileActivity.class);
            startActivity(intent);
        } else if (UID == R.id.menu_change_password) {
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (UID == R.id.menu_delete_account) {
            Intent intent = new Intent(this, DeleteAccountActivity.class);
            startActivity(intent);
        } else if (UID == R.id.menu_logout) {
            logoutUser();
        } else if (UID == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        mAuth.signOut();
        Intent mainIntent = new Intent(this, LoginActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }
}