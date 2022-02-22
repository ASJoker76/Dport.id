package com.app.dportshipper.model.request;

public class ReqValidateCode {

    private String email;
    private String forget_code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForget_code() {
        return forget_code;
    }

    public void setForget_code(String forget_code) {
        this.forget_code = forget_code;
    }
}
