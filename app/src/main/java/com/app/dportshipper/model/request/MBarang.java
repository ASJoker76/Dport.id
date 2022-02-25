package com.app.dportshipper.model.request;

public class MBarang {

    private double volumeM2;
    private String id_kategori_barang;
    private String tinggi_barang;
    private String lebar_barang;
    private String panjang_barang;
    private String bobot_barang;
    private String jenis_barang;
    private String kuantitas_barang;

    public double getVolumeM2() {
        return volumeM2;
    }

    public void setVolumeM2(double volumeM2) {
        this.volumeM2 = volumeM2;
    }

    public String getId_kategori_barang() {
        return id_kategori_barang;
    }

    public void setId_kategori_barang(String id_kategori_barang) {
        this.id_kategori_barang = id_kategori_barang;
    }

    public String getTinggi_barang() {
        return tinggi_barang;
    }

    public void setTinggi_barang(String tinggi_barang) {
        this.tinggi_barang = tinggi_barang;
    }

    public String getLebar_barang() {
        return lebar_barang;
    }

    public void setLebar_barang(String lebar_barang) {
        this.lebar_barang = lebar_barang;
    }

    public String getPanjang_barang() {
        return panjang_barang;
    }

    public void setPanjang_barang(String panjang_barang) {
        this.panjang_barang = panjang_barang;
    }

    public String getBobot_barang() {
        return bobot_barang;
    }

    public void setBobot_barang(String bobot_barang) {
        this.bobot_barang = bobot_barang;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
    }

    public String getKuantitas_barang() {
        return kuantitas_barang;
    }

    public void setKuantitas_barang(String kuantitas_barang) {
        this.kuantitas_barang = kuantitas_barang;
    }
}
