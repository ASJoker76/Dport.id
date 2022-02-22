package com.app.dportshipper.model;

public class MHead {

    private int id_transporter;
    private int id_shipper;
    private int id_top;
    private int tipe_order;
    private int tipe_schedule;
    private long total_harga;
    private long harga;
    private int id_pajak;
    private String date_from;
    private String date_to;
    private int estimasi;
    private int id_produk_transporter;
    private String keterangan;
    private int layanan_shipper;
    private int layanan_transporter;
    private int ppn_shipper;
    private long nilai_barang;

    public long getNilai_barang() {
        return nilai_barang;
    }

    public void setNilai_barang(long nilai_barang) {
        this.nilai_barang = nilai_barang;
    }

    public int getId_transporter() {
        return id_transporter;
    }

    public void setId_transporter(int id_transporter) {
        this.id_transporter = id_transporter;
    }

    public int getId_shipper() {
        return id_shipper;
    }

    public void setId_shipper(int id_shipper) {
        this.id_shipper = id_shipper;
    }

    public int getId_top() {
        return id_top;
    }

    public void setId_top(int id_top) {
        this.id_top = id_top;
    }

    public int getTipe_order() {
        return tipe_order;
    }

    public void setTipe_order(int tipe_order) {
        this.tipe_order = tipe_order;
    }

    public int getTipe_schedule() {
        return tipe_schedule;
    }
    public void setTipe_schedule(int tipe_schedule) {
        this.tipe_schedule = tipe_schedule;
    }

    public long getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(long total_harga) {
        this.total_harga = total_harga;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public int getId_pajak() {
        return id_pajak;
    }

    public void setId_pajak(int id_pajak) {
        this.id_pajak = id_pajak;
    }

    public String getDate_from() {
        return date_from;
    }

    public void setDate_from(String date_from) {
        this.date_from = date_from;
    }

    public String getDate_to() {
        return date_to;
    }

    public void setDate_to(String date_to) {
        this.date_to = date_to;
    }

    public int getEstimasi() {
        return estimasi;
    }

    public void setEstimasi(int estimasi) {
        this.estimasi = estimasi;
    }

    public int getId_produk_transporter() {
        return id_produk_transporter;
    }

    public void setId_produk_transporter(int id_produk_transporter) {
        this.id_produk_transporter = id_produk_transporter;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getLayanan_shipper() {
        return layanan_shipper;
    }

    public void setLayanan_shipper(int layanan_shipper) {
        this.layanan_shipper = layanan_shipper;
    }

    public int getLayanan_transporter() {
        return layanan_transporter;
    }

    public void setLayanan_transporter(int layanan_transporter) {
        this.layanan_transporter = layanan_transporter;
    }

    public int getPpn_shipper() {
        return ppn_shipper;
    }

    public void setPpn_shipper(int ppn_shipper) {
        this.ppn_shipper = ppn_shipper;
    }
}
