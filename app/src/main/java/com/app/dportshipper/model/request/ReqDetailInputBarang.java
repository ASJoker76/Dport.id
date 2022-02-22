package com.app.dportshipper.model.request;

public class ReqDetailInputBarang {

    private String type_send;
    private int type_service;
    private String tanggal;
    private int id_produk_transporter;

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

    public int getId_produk_transporter() {
        return id_produk_transporter;
    }

    public void setId_produk_transporter(int id_produk_transporter) {
        this.id_produk_transporter = id_produk_transporter;
    }
}
