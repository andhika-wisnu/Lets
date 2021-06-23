package com.andhika.lets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class EditAccount extends AppCompatActivity {

    EditText etNama, etNim;
    Button save;
    String userID;
    FirebaseAuth mFirebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        etNama = (EditText)findViewById(R.id.nama);
        etNim = (EditText)findViewById(R.id.nim);
        save = (Button)findViewById(R.id.save);
        database = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isEmpty(etNama.getText().toString()) && !isEmpty(etNim.getText().toString())) {
                    submitRuang(new User(etNama.getText().toString(), etNim.getText().toString()));
                    Intent inToEdit = new Intent(EditAccount.this, AccountActivity.class);
                    startActivity(inToEdit);
                }
                else {
                    Snackbar.make(findViewById(R.id.save), "Field cannot be empty!", Snackbar.LENGTH_LONG).show();
                }

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etNama.getWindowToken(), 0);


            }
        });
    }

    private void submitRuang(User user) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        userID = mFirebaseAuth.getCurrentUser().getUid();
        database.child("user").child(userID).setValue(user).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNama.setText("");
                etNim.setText("");

                Snackbar.make(findViewById(R.id.save), "Data updated successfully", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
