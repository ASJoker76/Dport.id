package com.app.dportshipper.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MListDataBarang implements Parcelable {
      private int id_kategori_barang;
      private String nama_barang;
      private int kuantitas_barang;
      private int bobot_barang;
      private int panjang_barang;
      private int lebar_barang;
      private int tinggi_barang;

    public MListDataBarang(int id_kategori_barang, String nama_barang, int kuantitas_barang, int bobot_barang, int panjang_barang, int lebar_barang, int tinggi_barang) {
        this.id_kategori_barang = id_kategori_barang;
        this.nama_barang = nama_barang;
        this.kuantitas_barang = kuantitas_barang;
        this.bobot_barang = bobot_barang;
        this.panjang_barang = panjang_barang;
        this.lebar_barang = lebar_barang;
        this.tinggi_barang = tinggi_barang;
    }

    public MListDataBarang() {

    }



    public static final Creator<MListDataBarang> CREATOR = new Creator<MListDataBarang>() {
        @Override
        public MListDataBarang createFromParcel(Parcel in) {
            return new MListDataBarang(in);
        }

        @Override
        public MListDataBarang[] newArray(int size) {
            return new MListDataBarang[size];
        }
    };

    public int getId_kategori_barang() {
        return id_kategori_barang;
    }

    public void setId_kategori_barang(int id_kategori_barang) {
        this.id_kategori_barang = id_kategori_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
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

    protected MListDataBarang(Parcel in) {
        id_kategori_barang = in.readInt();
        nama_barang = in.readString();
        kuantitas_barang = in.readInt();
        bobot_barang = in.readInt();
        panjang_barang = in.readInt();
        lebar_barang = in.readInt();
        tinggi_barang = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_kategori_barang);
        dest.writeString(nama_barang);
        dest.writeInt(kuantitas_barang);
        dest.writeInt(bobot_barang);
        dest.writeInt(panjang_barang);
        dest.writeInt(lebar_barang);
        dest.writeInt(tinggi_barang);
    }
}

