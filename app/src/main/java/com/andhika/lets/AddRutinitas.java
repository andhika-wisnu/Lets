package com.andhika.lets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.text.TextUtils.isEmpty;

public class AddRutinitas extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, BottomNavigationView.OnNavigationItemSelectedListener{
    Button btSubmit;
    EditText etWaktuPinjam, etWaktuSelesai, etKeperluan;
    Spinner hari, spinnerRuang;
    String email;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rutinitas);

        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Init firebase
        database = FirebaseDatabase.getInstance().getReference();

        spinnerRuang = (Spinner) findViewById(R.id.spinner);
        hari = (Spinner) findViewById(R.id.hari);
        etWaktuPinjam = (EditText)findViewById(R.id.waktuPinjam);
        etWaktuSelesai = (EditText)findViewById(R.id.waktuSelesai);
        etKeperluan = (EditText)findViewById(R.id.matkul);
        btSubmit=(Button)findViewById(R.id.btSubmit);

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
                mTimePicker = new TimePickerDialog(AddRutinitas.this, new TimePickerDialog.OnTimeSetListener() {

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

                if (!isEmpty(etKeperluan.getText().toString()) && !isEmpty(spinnerRuang.getSelectedItem().toString()) && !isEmpty(hari.getSelectedItem().toString()) && !isEmpty(etWaktuPinjam.getText().toString()) && !isEmpty(etWaktuSelesai.getText().toString()))
                    submitRoutine(new Rutinitas(etKeperluan.getText().toString(), spinnerRuang.getSelectedItem().toString(), hari.getSelectedItem().toString(), etWaktuPinjam.getText().toString(), etWaktuSelesai.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.btSubmit), "Field cannot be empty!", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etWaktuSelesai.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etWaktuPinjam.setText(hourOfDay + ":" + minute);
    }

    private void submitRoutine(Rutinitas rutinitas) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("rutinitas").push().setValue(rutinitas).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etWaktuPinjam.setText("");
                etWaktuSelesai.setText("");
                etKeperluan.setText("");
                Snackbar.make(findViewById(R.id.btSubmit), "Routine Activity added Succesfully", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.home_menu:
                Intent home = new Intent(AddRutinitas.this, HomeActivity.class);
                startActivity(home);
                break;
            case R.id.add:
                if(!email.equals("admin@upnvj.ac.id")) {
                    Intent pinjam = new Intent(AddRutinitas.this, PinjamActivity.class);
                    startActivity(pinjam);
                }
                else {
                    Intent add = new Intent(AddRutinitas.this, AddRutinitas.class);
                    startActivity(add);
                }
                break;
            case R.id.account_menu:
                Intent akun = new Intent(AddRutinitas.this, AccountActivity.class);
                startActivity(akun);
                break;
        }
        return false;
    }
}
