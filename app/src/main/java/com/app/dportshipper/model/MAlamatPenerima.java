package com.app.dportshipper.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MAlamatPenerima implements Parcelable {

    private String alamat;
    private String namaPenerima;
    private String kontakTlp;
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    private String kelurahan;
    private int kodepos;

    public MAlamatPenerima(String alamat, String namaPenerima, String kontakTlp, String provinsi, String kabupaten, String kecamatan, String kelurahan, int kodepos) {
        this.alamat = alamat;
        this.namaPenerima = namaPenerima;
        this.kontakTlp = kontakTlp;
        this.provinsi = provinsi;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.kelurahan = kelurahan;
        this.kodepos = kodepos;
    }

    protected MAlamatPenerima(Parcel in) {
        alamat = in.readString();
        namaPenerima = in.readString();
        kontakTlp = in.readString();
        provinsi = in.readString();
        kabupaten = in.readString();
        kecamatan = in.readString();
        kelurahan = in.readString();
        kodepos = in.readInt();
    }

    public MAlamatPenerima() {

    }


    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
    }

    public String getKontakTlp() {
        return kontakTlp;
    }

    public void setKontakTlp(String kontakTlp) {
        this.kontakTlp = kontakTlp;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public int getKodepos() {
        return kodepos;
    }

    public void setKodepos(int kodepos) {
        this.kodepos = kodepos;
    }

    public static final Creator<MAlamatPenerima> CREATOR = new Creator<MAlamatPenerima>() {
        @Override
        public MAlamatPenerima createFromParcel(Parcel in) {
            return new MAlamatPenerima(in);
        }

        @Override
        public MAlamatPenerima[] newArray(int size) {
            return new MAlamatPenerima[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alamat);
        dest.writeString(namaPenerima);
        dest.writeString(kontakTlp);
        dest.writeString(provinsi);
        dest.writeString(kabupaten);
        dest.writeString(kecamatan);
        dest.writeString(kelurahan);
        dest.writeInt(kodepos);
    }
}
