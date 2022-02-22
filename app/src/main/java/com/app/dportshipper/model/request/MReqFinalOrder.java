package com.app.dportshipper.model.request;

public class MReqFinalOrder {
    private int id_order;
    private String foto_bukti_filepath;

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public String getFoto_bukti_filepath() {
        return foto_bukti_filepath;
    }

    public void setFoto_bukti_filepath(String foto_bukti_filepath) {
        this.foto_bukti_filepath = foto_bukti_filepath;
    }
}
