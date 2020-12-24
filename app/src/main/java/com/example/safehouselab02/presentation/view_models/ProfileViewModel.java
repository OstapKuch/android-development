package com.example.safehouselab02.presentation.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouselab02.presentation.validators.ICredentialValidator;
import com.example.safehouselab02.presentation.validators.PasswordValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;


public class ProfileViewModel extends ViewModel {
    private final ICredentialValidator passwordValidator = new PasswordValidator();

    private final MutableLiveData<Boolean> isPasswordChanged = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public ProfileViewModel() {
    }

    public void updatePassword(String password) {
        boolean isValidPassword = passwordValidator.isValid(password);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(!isValidPassword)
            error.setValue("Password is too short");
        else {
            if(user != null) {
                user.updatePassword(password)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                Timber.d("Password updated");
                                isPasswordChanged.setValue(task.isSuccessful());

                            } else {
                                Timber.d("Error password not updated");
                            }
                        });
            }
        }
    }

    public MutableLiveData<Boolean> getIsPasswordChanged() {
        return isPasswordChanged;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}