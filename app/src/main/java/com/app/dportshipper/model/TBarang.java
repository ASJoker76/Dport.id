package com.app.dportshipper.model;

import com.orm.SugarRecord;

public class TBarang extends SugarRecord {

    private String bobot;
    private String jmlBarang;
    private String panjang;
    private String lebar;
    private String tinggi;

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }

    public String getJmlBarang() {
        return jmlBarang;
    }

    public void setJmlBarang(String jmlBarang) {
        this.jmlBarang = jmlBarang;
    }

    public String getPanjang() {
        return panjang;
    }

    public void setPanjang(String panjang) {
        this.panjang = panjang;
    }

    public String getLebar() {
        return lebar;
    }

    public void setLebar(String lebar) {
        this.lebar = lebar;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }
}
