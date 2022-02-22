package com.app.dportshipper.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewBarang implements Parcelable {

    private int id_kategori_barang;
    //private String nama_barang;
    private int kuantitas_barang;
    private int bobot_barang;
    private int panjang_barang;
    private int lebar_barang;
    private int tinggi_barang;
    private int nilai_barang;
    private String keterangan;

    public NewBarang(int id_kategori_barang, int kuantitas_barang, int bobot_barang, int panjang_barang, int lebar_barang, int tinggi_barang, int nilai_barang,String keterangan) {
        this.id_kategori_barang = id_kategori_barang;
        //this.nama_barang = nama_barang;
        this.kuantitas_barang = kuantitas_barang;
        this.bobot_barang = bobot_barang;
        this.panjang_barang = panjang_barang;
        this.lebar_barang = lebar_barang;
        this.tinggi_barang = tinggi_barang;
        this.nilai_barang = nilai_barang;
        this.keterangan = keterangan;
    }

    public NewBarang() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //The parcelable object has to be the first one
        dest.writeInt(this.id_kategori_barang);
        //dest.writeString(this.nama_barang);
        dest.writeInt(this.kuantitas_barang);
        dest.writeInt(this.bobot_barang);
        dest.writeInt(this.panjang_barang);
        dest.writeInt(this.lebar_barang);
        dest.writeInt(this.tinggi_barang);
        dest.writeInt(this.nilai_barang);
        dest.writeString(this.keterangan);
    }

    protected NewBarang(Parcel in) {
        // The order of the properties has to be the same in the writeToParcel method
        this.id_kategori_barang = in.readInt();
        //this.nama_barang = in.readString();
        this.kuantitas_barang = in.readInt();
        this.bobot_barang = in.readInt();
        this.panjang_barang = in.readInt();
        this.lebar_barang = in.readInt();
        this.tinggi_barang = in.readInt();
        this.nilai_barang = in.readInt();
        this.keterangan = in.readString();
    }

    public static final Creator<NewBarang> CREATOR = new Creator<NewBarang>() {
        public NewBarang createFromParcel(Parcel source) {
            return new NewBarang(source);
        }
        public NewBarang[] newArray(int size) {
            return new NewBarang[size];
        }
    };


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //The following are just setter and getter methods
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getId_kategori_barang() {
        return id_kategori_barang;
    }

    public void setId_kategori_barang(int id_kategori_barang) {
        this.id_kategori_barang = id_kategori_barang;
    }

//    public String getNama_barang() {
//        return nama_barang;
//    }
//
//    public void setNama_barang(String nama_barang) {
//        this.nama_barang = nama_barang;
//    }

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

    public int getNilai_barang() {
        return nilai_barang;
    }

    public void setNilai_barang(int nilai_barang) {
        this.nilai_barang = nilai_barang;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public static Creator<NewBarang> getCREATOR() {
        return CREATOR;
    }
}
