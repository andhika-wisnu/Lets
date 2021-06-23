package com.andhika.lets;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

public class Ruangan implements Serializable {
    private String Nim, NamaPeminjam, RuanganPinjam, Gambar, Deskripsi, BerandaId, Status, TglPinjam, WaktuPinjam, WaktuSelesai, Keperluan;


    public Ruangan() {
        //constructor for firebase
    }

    public Ruangan(String nim, String namaPeminjam, String ruanganPinjam, String tglPinjam, String waktuPinjam, String waktuSelesai, String keperluan, String status) {
        Nim = nim;
        NamaPeminjam = namaPeminjam;
        RuanganPinjam = ruanganPinjam;
        TglPinjam = tglPinjam;
        WaktuPinjam = waktuPinjam;
        WaktuSelesai = waktuSelesai;
        Keperluan = keperluan;
        Status = status;
    }

    public String getNim() {
        return Nim;
    }

    public void setNim(String nim) {
        Nim = nim;
    }

    public String getNamaPeminjam() {
        return NamaPeminjam;
    }

    public void setNamaPeminjam(String namaPeminjam) {
        NamaPeminjam = namaPeminjam;
    }

    public String getRuanganPinjam() {
        return RuanganPinjam;
    }

    public void setRuanganPinjam(String ruanganPinjam) {
        RuanganPinjam = ruanganPinjam;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getBerandaId() {
        return BerandaId;
    }

    public void setBerandaId(String berandaId) {
        BerandaId = berandaId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTglPinjam() {
        return TglPinjam;
    }

    public void setTglPinjam(String tglPinjam) {
        TglPinjam = tglPinjam;
    }

    public String getWaktuPinjam() {
        return WaktuPinjam;
    }

    public void setWaktuPinjam(String waktuPinjam) {
        WaktuPinjam = waktuPinjam;
    }

    public String getWaktuSelesai() {
        return WaktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        WaktuSelesai = waktuSelesai;
    }

    public String getKeperluan() {
        return Keperluan;
    }

    public void setKeperluan(String keperluan) {
        Keperluan = keperluan;
    }
}
