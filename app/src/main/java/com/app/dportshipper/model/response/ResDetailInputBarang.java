package com.app.dportshipper.model.response;

public class ResDetailInputBarang {

    private int type_send;
    private int type_service;
    private String tanggal;
    private String tanggal_to;
    private String biaya_layanan_shipper;
    private String biaya_layanan_transporter;
    private String biaya_ppn;
    private long total_harga;
    private String tipe_layanan;
    private ResDataDetailInputBarang detail;
    private ResDataAlamatInputBarang alamat;

    public int getType_send() {
        return type_send;
    }

    public void setType_send(int type_send) {
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

    public String getTanggal_to() {
        return tanggal_to;
    }

    public void setTanggal_to(String tanggal_to) {
        this.tanggal_to = tanggal_to;
    }

    public String getBiaya_layanan_shipper() {
        return biaya_layanan_shipper;
    }

    public void setBiaya_layanan_shipper(String biaya_layanan_shipper) {
        this.biaya_layanan_shipper = biaya_layanan_shipper;
    }

    public String getBiaya_layanan_transporter() {
        return biaya_layanan_transporter;
    }

    public void setBiaya_layanan_transporter(String biaya_layanan_transporter) {
        this.biaya_layanan_transporter = biaya_layanan_transporter;
    }

    public String getBiaya_ppn() {
        return biaya_ppn;
    }

    public void setBiaya_ppn(String biaya_ppn) {
        this.biaya_ppn = biaya_ppn;
    }

    public long getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public String getTipe_layanan() {
        return tipe_layanan;
    }

    public void setTipe_layanan(String tipe_layanan) {
        this.tipe_layanan = tipe_layanan;
    }

    public ResDataDetailInputBarang getDetail() {
        return detail;
    }

    public void setDetail(ResDataDetailInputBarang detail) {
        this.detail = detail;
    }

    public ResDataAlamatInputBarang getAlamat() {
        return alamat;
    }

    public void setAlamat(ResDataAlamatInputBarang alamat) {
        this.alamat = alamat;
    }
}
