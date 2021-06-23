package com.andhika.lets;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {
    public TextView nama, ruangan, tglPinjam, jamPinjam, jamSelesai, keterangan, status;
    public TextView matkul, ruanganRutin, hari, jamawal, jamakhir;
    public Button terima, tolak;
    String email;


    public FirebaseViewHolder(@NonNull View itemView){
        super(itemView);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        nama = itemView.findViewById(R.id.nama);
        ruangan = itemView.findViewById(R.id.ruangan);
        tglPinjam = itemView.findViewById(R.id.tglPinjam);
        jamPinjam = itemView.findViewById(R.id.jamPinjam);
        jamSelesai = itemView.findViewById(R.id.jamselesai);
        keterangan = itemView.findViewById(R.id.keterangan);
        status = itemView.findViewById(R.id.status);
        terima = itemView.findViewById(R.id.accept);
        tolak = itemView.findViewById(R.id.decline);


        if(!email.equals("admin@upnvj.ac.id")){
            terima.setVisibility(View.GONE);
            tolak.setVisibility(View.GONE);
        }

    }
}
