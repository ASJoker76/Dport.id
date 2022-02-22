package com.app.dportshipper.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NewAlamat implements Parcelable {

    private String alamat;
    private String nama_penerima;
    private String no_hp;
    private String prov;
    private String kab;
    private String kec;
    private String kel;
    private int kode_pos;
    private String ktp_penerima;
    private int cost;

    private ArrayList<NewBarang> detail_barang = new ArrayList<>();

    public NewAlamat(String alamat, String nama_penerima, String no_hp, String prov, String kab, String kec, String kel,int kode_pos,String ktp_penerima,int cost, ArrayList<NewBarang> detail_barang) {
        this.alamat = alamat;
        this.nama_penerima = nama_penerima;
        this.no_hp = no_hp;
        this.prov = prov;
        this.kab = kab;
        this.kec = kec;
        this.kel = kel;
        this.kode_pos = kode_pos;
        this.detail_barang = detail_barang;
        this.ktp_penerima = ktp_penerima;
        this.cost = cost;
    }

    public NewAlamat() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.alamat);
        dest.writeString(this.nama_penerima);
        dest.writeString(this.no_hp);
        dest.writeString(this.prov);
        dest.writeString(this.kab);
        dest.writeString(this.kec);
        dest.writeString(this.kel);
        dest.writeInt(this.kode_pos);
        dest.writeString(this.ktp_penerima);
        dest.writeInt(this.cost);

        dest.writeTypedList(this.detail_barang);
    }

    protected NewAlamat(Parcel in) {
        this.alamat = in.readString();
        this.nama_penerima = in.readString();
        this.no_hp = in.readString();
        this.prov = in.readString();
        this.kab = in.readString();
        this.kec = in.readString();
        this.kel = in.readString();
        this.kode_pos = in.readInt();
        this.ktp_penerima = in.readString();
        this.cost = in.readInt();

        in.readTypedList(this.detail_barang, NewBarang.CREATOR);
    }

    public static final Creator<NewAlamat> CREATOR = new Creator<NewAlamat>() {
        public NewAlamat createFromParcel(Parcel source) {
            return new NewAlamat(source);
        }
        public NewAlamat[] newArray(int size) {
            return new NewAlamat[size];
        }
    };

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //The following are just setter and getter methods
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama_penerima() {
        return nama_penerima;
    }

    public void setNama_penerima(String nama_penerima) {
        this.nama_penerima = nama_penerima;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getKab() {
        return kab;
    }

    public void setKab(String kab) {
        this.kab = kab;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getKel() {
        return kel;
    }

    public void setKel(String kel) {
        this.kel = kel;
    }

    public int getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(int kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getKtp_penerima() {
        return ktp_penerima;
    }

    public void setKtp_penerima(String ktp_penerima) {
        this.ktp_penerima = ktp_penerima;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<NewBarang> getDetail_barang() {
        return detail_barang;
    }

    public void setDetail_barang(ArrayList<NewBarang> detail_barang) {
        this.detail_barang = detail_barang;
    }

    public static Creator<NewAlamat> getCREATOR() {
        return CREATOR;
    }
}
