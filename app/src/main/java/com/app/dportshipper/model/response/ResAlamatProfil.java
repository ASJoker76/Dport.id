package com.app.dportshipper.model.response;

public class ResAlamatProfil {

    private int id_alamat_shipper;
    private String kd_alamat_shipper;
    private String alamat;
    private String prov;
    private String kab;
    private String kec;
    private String kel;
    private int kode_pos;
    private int flag;


    public int getId_alamat_shipper() {
        return id_alamat_shipper;
    }

    public void setId_alamat_shipper(int id_alamat_shipper) {
        this.id_alamat_shipper = id_alamat_shipper;
    }

    public String getKd_alamat_shipper() {
        return kd_alamat_shipper;
    }

    public void setKd_alamat_shipper(String kd_alamat_shipper) {
        this.kd_alamat_shipper = kd_alamat_shipper;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getKab() {
        return kab;
    }

    public void setKab(String kab) {
        this.kab = kab;
    }

    public String getKec() {
        return kec;
    }

    public void setKec(String kec) {
        this.kec = kec;
    }

    public String getKel() {
        return kel;
    }

    public void setKel(String kel) {
        this.kel = kel;
    }

    public int getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(int kode_pos) {
        this.kode_pos = kode_pos;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
