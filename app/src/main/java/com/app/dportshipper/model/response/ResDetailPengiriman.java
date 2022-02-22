package com.app.dportshipper.model.response;

import java.util.List;

public class ResDetailPengiriman {

    private ResDetailPengirimanIsi detail;
    private ResDetailPengirim pengirim;
    private List<ResDetailPenerima> penerima;
    private List<ResLogStatus> log_status;
    private List<ResDetailPengirimanBarang> detail_barang;
    private List<ResDataFotoMuatBarang> foto_muat_barang;
    private List<ResDataFotoBongkarBarang> foto_bongkar_barang;

    public List<ResDetailPengirimanBarang> getDetail_barang() {
        return detail_barang;
    }

    public void setDetail_barang(List<ResDetailPengirimanBarang> detail_barang) {
        this.detail_barang = detail_barang;
    }

    public List<ResLogStatus> getLog_status() {
        return log_status;
    }

    public void setLog_status(List<ResLogStatus> log_status) {
        this.log_status = log_status;
    }

    public ResDetailPengirimanIsi getDetail() {
        return detail;
    }

    public void setDetail(ResDetailPengirimanIsi detail) {
        this.detail = detail;
    }

    public ResDetailPengirim getPengirim() {
        return pengirim;
    }

    public void setPengirim(ResDetailPengirim pengirim) {
        this.pengirim = pengirim;
    }

    public List<ResDetailPenerima> getPenerima() {
        return penerima;
    }

    public void setPenerima(List<ResDetailPenerima> penerima) {
        this.penerima = penerima;
    }

//    public List<ResDetailPengirimanBarang> getDetail_barang() {
//        return detail_barang;
//    }
//
//    public void setDetail_barang(List<ResDetailPengirimanBarang> detail_barang) {
//        this.detail_barang = detail_barang;
//    }

    public List<ResDataFotoMuatBarang> getFoto_muat_barang() {
        return foto_muat_barang;
    }

    public void setFoto_muat_barang(List<ResDataFotoMuatBarang> foto_muat_barang) {
        this.foto_muat_barang = foto_muat_barang;
    }

    public List<ResDataFotoBongkarBarang> getFoto_bongkar_barang() {
        return foto_bongkar_barang;
    }

    public void setFoto_bongkar_barang(List<ResDataFotoBongkarBarang> foto_bongkar_barang) {
        this.foto_bongkar_barang = foto_bongkar_barang;
    }
}
