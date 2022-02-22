package com.app.dportshipper.model;

public class Riwayat {

    private String nomorRiwayat;
    private String kotaAsal;
    private String kotaTujuan;
    private String waktu;
    private String reefer;

    public Riwayat(){}

    public Riwayat(String nomorRiwayat, String kotaAsal, String kotaTujuan, String waktu, String reefer) {
        this.nomorRiwayat = nomorRiwayat;
        this.kotaAsal = kotaAsal;
        this.kotaTujuan = kotaTujuan;
        this.waktu = waktu;
        this.reefer = reefer;
    }

    public String getNomorRiwayat() {
        return nomorRiwayat;
    }

    public void setNomorRiwayat(String nomorRiwayat) {
        this.nomorRiwayat = nomorRiwayat;
    }

    public String getKotaAsal() {
        return kotaAsal;
    }

    public void setKotaAsal(String kotaAsal) {
        this.kotaAsal = kotaAsal;
    }

    public String getKotaTujuan() {
        return kotaTujuan;
    }

    public void setKotaTujuan(String kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getReefer() {
        return reefer;
    }

    public void setReefer(String reefer) {
        this.reefer = reefer;
    }
}
