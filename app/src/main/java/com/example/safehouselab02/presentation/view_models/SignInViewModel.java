package com.example.safehouselab02.presentation.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.safehouselab02.presentation.validators.EmailValidator;
import com.example.safehouselab02.presentation.validators.ICredentialValidator;
import com.example.safehouselab02.presentation.validators.PasswordValidator;
import com.google.firebase.auth.FirebaseAuth;

public class SignInViewModel extends ViewModel {
    private final ICredentialValidator emailValidator = new EmailValidator();
    private final ICredentialValidator passwordValidator = new PasswordValidator();

    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public SignInViewModel() {
    }

    public void signIn(String email, String password) {
        boolean isValidEmail = emailValidator.isValid(email);
        boolean isValidPassword = passwordValidator.isValid(password);

        if(!isValidEmail || !isValidPassword)
            error.setValue("Email or password is incorrect");
        else {

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        isLoggedIn.setValue(task.isSuccessful());
                    });
        }
    }

    public MutableLiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
