package com.app.dportshipper.model.request;

public class ReqRegister {

    private String nama_perusahaan;
    private String no_phone;
    private String email;
    private String password;

    public String getNama_perusahaan() {
        return nama_perusahaan;
    }

    public void setNama_perusahaan(String nama_perusahaan) {
        this.nama_perusahaan = nama_perusahaan;
    }

    public String getNo_phone() {
        return no_phone;
    }

    public void setNo_phone(String no_phone) {
        this.no_phone = no_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
