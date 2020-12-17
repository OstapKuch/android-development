package com.example.safehouselab02.presentation.view_models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouselab02.presentation.validators.ICredentialValidator;
import com.example.safehouselab02.presentation.validators.PasswordValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import timber.log.Timber;

import static android.content.ContentValues.TAG;

public class ProfileViewModel extends ViewModel {
    private final ICredentialValidator passwordValidator = new PasswordValidator();

    private MutableLiveData<Boolean> isPasswordChanged = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public ProfileViewModel() {
    }

    public void updatePassword(String password) {
        boolean isValidPassword = passwordValidator.isValid(password);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Timber.e(user.getEmail() + "--------------------------");
        if (!isValidPassword)
            error.setValue("Password is too short");
        else {
            if (user != null) {
                user.updatePassword(password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Password updated");
                                isPasswordChanged.setValue(task.isSuccessful());

                            } else {
                                Log.d(TAG, "Error password not updated");
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