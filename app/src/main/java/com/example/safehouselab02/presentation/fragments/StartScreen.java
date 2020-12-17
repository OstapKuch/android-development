package com.example.safehouselab02.presentation.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.safehouselab02.R;
import com.example.safehouselab02.presentation.OnItemClick;
import com.example.safehouselab02.presentation.sensors_list.SensorsAdapter;
import com.example.safehouselab02.presentation.view_models.SensorsViewModel;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;


public class StartScreen extends Fragment {

    private RecyclerView userList;
    private SensorsAdapter sensorsAdapter = new SensorsAdapter();
    private SwipeRefreshLayout refreshLayout;
    private SensorsViewModel sensorsViewModel;
    private ProgressBar loadingIndicator;
    private OnItemClick listener;


    public StartScreen() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start_screen, container, false);
        initialiseToolbar(rootView);
        initUI(rootView);
        initRecycler(rootView);
        initViewModel();
        return rootView;
    }

    private void initUI(View view) {
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(() -> {
            sensorsViewModel.loadUserList();
        });
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);


    }


    private void initViewModel() {
        sensorsViewModel = new SensorsViewModel();
        sensorsViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            hideLoading();

        });
        sensorsViewModel.getResponse().observe(getViewLifecycleOwner(), response -> {
            Timber.i("Timber we got response");
            sensorsAdapter.setItems(response);
            hideLoading();

        });
        sensorsViewModel.loadUserList();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClick) {
            listener = (OnItemClick) context;
            sensorsAdapter.setClickListener(listener);
        } else {
            throw new IllegalStateException("Activity must implement OnItemClick interface");
        }
    }

    private void hideLoading() {
        refreshLayout.setRefreshing(false);
        loadingIndicator.setVisibility(View.GONE);
    }

    private void initRecycler(View view) {
        userList = view.findViewById(R.id.users_list);
        userList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        userList.setAdapter(sensorsAdapter);

    }

    private void initialiseToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
}
