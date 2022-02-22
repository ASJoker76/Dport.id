package com.app.dportshipper.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleMapsImp {
    static final String BASE_URL = "https://maps.googleapis.com/maps/api/";

    private static GoogleMapsApi googleMapsApi;

    private static Retrofit retrofit = null;

    private static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

    public static GoogleMapsApi googleMapsApi() {
        if(googleMapsApi ==null){
            googleMapsApi = getClient().create(GoogleMapsApi.class);
        }
        return googleMapsApi;
    }
}
