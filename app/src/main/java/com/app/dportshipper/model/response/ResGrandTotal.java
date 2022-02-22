package com.app.dportshipper.model.response;

public class ResGrandTotal {

    private long grand_total;
    private String asuransi;
    private long ppn;
    private long layanan_shipper;
    private long biaya_layanan;
    private long total_nilai_barang;

    public long getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(long grand_total) {
        this.grand_total = grand_total;
    }

    public String getAsuransi() {
        return asuransi;
    }

    public void setAsuransi(String asuransi) {
        this.asuransi = asuransi;
    }

    public long getPpn() {
        return ppn;
    }

    public void setPpn(long ppn) {
        this.ppn = ppn;
    }

    public long getLayanan_shipper() {
        return layanan_shipper;
    }

    public void setLayanan_shipper(long layanan_shipper) {
        this.layanan_shipper = layanan_shipper;
    }

    public long getBiaya_layanan() {
        return biaya_layanan;
    }

    public void setBiaya_layanan(long biaya_layanan) {
        this.biaya_layanan = biaya_layanan;
    }

    public long getTotal_nilai_barang() {
        return total_nilai_barang;
    }

    public void setTotal_nilai_barang(long total_nilai_barang) {
        this.total_nilai_barang = total_nilai_barang;
    }
}
