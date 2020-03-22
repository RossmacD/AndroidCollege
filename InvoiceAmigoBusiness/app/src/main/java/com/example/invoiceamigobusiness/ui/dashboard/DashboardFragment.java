package com.example.invoiceamigobusiness.ui.dashboard;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invoiceamigobusiness.R;
import com.example.invoiceamigobusiness.databinding.DashboardFragmentBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel mViewModel;
    private DashboardFragmentBinding dashboardFragmentBinding;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Inflate for AndroidX DataBinding
        dashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
        dashboardFragmentBinding.setLoading(true);
        //Create ViewModelProvider
        mViewModel =new ViewModelProvider(this).get(DashboardViewModel.class);
        mViewModel.fillDash(dashboardFragmentBinding);

        return dashboardFragmentBinding.getRoot();
    }

}
