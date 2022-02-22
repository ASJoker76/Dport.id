package com.app.dportshipper.model.response;

import com.app.dportshipper.model.DataPencarian;

import java.util.List;

public class ResPencarian {

    private String type_send;
    private int type_service;
    private String tanggal;
    private List<DataPencarian> data;

    public String getType_send() {
        return type_send;
    }

    public void setType_send(String type_send) {
        this.type_send = type_send;
    }

    public int getType_service() {
        return type_service;
    }

    public void setType_service(int type_service) {
        this.type_service = type_service;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public List<DataPencarian> getData() {
        return data;
    }

    public void setData(List<DataPencarian> data) {
        this.data = data;
    }
}
