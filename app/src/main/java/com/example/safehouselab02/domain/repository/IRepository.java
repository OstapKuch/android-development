package com.example.safehouselab02.domain.repository;
import com.example.safehouselab02.domain.entity.SensorData;

import io.reactivex.Single;

public interface IRepository {
    Single<SensorData> loadSensors();
}
