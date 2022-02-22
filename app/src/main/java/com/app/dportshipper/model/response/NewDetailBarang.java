package com.app.dportshipper.model.response;

public class NewDetailBarang {

    private int nilai_barang;
    private int tinggi;
    private int panjang;
    private int lebar;
    private String keterangan;
    private float bobot_barang;
    private int berat;
    private String jenis_barang;
    private int id;

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getNilai_barang() {
        return nilai_barang;
    }

    public void setNilai_barang(int nilai_barang) {
        this.nilai_barang = nilai_barang;
    }

    public int getTinggi() {
        return tinggi;
    }

    public void setTinggi(int tinggi) {
        this.tinggi = tinggi;
    }

    public int getPanjang() {
        return panjang;
    }

    public void setPanjang(int panjang) {
        this.panjang = panjang;
    }

    public int getLebar() {
        return lebar;
    }

    public void setLebar(int lebar) {
        this.lebar = lebar;
    }

    public float getBobot_barang() {
        return bobot_barang;
    }

    public void setBobot_barang(float bobot_barang) {
        this.bobot_barang = bobot_barang;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
