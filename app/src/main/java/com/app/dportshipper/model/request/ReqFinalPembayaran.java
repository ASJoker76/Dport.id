package com.app.dportshipper.model.request;

public class ReqFinalPembayaran {
    private int id_order;
    private String jumlah_pembayaran;

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public String getJumlah_pembayaran() {
        return jumlah_pembayaran;
    }

    public void setJumlah_pembayaran(String jumlah_pembayaran) {
        this.jumlah_pembayaran = jumlah_pembayaran;
    }
}
