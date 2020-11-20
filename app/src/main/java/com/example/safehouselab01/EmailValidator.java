package com.example.safehouselab01;

import android.util.Patterns;

public class EmailValidator implements ICredentialValidator {

    @Override
    public boolean isValid(String credential) {
        return Patterns.EMAIL_ADDRESS.matcher(credential).matches();
    }
}
