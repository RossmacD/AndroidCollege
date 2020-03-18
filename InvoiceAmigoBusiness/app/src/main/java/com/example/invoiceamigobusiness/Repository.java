package com.example.invoiceamigobusiness;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;

import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.auth.AuthApi;
import com.example.invoiceamigobusiness.network.auth.UserApi;
import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import io.reactivex.Single;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.*;
import io.reactivex.disposables.*;
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
    private UserApi userApi;
    public Repository(){
        rebuild();
    }

    //Rebuild Retrofit
    private void rebuild(){
        authApi= RetrofitService.createService(AuthApi.class);
        userApi= RetrofitService.createService(UserApi.class);
    }

    /**
     * Login - Add authorisation token to future retrofit headers
     * @param email - User Email
     * @param password - User password
     *
     * @response - Api responds with email, name and Auth token
     */
    public void executeLogin(String email, String password) {
        Login login=new Login("ultan.on98@gmail.com","secret");
        Call<User> call = authApi.login(login);
        call.enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Log.d("RossLog",response.body().getToken());
                            //Add Bearer token to header
                            RetrofitService.addAuthToken("Bearer " + response.body().getToken());
                            //Rebuild to update intercepters and callback factories
                            rebuild();
                            //Get user with authentication token
                            executeGetUser();
                        }else{
                            //Server Side error - Should mean invalid credentials but could be a failed connection/other
                            Log.d("RossLog","Login Fail: Invalid Credentials / No Connection" + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        //This means the was a error on the client side
                        Log.d("RossLog","Login Fail: Error!",t);
                    }
                }
        );

    }

    public void executeGetUser()  {
        Log.d("Ross","Adding observable");
        userApi.getUser().subscribeOn(Schedulers.io()).observeOn(Schedulers.computation()).subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        Log.d("Ross","complete " + userResponse.body().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }
        );
    }
}
