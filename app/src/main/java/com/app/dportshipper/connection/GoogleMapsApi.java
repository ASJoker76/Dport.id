package com.app.dportshipper.connection;

import com.app.dportshipper.model.response.ResAddressToLatLng;
import com.app.dportshipper.model.response.ResDirection;
import com.app.dportshipper.model.response.ResResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsApi {

    @GET("directions/json")
    Call<ResDirection> getDirection(@Query("origin") String origin,
                                    @Query("destination") String destination,
                                    @Query("key") String key);

    @GET("geocode/json")
    Call<ResResults> fromLatLngToAddress(@Query("latlng") String latLng,
                                         @Query("key") String key);

    @GET("geocode/json")
    Call<ResAddressToLatLng> addressToLatLng(@Query("address") String address,
                                             @Query("key") String key);
}
