package com.example.invoiceamigobusiness;

import android.util.Log;

import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.auth.AuthApi;
import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public Repository(){
        authApi= RetrofitService.createService(AuthApi.class);
    }


    /**
     * Login - Add authorisation token to future retrofit headers
     * @param email
     * @param password
     */
    public void executeLogin(String email, String password) {
        Login login=new Login(email,password);
        Call<User> call = authApi.login(login);
        call.enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Log.d("RossLog",response.body().getToken());
                            //Add Bearer token to header
                            RetrofitService.addAuthToken("Bearer " + response.body().getToken());
                            executeGetUser();
                        }else{
                            //Server Side error - Should mean invalid credentials but could be a failed connection/other
                            Log.d("RossLog","Login Fail: Invalid Credentials / No Connection" + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        //This means the was a error on the client side
                        Log.d("RossLog","Login Fail: Error!");
                        Log.e("RossLog",t.toString());
                    }
                }
        );
    }

    public void executeGetUser(){

    }
}
