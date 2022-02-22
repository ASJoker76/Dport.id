package com.app.dportshipper.model.request;

public class ReqInputDataPersonal {

    private String param;
    private String id_shipper;
    private String shipper_ktp;
    private String shipper_npwp;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getId_shipper() {
        return id_shipper;
    }

    public void setId_shipper(String id_shipper) {
        this.id_shipper = id_shipper;
    }

    public String getShipper_ktp() {
        return shipper_ktp;
    }

    public void setShipper_ktp(String shipper_ktp) {
        this.shipper_ktp = shipper_ktp;
    }

    public String getShipper_npwp() {
        return shipper_npwp;
    }

    public void setShipper_npwp(String shipper_npwp) {
        this.shipper_npwp = shipper_npwp;
    }
}
