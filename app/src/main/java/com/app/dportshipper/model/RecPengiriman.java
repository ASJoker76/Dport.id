package com.app.dportshipper.model;

public class RecPengiriman {

    private String kota_angkut;
    private String kota_tuju;
    private String j_truck;
    private String tgl_muat;
    private String tgl_drop;

    public RecPengiriman(){}

    public RecPengiriman(String kota_angkut, String kota_tuju, String j_truck, String tgl_muat, String tgl_drop) {
        this.kota_angkut = kota_angkut;
        this.kota_tuju = kota_tuju;
        this.j_truck = j_truck;
        this.tgl_muat = tgl_muat;
        this.tgl_drop = tgl_drop;
    }

    public String getKota_angkut() {
        return kota_angkut;
    }

    public void setKota_angkut(String kota_angkut) {
        this.kota_angkut = kota_angkut;
    }

    public String getKota_tuju() {
        return kota_tuju;
    }

    public void setKota_tuju(String kota_tuju) {
        this.kota_tuju = kota_tuju;
    }

    public String getJ_truck() {
        return j_truck;
    }

    public void setJ_truck(String j_truck) {
        this.j_truck = j_truck;
    }

    public String getTgl_muat() {
        return tgl_muat;
    }

    public void setTgl_muat(String tgl_muat) {
        this.tgl_muat = tgl_muat;
    }

    public String getTgl_drop() {
        return tgl_drop;
    }

    public void setTgl_drop(String tgl_drop) {
        this.tgl_drop = tgl_drop;
    }
}
