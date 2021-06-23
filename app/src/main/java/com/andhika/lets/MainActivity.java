package com.andhika.lets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText emailId, password, mFullname, fNim;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    String userID;
    private DatabaseReference database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        mFullname = findViewById(R.id.nama);
        fNim = findViewById(R.id.nim);
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btnSignUp = findViewById(R.id.signup);
        tvSignIn = findViewById(R.id.signIn);




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                String nama = mFullname.getText().toString();
                String nim = fNim.getText().toString();
                if (nama.isEmpty()) {
                    mFullname.setError("Please enter your fullname");
                    mFullname.requestFocus();
                } else if (nim.isEmpty()) {
                    fNim.setError("Please enter your NIM");
                    fNim.requestFocus();
                } else if (email.isEmpty()) {
                    emailId.setError("Please enter your email");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (pwd.length() < 8){
                    password.setError("Please enter password at least 8 character");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your email and password!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Sign Up failed, Try again", Toast.LENGTH_SHORT).show();
                            } else {
                                submitUser(new User(mFullname.getText().toString(), fNim.getText().toString()));

                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                            InputMethodManager imm = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(
                                    fNim.getWindowToken(), 0);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void submitUser(User user) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        FirebaseUser fuser = mFirebaseAuth.getCurrentUser();
        userID = mFirebaseAuth.getCurrentUser().getUid();
        database.child("user").child(userID).setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFullname.setText("");
                fNim.setText("");
                Snackbar.make(findViewById(R.id.signup), "User added successfully", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}

