package com.app.dportshipper.model.request;

public class ReqPencarian {

    private String kab_asal;
    private String kab_tujuan;
    private String type_send;
    private int type_service;
    private String tanggal;

    public String getKab_asal() {
        return kab_asal;
    }

    public void setKab_asal(String kab_asal) {
        this.kab_asal = kab_asal;
    }

    public String getKab_tujuan() {
        return kab_tujuan;
    }

    public void setKab_tujuan(String kab_tujuan) {
        this.kab_tujuan = kab_tujuan;
    }

    public String getType_send() {
        return type_send;
    }

    public void setType_send(String type_send) {
        this.type_send = type_send;
    }

    public int getType_service() {
        return type_service;
    }

    public void setType_service(int type_service) {
        this.type_service = type_service;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
