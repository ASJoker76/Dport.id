package com.app.dportshipper.model.response;

public class ResDetailTruck {

    private String type_send;
    private String type_service;
    private String tanggal;
    private ResDataDetailTruck data;

    public String getType_send() {
        return type_send;
    }

    public void setType_send(String type_send) {
        this.type_send = type_send;
    }

    public String getType_service() {
        return type_service;
    }

    public void setType_service(String type_service) {
        this.type_service = type_service;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public ResDataDetailTruck getData() {
        return data;
    }

    public void setData(ResDataDetailTruck data) {
        this.data = data;
    }
}
