package com.andhika.lets;

import java.io.Serializable;

public class Rutinitas implements Serializable {
    private String Matkul, Ruangan, Hari, JamAwal, JamAkhir;

    public Rutinitas() {
        //constructor for firebase
    }

    public Rutinitas(String matkul, String ruangan, String hari, String jamAwal, String jamAkhir) {
        Matkul = matkul;
        Ruangan = ruangan;
        Hari = hari;
        JamAwal = jamAwal;
        JamAkhir = jamAkhir;
    }

    public String getMatkul() {
        return Matkul;
    }

    public void setMatkul(String matkul) {
        Matkul = matkul;
    }

    public String getRuangan() {
        return Ruangan;
    }

    public void setRuangan(String ruangan) {
        Ruangan = ruangan;
    }

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {
        Hari = hari;
    }

    public String getJamAwal() {
        return JamAwal;
    }

    public void setJamAwal(String jamAwal) {
        JamAwal = jamAwal;
    }

    public String getJamAkhir() {
        return JamAkhir;
    }

    public void setJamAkhir(String jamAkhir) {
        JamAkhir = jamAkhir;
    }
}
