package com.example.safehouselab02.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.view_models.ProfileViewModel;

import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {

    private TextView emailEditText;
    private Button saveButton;
    private ProfileViewModel viewModel;
    private OnButtonClickListener onClickedListener;

    public ProfileFragment() {
    }

    public interface OnButtonClickListener {
        void onSaveButtonClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        saveButton = rootView.findViewById(R.id.saveButton);
        emailEditText = rootView.findViewById(R.id.newEmail);
        initialiseToolbar(rootView);
        saveButton.setOnClickListener(onSaveClickListener);
        registerViewModel();
        return rootView;

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

    private void initialiseToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void registerViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.getIsPasswordChanged().observe(getViewLifecycleOwner(), isPasswordChanged -> {
            if(isPasswordChanged) {
                onClickedListener.onSaveButtonClicked();
                showMessage("password changed");
            }
        });
        viewModel.getError().observe(getViewLifecycleOwner(), this::showMessage);


    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private final View.OnClickListener onSaveClickListener = view -> {
        String password = emailEditText.getText().toString();
        viewModel.updatePassword(password);
    };

}