package com.example.safehouselab02.data.repository;

import com.example.safehouselab02.data.RandomSensorService;
import com.example.safehouselab02.domain.entity.SensorData;
import com.example.safehouselab02.domain.repository.IRepository;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository implements IRepository {
    private static final String BASE_URL = "http://206.81.22.134:3000/";
    private static final String API_PATH = "results";
    private final RandomSensorService service;

    public RemoteRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoggingClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(RandomSensorService.class);
    }

    public OkHttpClient getLoggingClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Override
    public Single<SensorData> loadSensors() {
        return service.loadSensors(API_PATH);
    }
}
