package com.andhika.lets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.text.TextUtils.isEmpty;

public class PinjamActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, BottomNavigationView.OnNavigationItemSelectedListener {
    //TextView nama_ruangan, status_ruangan, deskripsi_ruangan, IDOrder;
    CheckBox check;
    Button btSubmit;
    EditText etTglPinjam, etWaktuPinjam, etWaktuSelesai, etKeperluan;
    EditText etNamaPeminjam, etNIMNIDN, etStatus;
    Spinner spinnerRuang;
    FirebaseAuth mFirebaseAuth;
    String userID, email;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    private TimePickerDialog timePickerDialog;

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;
    //FirebaseDatabase database;
    //DatabaseReference ruangan;
    public static final String TAG = "Account";

    Ruangan currentRuangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        //loadFragment(new HomeFragment());
        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //Init firebase
        database = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        //ruangan = database.getReference("Ruangan");

        //Init view
        btSubmit=(Button)findViewById(R.id.btSubmit);

        check = (CheckBox)findViewById(R.id.checkBox);
        etNIMNIDN =(EditText)findViewById(R.id.nim);
        etNamaPeminjam = (EditText)findViewById(R.id.nama);
        spinnerRuang = (Spinner) findViewById(R.id.spinner);
        etTglPinjam = (EditText)findViewById(R.id.tglPinjam);
        etWaktuPinjam = (EditText)findViewById(R.id.waktuPinjam);
        etWaktuSelesai = (EditText)findViewById(R.id.waktuSelesai);
        etKeperluan = (EditText)findViewById(R.id.keperluan);
        etStatus = (EditText)findViewById(R.id.status);
        etNIMNIDN.setVisibility(View.GONE);
        etNamaPeminjam.setVisibility(View.GONE);
        etStatus.setVisibility(View.GONE);
        etStatus.setText("Waiting");

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("user").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nama = dataSnapshot.child("nama").getValue(String.class);
                String nim = dataSnapshot.child("nim").getValue(String.class);
                etNamaPeminjam.setText(nama);
                etNIMNIDN.setText(nim);
                Log.d(TAG, nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);

        etTglPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        etWaktuPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        etWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PinjamActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etWaktuSelesai.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isEmpty(etNamaPeminjam.getText().toString()) && !isEmpty(etNIMNIDN.getText().toString()) && !isEmpty(spinnerRuang.getSelectedItem().toString()) && !isEmpty(etTglPinjam.getText().toString()) && !isEmpty(etWaktuPinjam.getText().toString()) && !isEmpty(etKeperluan.getText().toString()) && check.isChecked())
                    submitRuang(new Ruangan(etNIMNIDN.getText().toString(), etNamaPeminjam.getText().toString(), spinnerRuang.getSelectedItem().toString(), etTglPinjam.getText().toString(), etWaktuPinjam.getText().toString(), etWaktuSelesai.getText().toString(), etKeperluan.getText().toString(), etStatus.getText().toString()));
                else if (!check.isChecked())
                    Snackbar.make(findViewById(R.id.btSubmit), "Please tick the agreement above!", Snackbar.LENGTH_LONG).show();
                else
                Snackbar.make(findViewById(R.id.btSubmit), "Field cannot be empty!", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etNIMNIDN.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        etTglPinjam.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etWaktuPinjam.setText(hourOfDay + ":" + minute);
    }


    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }

    private void submitRuang(Ruangan ruang) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        userID = mFirebaseAuth.getCurrentUser().getUid();
        database.child("ruang").push().setValue(ruang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etNIMNIDN.setText("");
                etNamaPeminjam.setText("");
                etTglPinjam.setText("");
                etWaktuPinjam.setText("");
                etWaktuSelesai.setText("");
                etKeperluan.setText("");
                etStatus.setText("Waiting");
                Snackbar.make(findViewById(R.id.btSubmit), "Data added successfully", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, PinjamActivity.class);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.home_menu:
                Intent home = new Intent(PinjamActivity.this, HomeActivity.class);
                startActivity(home);
                break;
            case R.id.add:
                if(!email.equals("admin@upnvj.ac.id")) {
                    Intent pinjam = new Intent(PinjamActivity.this, PinjamActivity.class);
                    startActivity(pinjam);
                }
                else {
                    Intent add = new Intent(PinjamActivity.this, AddRutinitas.class);
                    startActivity(add);
                }
                break;
            case R.id.account_menu:
                Intent akun = new Intent(PinjamActivity.this, AccountActivity.class);
                startActivity(akun);
                break;
        }
        return false;
    }
}
