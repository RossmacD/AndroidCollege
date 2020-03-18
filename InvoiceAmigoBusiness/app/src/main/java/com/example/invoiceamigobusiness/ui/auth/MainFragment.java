package com.example.invoiceamigobusiness.ui.auth;

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
import com.example.invoiceamigobusiness.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {
    private MainViewModel mViewModel;
    public static MainFragment newInstance() {
        return new MainFragment();
    }
    private MainFragmentBinding mainFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate for AndroidX DataBinding
        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        //Create ViewModelProvider
        mViewModel =new ViewModelProvider(this).get(MainViewModel.class);
        mainFragmentBinding.setLoading(false);

        //Login button listener - pass bindings  to view model to handle/read from UI
        mainFragmentBinding.setOnClickListener(view -> {
            mViewModel.login(mainFragmentBinding, getActivity());
        });

        return mainFragmentBinding.getRoot();
    }


}
