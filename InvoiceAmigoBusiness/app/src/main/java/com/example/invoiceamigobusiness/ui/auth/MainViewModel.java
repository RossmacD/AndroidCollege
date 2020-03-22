package com.example.invoiceamigobusiness.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.invoiceamigobusiness.HomeActivity;
import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.MainFragmentBinding;
import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    public interface LoginListener {
        public void onLogin(Boolean success,String token);
    }

    public void login(MainFragmentBinding mainFragmentBinding, LoginListener loginListener) {
        //Show Loading state in UI
        mainFragmentBinding.setLoading(true);
//        mainFragmentBinding.email.getText().toString() , mainFragmentBinding.password.getText().toString())
        Single response= Repository.getInstance().executeLogin(new Login("ultan.on98@gmail.com","secret"));
        response.subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {


                        //Add Bearer token to header
                        RetrofitService.addAuthToken("Bearer " + userResponse.body().getToken());
                        //Rebuild to update intercepters and callback factories
                        Repository.getInstance().rebuild();
                        loginListener.onLogin(true,userResponse.body().getToken());
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginListener.onLogin(false,"");
                    }
                }
        );
    }

}

