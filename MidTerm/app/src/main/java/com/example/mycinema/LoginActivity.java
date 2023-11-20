package com.example.mycinema;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycinema.Screen.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signupText, forgotPasswordText;

    private FirebaseAuth mAuth;
    public  static String uID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.registerText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if email and password are not empty
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Authenticate user with Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) { // Sign in success
                                    // Check if user has verified email
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user.isEmailVerified()) {
                                        uID = user.getUid();
                                        Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut(); // Sign out user
                                        showAlertDialog();
                                    }


                                } else {
                                    // If sign in fails, display a message to the user.
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        usernameEditText.setError("User does not exist or has been deleted. Please register again.");
                                        usernameEditText.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        usernameEditText.setError("Invalid email or password. Kindly, check check and re-enter.");
                                        usernameEditText.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verification Email Sent");
        builder.setMessage("A verification email has been sent to your email address. Please click 'Continue' to open Gmail and verify your email.");
        // Open Gmail app to verify email
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Launch the app in a new task
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // Check if user is already logged in and redirect to MainActivity
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
            uID = currentUser.getUid();
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();
        }
    }
}
