package com.example.safehouselab01;

public class PasswordValidator implements ICredentialValidator {
    private static final int PASSWORD_MIN_LENGTH = 8;

    @Override
    public boolean isValid(String credential) {
        return credential.length() > PASSWORD_MIN_LENGTH;
    }
}
