package com.app.dportshipper.model.response;

public class ResDataNotif {

    private int id;
    private int order_id;
    private int status_id;
    private String no_order;
    private String nama_status;
    private int flag_read;
    private String nama_shipper;
    private String nama_transporter;
    private String tgl;
    private String nama_route;
    private String kab_asal;
    private String kab_tujuan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getNo_order() {
        return no_order;
    }

    public void setNo_order(String no_order) {
        this.no_order = no_order;
    }

    public String getNama_status() {
        return nama_status;
    }

    public void setNama_status(String nama_status) {
        this.nama_status = nama_status;
    }

    public int getFlag_read() {
        return flag_read;
    }

    public void setFlag_read(int flag_read) {
        this.flag_read = flag_read;
    }

    public String getNama_shipper() {
        return nama_shipper;
    }

    public void setNama_shipper(String nama_shipper) {
        this.nama_shipper = nama_shipper;
    }

    public String getNama_transporter() {
        return nama_transporter;
    }

    public void setNama_transporter(String nama_transporter) {
        this.nama_transporter = nama_transporter;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getNama_route() {
        return nama_route;
    }

    public void setNama_route(String nama_route) {
        this.nama_route = nama_route;
    }

    public String getKab_asal() {
        return kab_asal;
    }

    public void setKab_asal(String kab_asal) {
        this.kab_asal = kab_asal;
    }

    public String getKab_tujuan() {
        return kab_tujuan;
    }

    public void setKab_tujuan(String kab_tujuan) {
        this.kab_tujuan = kab_tujuan;
    }
}
