package com.app.dportshipper.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class ResDataFotoMuatBarang implements Parcelable {
    private int id;
    private String image;
    private String tanggal;
    private String jam;
    private String description;

    protected ResDataFotoMuatBarang(Parcel in) {
        id = in.readInt();
        image = in.readString();
        tanggal = in.readString();
        jam = in.readString();
        description = in.readString();
    }

    public static final Creator<ResDataFotoMuatBarang> CREATOR = new Creator<ResDataFotoMuatBarang>() {
        @Override
        public ResDataFotoMuatBarang createFromParcel(Parcel in) {
            return new ResDataFotoMuatBarang(in);
        }

        @Override
        public ResDataFotoMuatBarang[] newArray(int size) {
            return new ResDataFotoMuatBarang[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(tanggal);
        dest.writeString(jam);
        dest.writeString(description);
    }
}
