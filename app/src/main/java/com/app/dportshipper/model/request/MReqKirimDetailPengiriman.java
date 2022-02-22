package com.app.dportshipper.model.request;

import com.app.dportshipper.model.MHead;
import com.app.dportshipper.model.MPengirim;
import com.app.dportshipper.model.NewAlamat;

import java.util.ArrayList;
import java.util.List;

public class MReqKirimDetailPengiriman {

    MHead head;
    MPengirim pengirim;
    List<NewAlamat> penerima = new ArrayList<>();
    //List<MListDataBarang> detail_barang= new ArrayList<>();

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

    public List<NewAlamat> getPenerima() {
        return penerima;
    }

    public void setPenerima(List<NewAlamat> penerima) {
        this.penerima = penerima;
    }
}
