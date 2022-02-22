package com.app.dportshipper.model;

public class ScreenPengiriman {

    private String asalKota;
    private String asalTujuan;
    private String jenis_truck;
    private String waktu_kirim;
    private String status;
    private int ivTransporter;

    public ScreenPengiriman(){}

    public ScreenPengiriman(String asalKota, String asalTujuan, String jenis_truck, String waktu_kirim, String status, int ivTransporter) {
        this.asalKota = asalKota;
        this.asalTujuan = asalTujuan;
        this.jenis_truck = jenis_truck;
        this.waktu_kirim = waktu_kirim;
        this.status = status;
        this.ivTransporter = ivTransporter;
    }

    public String getAsalKota() {
        return asalKota;
    }

    public void setAsalKota(String asalKota) {
        this.asalKota = asalKota;
    }

    public String getAsalTujuan() {
        return asalTujuan;
    }

    public void setAsalTujuan(String asalTujuan) {
        this.asalTujuan = asalTujuan;
    }

    public String getJenis_truck() {
        return jenis_truck;
    }

    public void setJenis_truck(String jenis_truck) {
        this.jenis_truck = jenis_truck;
    }

    public String getWaktu_kirim() {
        return waktu_kirim;
    }

    public void setWaktu_kirim(String waktu_kirim) {
        this.waktu_kirim = waktu_kirim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIvTransporter() {
        return ivTransporter;
    }

    public void setIvTransporter(int ivTransporter) {
        this.ivTransporter = ivTransporter;
    }
}
