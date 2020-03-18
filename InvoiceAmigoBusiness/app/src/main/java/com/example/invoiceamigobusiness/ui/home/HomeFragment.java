package com.example.invoiceamigobusiness.ui.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invoiceamigobusiness.R;
import com.example.invoiceamigobusiness.databinding.HomeFragmentBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private HomeFragmentBinding homeFragmentBinding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Inflate for AndroidX DataBinding
        homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        //Create ViewModelProvider
        mViewModel =new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.fillDash(homeFragmentBinding);

        return homeFragmentBinding.getRoot();
    }

}
