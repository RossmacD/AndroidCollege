package com.example.invoiceamigobusiness.ui.main;

import androidx.lifecycle.ViewModel;

import com.example.invoiceamigobusiness.Repository;


public class MainViewModel extends ViewModel {
    public void login(){
        Repository.getInstance().executeLogin("ultan.on98@gmail.com","secret");
    }
}
