package com.app.dportshipper.model;

public class DetailTransporter {

    private String kotaAsalTransporter;
    private String kotaTujuanTransporter;
    private String tglMuatTransporter;
    private String hargaTransporter;
    private String typeTruckTransporter;
    private int imgTransporter;
    private String namaTransporter;

    public DetailTransporter(){}

    public DetailTransporter(String kotaAsalTransporter, String kotaTujuanTransporter, String tglMuatTransporter, String hargaTransporter, String typeTruckTransporter, int imgTransporter, String namaTransporter) {
        this.kotaAsalTransporter = kotaAsalTransporter;
        this.kotaTujuanTransporter = kotaTujuanTransporter;
        this.tglMuatTransporter = tglMuatTransporter;
        this.hargaTransporter = hargaTransporter;
        this.typeTruckTransporter = typeTruckTransporter;
        this.imgTransporter = imgTransporter;
        this.namaTransporter = namaTransporter;
    }

    public String getKotaAsalTransporter() {
        return kotaAsalTransporter;
    }

    public void setKotaAsalTransporter(String kotaAsalTransporter) {
        this.kotaAsalTransporter = kotaAsalTransporter;
    }

    public String getKotaTujuanTransporter() {
        return kotaTujuanTransporter;
    }

    public void setKotaTujuanTransporter(String kotaTujuanTransporter) {
        this.kotaTujuanTransporter = kotaTujuanTransporter;
    }

    public String getTglMuatTransporter() {
        return tglMuatTransporter;
    }

    public void setTglMuatTransporter(String tglMuatTransporter) {
        this.tglMuatTransporter = tglMuatTransporter;
    }

    public String getHargaTransporter() {
        return hargaTransporter;
    }

    public void setHargaTransporter(String hargaTransporter) {
        this.hargaTransporter = hargaTransporter;
    }

    public String getTypeTruckTransporter() {
        return typeTruckTransporter;
    }

    public void setTypeTruckTransporter(String typeTruckTransporter) {
        this.typeTruckTransporter = typeTruckTransporter;
    }

    public int getImgTransporter() {
        return imgTransporter;
    }

    public void setImgTransporter(int imgTransporter) {
        this.imgTransporter = imgTransporter;
    }

    public String getNamaTransporter() {
        return namaTransporter;
    }

    public void setNamaTransporter(String namaTransporter) {
        this.namaTransporter = namaTransporter;
    }
}
