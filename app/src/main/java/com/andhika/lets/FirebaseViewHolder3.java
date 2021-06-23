package com.andhika.lets;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder3 extends RecyclerView.ViewHolder{

    public TextView matkul, ruanganRutin, hari, jamawal, jamakhir;
    String email;


    public FirebaseViewHolder3(@NonNull View itemView){
        super(itemView);

        matkul = itemView.findViewById(R.id.matkul);
        ruanganRutin = itemView.findViewById(R.id.ruanganRutin);
        hari = itemView.findViewById(R.id.hari);
        jamawal = itemView.findViewById(R.id.jamawal);
        jamakhir = itemView.findViewById(R.id.jamakhir);

    }
}
