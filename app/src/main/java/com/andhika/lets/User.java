package com.andhika.lets;

import java.io.Serializable;

public class User implements Serializable {
    private String Nim, Nama;

    public User() {
        //constructor for firebase
    }

    public User(String nama, String nim) {
        Nim = nim;
        Nama = nama;
    }

    public String getNim() {
        return Nim;
    }

    public void setNim(String nim) {
        Nim = nim;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }
}
