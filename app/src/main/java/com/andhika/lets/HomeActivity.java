package com.andhika.lets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
CardView btnPinjam, btnStatus, btnTerjadwal, btnRutin;
String email;
TextView etNama;
public static final String TAG = "Account";
FirebaseAuth mFirebaseAuth;
private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);


        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        //loadFragment(new HomeFragment());
        // inisialisasi BottomNavigaionView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        // beri listener pada saat item/menu bottomnavigation terpilih
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        //btnPinjam = findViewById(R.id.pinjam);
        btnStatus = findViewById(R.id.status);
        btnRutin = findViewById(R.id.btRutin);
        btnTerjadwal = findViewById(R.id.btTerjadwal);
        etNama = findViewById(R.id.nama);



        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("user").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nama = dataSnapshot.child("nama").getValue(String.class);
                etNama.setText(nama);
                Log.d(TAG, nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);


/*
        btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pinjam = new Intent(HomeActivity.this, PinjamActivity.class);
                startActivity(pinjam);
            }
        });
*/
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent status = new Intent(HomeActivity.this, StatusActivity.class);
                startActivity(status);
            }
        });

        btnTerjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jadwal = new Intent(HomeActivity.this, TerjadwalActivity.class);
                startActivity(jadwal);
            }
        });

        btnRutin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rutin = new Intent(HomeActivity.this, RutinActivity.class);
                startActivity(rutin);
            }
        });



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
                Intent home = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(home);
                break;
            case R.id.add:
                if(!email.equals("admin@upnvj.ac.id")) {
                    Intent pinjam = new Intent(HomeActivity.this, PinjamActivity.class);
                    startActivity(pinjam);
                }
                else {
                    Intent add = new Intent(HomeActivity.this, AddRutinitas.class);
                    startActivity(add);
                }
                break;
            case R.id.account_menu:
                Intent akun = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(akun);
                break;
        }
        return false;
    }
}
