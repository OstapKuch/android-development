package com.example.safehouselab02.data;

import com.example.safehouselab02.domain.entity.SensorData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface RandomSensorService {

    @GET("{results}/")
    Single<SensorData> loadSensors(@Path("results") String results);
}
