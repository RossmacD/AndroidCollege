package com.example.invoiceamigobusiness.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.MainFragmentBinding;


public class MainViewModel extends ViewModel {
    public void login(MainFragmentBinding mainFragmentBinding) {
        mainFragmentBinding.setLoading(true);
        Repository.getInstance().executeLogin(mainFragmentBinding.email.getText().toString() , mainFragmentBinding.password.getText().toString());
    }

}
