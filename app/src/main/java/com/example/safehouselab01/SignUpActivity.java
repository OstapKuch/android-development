package com.example.safehouselab01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel viewModel;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordRepeatedEditText;
    private Button signUpButton;
    private TextInputLayout inputLayoutPersonName;
    private TextInputLayout inputLayoutEmailAddress;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutPasswordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        onClickToolbar();
        initializeFields();
        registerViewModel();
        buttonOnclick();

    }

    private void initializeFields() {
        nameEditText = findViewById(R.id.editTextTextPersonName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        passwordRepeatedEditText = findViewById(R.id.editTextTextPasswordRepeat);
        signUpButton = findViewById(R.id.buttonSignUp);
        inputLayoutPersonName = findViewById(R.id.inputLayoutPersonName);
        inputLayoutEmailAddress = findViewById(R.id.inputLayoutEmailAddress);
        inputLayoutPassword = findViewById(R.id.inputLayoutPassword);
        inputLayoutPasswordRepeat = findViewById(R.id.inputLayoutPasswordRepeat);
    }


    private void onClickToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void buttonOnclick() {
        signUpButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordRepeated = passwordRepeatedEditText.getText().toString();
            viewModel.signUn(name, email, password, passwordRepeated);
        });
    }

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.getIsSignedUp().observe(this, isSignedUn -> {
            showMessage("Registered");
        });
        viewModel.getError().observe(this, this::showErrorOnFields);

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorOnFields(String error) {
        inputLayoutPersonName.setErrorEnabled(false);
        inputLayoutEmailAddress.setErrorEnabled(false);
        inputLayoutPassword.setErrorEnabled(false);
        inputLayoutPasswordRepeat.setErrorEnabled(false);
        if (error.equals(EnumErrors.NAME.toString())) {
            inputLayoutPersonName.setError(EnumErrors.NAME.getError());
        } else if (error.equals(EnumErrors.EMAIL.toString())) {
            inputLayoutEmailAddress.setError(EnumErrors.EMAIL.getError());
        } else if (error.equals(EnumErrors.PASSWORD.toString())) {
            inputLayoutPassword.setError(EnumErrors.PASSWORD.getError());
        } else if (error.equals(EnumErrors.REPEAT_PASSWORD.toString())) {
            inputLayoutPasswordRepeat.setError(EnumErrors.REPEAT_PASSWORD.getError());
        }
    }
}