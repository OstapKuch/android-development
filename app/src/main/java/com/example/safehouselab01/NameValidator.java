package com.example.safehouselab01;

import android.text.TextUtils;

public class NameValidator implements ICredentialValidator {

    @Override
    public boolean isValid(String credential) {
        return !TextUtils.isEmpty(credential);
    }
}
