package com.example.safehouselab01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private SignInViewModel viewModel;

    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView signUpButton;
    private TextInputLayout inputLayoutEmailAddress;
    private TextInputLayout inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFields();
        registerViewModel();
        onClickSignIn();
        onClickSignUp();
    }

    private void initializeFields() {
        emailAddressEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpText);
        inputLayoutEmailAddress = findViewById(R.id.inputLayoutEmailAddress);
        inputLayoutPassword = findViewById(R.id.inputLayoutPassword);
    }

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        viewModel.getIsLoggedIn().observe(this, isLoggedIn -> {
            showMessage("Signed In");

        });
        viewModel.getError().observe(this, error -> {
            showMessage(error);
            showFieldsErrors();

        });

    }

    private void showMessage(String message) {
        inputLayoutEmailAddress.setErrorEnabled(false);
        inputLayoutPassword.setErrorEnabled(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showFieldsErrors() {
        inputLayoutEmailAddress.setError("Email is not correct");
        inputLayoutPassword.setError("Password is not correct");
    }

    private void onClickSignUp() {
        signUpButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, SignUpActivity.class);
            startActivity(myIntent);
        });
    }

    private void onClickSignIn() {
        signInButton.setOnClickListener(view -> {
            String email = emailAddressEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            viewModel.signIn(email, password);
        });
    }
}