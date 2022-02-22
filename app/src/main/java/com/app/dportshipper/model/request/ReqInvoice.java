package com.app.dportshipper.model.request;

public class ReqInvoice {
    private long total_harga;
    private int id_order;

    public long getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }
}
