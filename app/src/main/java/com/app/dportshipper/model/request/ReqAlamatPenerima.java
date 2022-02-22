package com.app.dportshipper.model.request;

import com.app.dportshipper.model.response.ResDataAlamatPenerima;

import java.util.List;

public class ReqAlamatPenerima {

    private String token;
    private List<ResDataAlamatPenerima> getAlamat;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResDataAlamatPenerima> getGetAlamat() {
        return getAlamat;
    }

    public void setGetAlamat(List<ResDataAlamatPenerima> getAlamat) {
        this.getAlamat = getAlamat;
    }
}
