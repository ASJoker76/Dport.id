package com.app.dportshipper.model;

public class DalamPengiriman {

    private String kota_asal;
    private String kota_tujuan;
    private String jenis_truck;
    private String tgl_muat;
    private String status_pengiriman;

    public DalamPengiriman(){}

    public DalamPengiriman(String kota_asal, String kota_tujuan, String jenis_truck, String tgl_muat, String status_pengiriman) {
        this.kota_asal = kota_asal;
        this.kota_tujuan = kota_tujuan;
        this.jenis_truck = jenis_truck;
        this.tgl_muat = tgl_muat;
        this.status_pengiriman = status_pengiriman;
    }

    public String getKota_asal() {
        return kota_asal;
    }

    public void setKota_asal(String kota_asal) {
        this.kota_asal = kota_asal;
    }

    public String getKota_tujuan() {
        return kota_tujuan;
    }

    public void setKota_tujuan(String kota_tujuan) {
        this.kota_tujuan = kota_tujuan;
    }

    public String getJenis_truck() {
        return jenis_truck;
    }

    public void setJenis_truck(String jenis_truck) {
        this.jenis_truck = jenis_truck;
    }

    public String getTgl_muat() {
        return tgl_muat;
    }

    public void setTgl_muat(String tgl_muat) {
        this.tgl_muat = tgl_muat;
    }

    public String getStatus_pengiriman() {
        return status_pengiriman;
    }

    public void setStatus_pengiriman(String status_pengiriman) {
        this.status_pengiriman = status_pengiriman;
    }
}
