package com.app.dportshipper.model.response;

import java.util.List;

public class ResAddressToLatLng {
    private List<ResultAddressToLng> results;
    private String status;

    public List<ResultAddressToLng> getResults() {
        return results;
    }

    public void setResults(List<ResultAddressToLng> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
