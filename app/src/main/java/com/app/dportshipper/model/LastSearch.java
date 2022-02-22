package com.app.dportshipper.model;

public class LastSearch {

    private String asalKotaLs;
    private String tujuanKotaLs;
    private String jenisTruckLs;
    private String tglMuatLs;

    public LastSearch(){}

    public LastSearch(String asalKotaLs, String tujuanKotaLs, String jenisTruckLs, String tglMuatLs) {
        this.asalKotaLs = asalKotaLs;
        this.tujuanKotaLs = tujuanKotaLs;
        this.jenisTruckLs = jenisTruckLs;
        this.tglMuatLs = tglMuatLs;
    }

    public String getAsalKotaLs() {
        return asalKotaLs;
    }

    public void setAsalKotaLs(String asalKotaLs) {
        this.asalKotaLs = asalKotaLs;
    }

    public String getTujuanKotaLs() {
        return tujuanKotaLs;
    }

    public void setTujuanKotaLs(String tujuanKotaLs) {
        this.tujuanKotaLs = tujuanKotaLs;
    }

    public String getJenisTruckLs() {
        return jenisTruckLs;
    }

    public void setJenisTruckLs(String jenisTruckLs) {
        this.jenisTruckLs = jenisTruckLs;
    }

    public String getTglMuatLs() {
        return tglMuatLs;
    }

    public void setTglMuatLs(String tglMuatLs) {
        this.tglMuatLs = tglMuatLs;
    }
}
