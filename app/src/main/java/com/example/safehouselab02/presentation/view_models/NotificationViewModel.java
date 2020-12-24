package com.example.safehouselab02.presentation.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouselab02.data.repository.RemoteRepository;
import com.example.safehouselab02.domain.entity.Result;
import com.example.safehouselab02.presentation.ui_data.SensorViewData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class NotificationViewModel extends ViewModel {
    private final RemoteRepository repository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<SensorViewData> response = new MutableLiveData<>();

    public NotificationViewModel() {
        repository = new RemoteRepository();
    }

    public void loadSensorById(String id) {

        compositeDisposable.add(
                repository.loadSensors()
                        .map(data -> {
                            for(Result item : data.getResults()) {
                                if(item.getId().getValue().equals(id)) {
                                    return new SensorViewData(
                                            item.getName(),
                                            item.getValue().getValue(),
                                            item.getValue().getTime(),
                                            item.getPicture().getLarge()
                                    );
                                }
                            }
                            return null;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response::setValue,
                                error -> errorMessage.setValue(error.getMessage())
                        )
        );
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<SensorViewData> getResponse() {
        return response;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
