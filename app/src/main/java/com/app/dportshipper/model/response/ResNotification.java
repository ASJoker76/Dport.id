package com.app.dportshipper.model.response;

import java.util.List;

public class ResNotification {

    private int total;
    private List<ResDataNotif> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResDataNotif> getData() {
        return data;
    }

    public void setData(List<ResDataNotif> data) {
        this.data = data;
    }
}
