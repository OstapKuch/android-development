package com.example.safehouselab02;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpViewModel extends ViewModel {
    private final ICredentialValidator emailValidator = new EmailValidator();
    private final ICredentialValidator passwordValidator = new PasswordValidator();
    private final ICredentialValidator nameValidator = new NameValidator();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSignedUn = new MutableLiveData<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    public void signUp(String name, String email, String password, String passwordRepeated) {
        boolean isValidEmail = emailValidator.isValid(email);
        boolean isValidName = nameValidator.isValid(name);
        boolean isValidPassword = passwordValidator.isValid(password);
        boolean isEqualPasswords = password.equals(passwordRepeated);
        if (!isValidName) {

            error.setValue(EnumErrors.NAME.toString());
        } else if (!isValidEmail) {
            error.setValue(EnumErrors.EMAIL.toString());
        } else if (!isValidPassword) {
            error.setValue(EnumErrors.PASSWORD.toString());
        } else if (!isEqualPasswords) {
            error.setValue(EnumErrors.REPEAT_PASSWORD.toString());
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        isSignedUn.setValue(task.isSuccessful());
                    });
        }
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getIsSignedUp() {
        return isSignedUn;
    }


}
