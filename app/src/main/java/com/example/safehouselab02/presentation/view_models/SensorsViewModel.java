package com.example.safehouselab02.presentation.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouselab02.data.repository.RemoteRepository;
import com.example.safehouselab02.domain.entity.Result;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SensorsViewModel extends ViewModel {
    private RemoteRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<List<SensorViewData>> response = new MutableLiveData<>();

    public SensorsViewModel() {
        repository = new RemoteRepository();
    }

    public void loadUserList() {

        compositeDisposable.add(
                repository.loadSensors()
                        .map(data -> {
                            List<SensorViewData> result = new ArrayList<>();
                            for (Result item : data.getResults()) {
                                SensorViewData sensorViewData = new SensorViewData(item.getName(),
                                        item.getValue().getValue(), item.getValue().getTime(), item.getPicture().getLarge());
                                result.add(sensorViewData);
                            }
                            Collections.shuffle(result, new Random());
                            return result;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> response.setValue(data),
                                error -> Timber.i("Timber %s", error.getMessage())
                        )
        );
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<List<SensorViewData>> getResponse() {
        return response;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
