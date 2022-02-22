package com.app.dportshipper.model.response;

public class MResTOP {

    private int id_top;
    private String kode_perjanjian;
    private String nama_perjanjian;
    private int jml_hari;
    private String keterangan;
    private int status;
    private String created_at;
    private String updated_at;
    private int create_by;
    private int update_by;

    public int getId_top() {
        return id_top;
    }

    public void setId_top(int id_top) {
        this.id_top = id_top;
    }

    public String getKode_perjanjian() {
        return kode_perjanjian;
    }

    public void setKode_perjanjian(String kode_perjanjian) {
        this.kode_perjanjian = kode_perjanjian;
    }

    public String getNama_perjanjian() {
        return nama_perjanjian;
    }

    public void setNama_perjanjian(String nama_perjanjian) {
        this.nama_perjanjian = nama_perjanjian;
    }

    public int getJml_hari() {
        return jml_hari;
    }

    public void setJml_hari(int jml_hari) {
        this.jml_hari = jml_hari;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
