package com.andhika.lets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.Query;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TerjadwalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Ruangan> arrayList;
    private FirebaseRecyclerOptions<Ruangan> options;
    private FirebaseRecyclerAdapter<Ruangan, FirebaseViewHolder2> adapter;
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
        setContentView(R.layout.activity_terjadwal);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Ruangan>();

        mFirebaseAuth = FirebaseAuth.getInstance();
        userID = mFirebaseAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("ruang").orderByChild("status").equalTo("ACC");
        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Ruangan>().setQuery(query, Ruangan.class).build();

        adapter = new FirebaseRecyclerAdapter<Ruangan, FirebaseViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder2 firebaseViewHolder, int i, @NonNull final Ruangan ruangan) {
                firebaseViewHolder.nama.setText(ruangan.getNamaPeminjam());
                firebaseViewHolder.ruangan.setText(ruangan.getRuanganPinjam());
                firebaseViewHolder.tglPinjam.setText(ruangan.getTglPinjam());
                firebaseViewHolder.jamPinjam.setText(ruangan.getWaktuPinjam());
                firebaseViewHolder.jamSelesai.setText(ruangan.getWaktuSelesai());
                firebaseViewHolder.keterangan.setText(ruangan.getKeperluan());
                firebaseViewHolder.status.setText(ruangan.getStatus());
            }

            @NonNull
            @Override
            public FirebaseViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder2(LayoutInflater.from(TerjadwalActivity.this).inflate(R.layout.row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);

        btKembali=(ImageView) findViewById(R.id.kembali);

        btKembali.setOnClickListener(new View.OnClickListener()
                                     {
                                         @Override
                                         public void onClick (View v){
                                             Intent kembali = new Intent(TerjadwalActivity.this, HomeActivity.class);
                                             startActivity(kembali);
                                         }
                                     }
        );
    }
}


