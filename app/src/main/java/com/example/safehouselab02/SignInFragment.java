package com.example.safehouselab02;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;


public class SignInFragment extends Fragment {

    private SignInViewModel viewModel;

    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView signUpButton;
    private TextInputLayout inputLayoutEmailAddress;
    private TextInputLayout inputLayoutPassword;

    public SignInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initializeFields(rootView);
        registerViewModel();
        onClickSignIn();
        onClickSignUp();
        return rootView;
    }

    private void initializeFields(View rootView) {
        emailAddressEditText = rootView.findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = rootView.findViewById(R.id.editTextTextPassword);
        signInButton = rootView.findViewById(R.id.signInButton);
        signUpButton = rootView.findViewById(R.id.signUpText);
        inputLayoutEmailAddress = rootView.findViewById(R.id.inputLayoutEmailAddress);
        inputLayoutPassword = rootView.findViewById(R.id.inputLayoutPassword);
    }

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        viewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (isLoggedIn) {
                showMessage("Signed In");
                Intent myIntent = new Intent(getActivity(), StartScreen.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            } else {
                showMessage("Failed");
            }
        });
        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            showMessage(error);
            showFieldsErrors();

        });

    }

    private void showMessage(String message) {
        inputLayoutEmailAddress.setErrorEnabled(false);
        inputLayoutPassword.setErrorEnabled(false);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void showFieldsErrors() {
        inputLayoutEmailAddress.setError("Email is not correct");
        inputLayoutPassword.setError("Password is not correct");
    }

    private void onClickSignUp() {
        signUpButton.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new SignUpFragment());
            fragmentTransaction.commit();
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