package com.app.dportshipper.model.response;

import java.util.List;

public class ResDataAlamt {

    private String token;
    private List<ResDataAlamatPenerima> dataAlamat;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResDataAlamatPenerima> getDataAlamat() {
        return dataAlamat;
    }

    public void setDataAlamat(List<ResDataAlamatPenerima> dataAlamat) {
        this.dataAlamat = dataAlamat;
    }
}
