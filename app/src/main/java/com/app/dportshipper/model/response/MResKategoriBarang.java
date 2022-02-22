package com.app.dportshipper.model.response;

public class MResKategoriBarang {
    private int id_ktg_barang;
    private String kode_ktg_barang;
    private String jenis_barang;
    private String keterangan;
    private int status;
    private String created_at;
    private String updated_at;
    private int update_by;

    public int getId_ktg_barang() {
        return id_ktg_barang;
    }

    public void setId_ktg_barang(int id_ktg_barang) {
        this.id_ktg_barang = id_ktg_barang;
    }

    public String getKode_ktg_barang() {
        return kode_ktg_barang;
    }

    public void setKode_ktg_barang(String kode_ktg_barang) {
        this.kode_ktg_barang = kode_ktg_barang;
    }

    public String getJenis_barang() {
        return jenis_barang;
    }

    public void setJenis_barang(String jenis_barang) {
        this.jenis_barang = jenis_barang;
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

    public int getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(int update_by) {
        this.update_by = update_by;
    }
}
