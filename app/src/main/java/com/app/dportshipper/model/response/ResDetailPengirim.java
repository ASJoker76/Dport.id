package com.app.dportshipper.model.response;

public class ResDetailPengirim {

    private int id_pickup;
    private int id_order;
    private int id_shipper;
    private String nama_pengirim;
    private String no_hp;
    private String alamat;
    private String prov;
    private String kab;
    private String kec;
    private String kel;
    private int kode_pos;
    private String ktp_pengirim;
    private int status;
    private double lat_pengirim;
    private double long_pengirim;
    private String created_at;
    private String updated_at;
    private int create_by;
    private String update_by;

    public int getId_pickup() {
        return id_pickup;
    }

    public void setId_pickup(int id_pickup) {
        this.id_pickup = id_pickup;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_shipper() {
        return id_shipper;
    }

    public void setId_shipper(int id_shipper) {
        this.id_shipper = id_shipper;
    }

    public String getNama_pengirim() {
        return nama_pengirim;
    }

    public void setNama_pengirim(String nama_pengirim) {
        this.nama_pengirim = nama_pengirim;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
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

    public String getKtp_pengirim() {
        return ktp_pengirim;
    }

    public void setKtp_pengirim(String ktp_pengirim) {
        this.ktp_pengirim = ktp_pengirim;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getLat_pengirim() {
        return lat_pengirim;
    }

    public void setLat_pengirim(double lat_pengirim) {
        this.lat_pengirim = lat_pengirim;
    }

    public double getLong_pengirim() {
        return long_pengirim;
    }

    public void setLong_pengirim(double long_pengirim) {
        this.long_pengirim = long_pengirim;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCreate_by() {
        return create_by;
    }

    public void setCreate_by(int create_by) {
        this.create_by = create_by;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }
}
