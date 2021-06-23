package com.andhika.lets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatusActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Ruangan> arrayList;
    private FirebaseRecyclerOptions<Ruangan> options;
    private FirebaseRecyclerAdapter<Ruangan, FirebaseViewHolder> adapter;
    ListView listViewRuangan;
    List<Ruangan> ruanganList;
    ImageView nama, ruangan, tglPinjam, jamPinjam, status, btKembali;
    private DatabaseReference mDatabase;


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
        setContentView(R.layout.activity_status);

        /*
        nama = findViewById(R.id.textViewNama);
        ruangan = findViewById(R.id.textViewRuangan);
        tglPinjam = findViewById(R.id.textViewtglPinjam);
        jamPinjam = findViewById(R.id.textViewtglKembali);
        status = findViewById(R.id.status);
         */
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<Ruangan>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("ruang");
        reference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Ruangan>().setQuery(query, Ruangan.class).build();

        adapter = new FirebaseRecyclerAdapter<Ruangan, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull final Ruangan ruangan) {
                firebaseViewHolder.nama.setText(ruangan.getNamaPeminjam());
                firebaseViewHolder.ruangan.setText(ruangan.getRuanganPinjam());
                firebaseViewHolder.tglPinjam.setText(ruangan.getTglPinjam());
                firebaseViewHolder.jamPinjam.setText(ruangan.getWaktuPinjam());
                firebaseViewHolder.jamSelesai.setText(ruangan.getWaktuSelesai());
                firebaseViewHolder.keterangan.setText(ruangan.getKeperluan());
                firebaseViewHolder.status.setText(ruangan.getStatus());
                firebaseViewHolder.terima.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference mDatabaseRef = database.getReference();

                        mDatabaseRef.child("ruang/-M9hCHsPm2He9HS7Rr2J/status").setValue("ACC");
                    }
                });

                firebaseViewHolder.tolak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference mDatabaseRef = database.getReference();

                        mDatabaseRef.child("ruang/-M9hCHsPm2He9HS7Rr2J/status").setValue("Rejected");
                    }
                });
            }


            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FirebaseViewHolder(LayoutInflater.from(StatusActivity.this).inflate(R.layout.row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);


/*
        mDatabase.child("ruang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                nama.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        btKembali = (ImageView) findViewById(R.id.kembali);
        //listViewRuangan = (ListView) findViewById(R.id.listViewRuangan);
        //ruanganList = new ArrayList<>();


        btKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kembali = new Intent(StatusActivity.this, HomeActivity.class);
                startActivity(kembali);
            }
        });
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ruanganList.clear();
                for(DataSnapshot ruanganSnapshot : dataSnapshot.getChildren()){
                    Ruangan ruangan = ruanganSnapshot.getValue(Ruangan.class);

                    ruanganList.add(ruangan);
                }

                RuanganList adapter = new RuanganList(StatusActivity.this, ruanganList);
                listViewRuangan.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/

}
