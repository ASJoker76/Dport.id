package com.app.dportshipper.model.response;

public class ResDataAlamatPenerima {

    private int id_penerima;
    private int id_order;
    private int  id_shipper;
    private String nama_penerima;
    private String no_hp;
    private String alamat;
    private String prov;
    private String kab;
    private String kec;
    private String kel;
    private int kode_pos;
    private int status;
    private String ktp_penerima;
    private String lat_penerima;
    private String long_penerima;
    private String created_at;
    private String updated_at;
    private int create_by;
    private int update_by;

    public int getId_penerima() {
        return id_penerima;
    }

    public void setId_penerima(int id_penerima) {
        this.id_penerima = id_penerima;
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

    public String getNama_penerima() {
        return nama_penerima;
    }

    public void setNama_penerima(String nama_penerima) {
        this.nama_penerima = nama_penerima;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKtp_penerima() {
        return ktp_penerima;
    }

    public void setKtp_penerima(String ktp_penerima) {
        this.ktp_penerima = ktp_penerima;
    }

    public String getLat_penerima() {
        return lat_penerima;
    }

    public void setLat_penerima(String lat_penerima) {
        this.lat_penerima = lat_penerima;
    }

    public String getLong_penerima() {
        return long_penerima;
    }

    public void setLong_penerima(String long_penerima) {
        this.long_penerima = long_penerima;
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

    public int getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(int update_by) {
        this.update_by = update_by;
    }
}
