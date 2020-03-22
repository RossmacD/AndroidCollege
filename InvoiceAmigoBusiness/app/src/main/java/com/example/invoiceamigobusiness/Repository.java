package com.example.invoiceamigobusiness;

import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.api.AuthApi;
import com.example.invoiceamigobusiness.network.api.DashApi;
import com.example.invoiceamigobusiness.network.api.InvoiceApi;
import com.example.invoiceamigobusiness.network.api.UserApi;
import com.example.invoiceamigobusiness.network.model.Login;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private DashApi dashApi;
    private InvoiceApi invoiceApi;
    private Repository(){
        rebuild();
    }

    //Rebuild Retrofit
    public void rebuild(){
        authApi= RetrofitService.createService(AuthApi.class);
        userApi= RetrofitService.createService(UserApi.class);
        dashApi=RetrofitService.createService(DashApi.class);
        invoiceApi=RetrofitService.createService(InvoiceApi.class);
    }

    /**
     * Login - Pass login info and recieve a login token, runs on new thread but can be subscribed through on main thread
     * @param login - an instance of the login model - contains username and password
     * @return Single - A one time observable object that will be fed the token
     */
    public Single executeLogin(Login login) {
        return authApi.login(login).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get User from login token, runs on new thread but can be subscribed through on main thread
     * @return Single - A one time observable object that will be fed the user info
     */
    public Single executeGetUser()  {
       return userApi.getUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get Dash information for user based on login token, runs on new thread but can be subscribed through on main thread
     * @return Single - A one time observable object that will be fed the dash info
     */
    public Single executeGetDash(){
        return dashApi.getDash().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get Invoices for user based on login token, runs on new thread but can be subscribed through on main thread
     * @return Single - A one time observable object that will be fed the invoices
     */
    public Single executeGetInvoices(){
        return invoiceApi.getInvoices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
