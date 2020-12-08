package com.example.safehouselab02.presentation.validators;

import android.util.Patterns;

public class EmailValidator implements ICredentialValidator {

    @Override
    public boolean isValid(String credential) {
        return Patterns.EMAIL_ADDRESS.matcher(credential).matches();
    }
}
