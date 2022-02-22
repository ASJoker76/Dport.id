package com.app.dportshipper.model.response;

public class ResGetProfil {

    private int id_shipper;
    private String nama_perusahaan;
    private String email_perusahaan;
    private String notelp_perusahaan;
    private String profile_pic_path;

    public int getId_shipper() {
        return id_shipper;
    }

    public void setId_shipper(int id_shipper) {
        this.id_shipper = id_shipper;
    }

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public String getEmail_perusahaan() {
        return email_perusahaan;
    }

    public void setEmail_perusahaan(String email_perusahaan) {
        this.email_perusahaan = email_perusahaan;
    }

    public String getNotelp_perusahaan() {
        return notelp_perusahaan;
    }

    public void setNotelp_perusahaan(String notelp_perusahaan) {
        this.notelp_perusahaan = notelp_perusahaan;
    }

    public String getProfile_pic_path() {
        return profile_pic_path;
    }

    public void setProfile_pic_path(String profile_pic_path) {
        this.profile_pic_path = profile_pic_path;
    }
}
