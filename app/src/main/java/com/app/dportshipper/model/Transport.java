package com.app.dportshipper.model;

public class Transport {

    private String nama_transport;
    private String rating_transport;
    private int iv_transport;

    public Transport(){}

    public Transport(String nama_transport, String rating_transport, int iv_transport) {
        this.nama_transport = nama_transport;
        this.rating_transport = rating_transport;
        this.iv_transport = iv_transport;
    }

    public String getNama_transport() {
        return nama_transport;
    }

    public void setNama_transport(String nama_transport) {
        this.nama_transport = nama_transport;
    }

    public String getRating_transport() {
        return rating_transport;
    }

    public void setRating_transport(String rating_transport) {
        this.rating_transport = rating_transport;
    }

    public int getIv_transport() {
        return iv_transport;
    }

    public void setIv_transport(int iv_transport) {
        this.iv_transport = iv_transport;
    }
}
