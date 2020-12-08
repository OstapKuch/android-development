package com.example.safehouselab02.presentation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.view_models.SignInViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;


public class SignInFragment extends Fragment {

    private SignInViewModel viewModel;

    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private TextView signUpButton;
    private TextInputLayout inputLayoutEmailAddress;
    private TextInputLayout inputLayoutPassword;
    private OnButtonClickListener onClickedListener;

    public SignInFragment() {
    }

    public interface OnButtonClickListener {
        void onSignInButtonClicked();

        void onSignUpTextClicked();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        try {
            onClickedListener = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSignUpNavClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClickedListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initializeFields(rootView);
        signInButton.setOnClickListener(onSignInClickListener);
        signUpButton.setOnClickListener(onSignUpClickListener);
        registerViewModel();
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
                onClickedListener.onSignInButtonClicked();

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


    private final View.OnClickListener onSignUpClickListener = view -> {
        onClickedListener.onSignUpTextClicked();
    };


    private final View.OnClickListener onSignInClickListener = view -> {
        String email = emailAddressEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        viewModel.signIn(email, password);
    };
}