package com.app.dportshipper.model.response;

public class ResDetailPengirimanBarang {

    private int id_order_barang;
    private int id_kategori_barang;
    private int kuantitas_barang;
    private int bobot_barang;
    private int panjang_barang;
    private int lebar_barang;
    private int tinggi_barang;
    private String jenis_barang;


    public int getId_order_barang() {
        return id_order_barang;
    }

    public void setId_order_barang(int id_order_barang) {
        this.id_order_barang = id_order_barang;
    }

    public int getId_kategori_barang() {
        return id_kategori_barang;
    }

    public void setId_kategori_barang(int id_kategori_barang) {
        this.id_kategori_barang = id_kategori_barang;
    }

    public int getKuantitas_barang() {
        return kuantitas_barang;
    }

    public void setKuantitas_barang(int kuantitas_barang) {
        this.kuantitas_barang = kuantitas_barang;
    }

    public int getBobot_barang() {
        return bobot_barang;
    }

    public void setBobot_barang(int bobot_barang) {
        this.bobot_barang = bobot_barang;
    }

    public int getPanjang_barang() {
        return panjang_barang;
    }

    public void setPanjang_barang(int panjang_barang) {
        this.panjang_barang = panjang_barang;
    }

    public int getLebar_barang() {
        return lebar_barang;
    }

    public void setLebar_barang(int lebar_barang) {
        this.lebar_barang = lebar_barang;
    }

    public int getTinggi_barang() {
        return tinggi_barang;
    }

    public void setTinggi_barang(int tinggi_barang) {
        this.tinggi_barang = tinggi_barang;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
    }
}
