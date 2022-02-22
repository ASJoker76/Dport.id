package com.app.dportshipper.model.response;

public class ResTrackingArmada {

    private int id_tracking;
    private int id_driver;
    private double lat_sekarang;
    private double long_sekarang;

    public int getId_tracking() {
        return id_tracking;
    }

    public void setId_tracking(int id_tracking) {
        this.id_tracking = id_tracking;
    }

    public int getId_driver() {
        return id_driver;
    }

    public void setId_driver(int id_driver) {
        this.id_driver = id_driver;
    }

    public Double getLat_sekarang() {
        return lat_sekarang;
    }

    public void setLat_sekarang(Double lat_sekarang) {
        this.lat_sekarang = lat_sekarang;
    }

    public Double getLong_sekarang() {
        return long_sekarang;
    }

    public void setLong_sekarang(Double long_sekarang) {
        this.long_sekarang = long_sekarang;
    }
}
