package com.example.invoiceamigobusiness.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.invoiceamigobusiness.HomeActivity;
import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.LoginFragmentBinding;
import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    public interface LoginListener {
        public void onLogin(Boolean success,String token);
    }

    public void login(LoginFragmentBinding loginFragmentBinding, LoginListener loginListener) {
        //Show Loading state in UI
        loginFragmentBinding.setLoading(true);
        Single response= Repository.getInstance().executeLogin(new Login(loginFragmentBinding.email.getText().toString() , loginFragmentBinding.password.getText().toString()));
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

