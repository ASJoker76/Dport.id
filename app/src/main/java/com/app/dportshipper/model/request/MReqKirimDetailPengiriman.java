package com.app.dportshipper.model.request;

import com.app.dportshipper.model.MHead;
import com.app.dportshipper.model.MListDataBarang;
import com.app.dportshipper.model.MPenerima;
import com.app.dportshipper.model.MPengirim;
import com.app.dportshipper.model.NewAlamat;

import java.util.ArrayList;
import java.util.List;

public class MReqKirimDetailPengiriman {

    MHead head;
    MPengirim pengirim;
    List<MPenerima> penerima = new ArrayList<>();
    List<MBarang> detail_barang= new ArrayList<>();

    public MHead getHead() {
        return head;
    }

    public void setHead(MHead head) {
        this.head = head;
    }

    public MPengirim getPengirim() {
        return pengirim;
    }

    public void setPengirim(MPengirim pengirim) {
        this.pengirim = pengirim;
    }

    public List<MPenerima> getPenerima() {
        return penerima;
    }

    public void setPenerima(List<MPenerima> penerima) {
        this.penerima = penerima;
    }

    public List<MBarang> getDetail_barang() {
        return detail_barang;
    }

    public void setDetail_barang(List<MBarang> detail_barang) {
        this.detail_barang = detail_barang;
    }
}
