package com.andhika.lets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RutinActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Rutinitas> arrayList;
    private FirebaseRecyclerOptions<Rutinitas> options;
    private FirebaseRecyclerAdapter<Rutinitas, FirebaseViewHolder3> adapter;
    private DatabaseReference mDatabase;
    ImageView btKembali;
    FirebaseAuth mFirebaseAuth;
    String userID;


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutin);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Rutinitas>();

        //mFirebaseAuth = FirebaseAuth.getInstance();
        //userID = mFirebaseAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rutinitas");
        mDatabase.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Rutinitas>().setQuery(mDatabase, Rutinitas.class).build();

        adapter = new FirebaseRecyclerAdapter<Rutinitas, FirebaseViewHolder3>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder3 firebaseViewHolder, int i, @NonNull final Rutinitas rutinitas) {
                firebaseViewHolder.matkul.setText(rutinitas.getMatkul());
                firebaseViewHolder.ruanganRutin.setText(rutinitas.getRuangan());
                firebaseViewHolder.hari.setText(rutinitas.getHari());
                firebaseViewHolder.jamawal.setText(rutinitas.getJamAwal());
                firebaseViewHolder.jamakhir.setText(rutinitas.getJamAkhir());
            }

            @NonNull
            @Override
            public FirebaseViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder3(LayoutInflater.from(RutinActivity.this).inflate(R.layout.rowjadwal, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);

        btKembali=(ImageView) findViewById(R.id.kembali);

        btKembali.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick (View v){
                                             Intent kembali = new Intent(RutinActivity.this, HomeActivity.class);
                                             startActivity(kembali);
                                         }
                                     }
        );
    }
}
