package com.example.invoiceamigobusiness;

import android.util.Log;

import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.auth.AuthApi;
import com.example.invoiceamigobusiness.network.auth.UserApi;
import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Repository {
    //Turn repository into singleton - prevent multiple builds of retrofit etc....
    private static Repository repository;
    public static Repository getInstance(){
        if (repository == null){
            repository = new Repository();
        }
        return repository;
    }
    //Add an instance of the retrofit service to the singleton
    private AuthApi authApi;
    private UserApi userApi;
    private Repository(){
        rebuild();
    }

    //Rebuild Retrofit
    public void rebuild(){
        authApi= RetrofitService.createService(AuthApi.class);
        userApi= RetrofitService.createService(UserApi.class);
    }

    /**
     * Login - Add authorisation token to future retrofit headers
     * @param login - an instance of the login model - contains username and password
     * @response - Api responds with email, name and Auth token
     */
    public Single executeLogin(Login login) {
        return authApi.login(login).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get user - Uses rxAndroid to retrieve user asynchronously with retrofit
     *
     */
    public Single executeGetUser()  {
       return userApi.getUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
