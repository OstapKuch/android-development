package com.example.safehouselab02;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;


import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;


public class SignUpFragment extends Fragment {

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

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        onClickToolbar(rootView);
        initializeFields(rootView);
        registerViewModel();
        buttonOnclick();
        return rootView;
    }

    private void initializeFields(View rootView) {
        nameEditText = rootView.findViewById(R.id.editTextTextPersonName);
        emailEditText = rootView.findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = rootView.findViewById(R.id.editTextTextPassword);
        passwordRepeatedEditText = rootView.findViewById(R.id.editTextTextPasswordRepeat);
        signUpButton = rootView.findViewById(R.id.buttonSignUp);
        inputLayoutPersonName = rootView.findViewById(R.id.inputLayoutPersonName);
        inputLayoutEmailAddress = rootView.findViewById(R.id.inputLayoutEmailAddress);
        inputLayoutPassword = rootView.findViewById(R.id.inputLayoutPassword);
        inputLayoutPasswordRepeat = rootView.findViewById(R.id.inputLayoutPasswordRepeat);
    }


    private void onClickToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> {
            moveToSignInFragment();
        });
    }

    private void buttonOnclick() {
        signUpButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordRepeated = passwordRepeatedEditText.getText().toString();
            viewModel.signUp(name, email, password, passwordRepeated);
        });
    }

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.getIsSignedUp().observe(getViewLifecycleOwner(), isSignedUn -> {
            Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
            moveToSignInFragment();
        });
        viewModel.getError().observe(getViewLifecycleOwner(), this::showErrorOnFields);
    }

    private void moveToSignInFragment() {
        FragmentTransaction fragmentTransaction = getActivity()
                .getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new SignInFragment());
        fragmentTransaction.commit();
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