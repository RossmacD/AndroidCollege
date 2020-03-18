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
        authApi.login(login).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        Log.d("RossLog",userResponse.body().getToken());
                        //Add Bearer token to header
                        RetrofitService.addAuthToken("Bearer " + userResponse.body().getToken());
                        //Rebuild to update intercepters and callback factories
                        rebuild();
                        //Get user with authentication token
                        executeGetUser();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RossLog","Login Fail: Error!",e);
                    }
                }
        );


    }

    /**
     * Get user - Uses rxAndroid to retrieve user asynchronously with retrofit
     *
     */
    private void executeGetUser()  {
        Log.d("Ross","Adding observable");
        userApi.getUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        Log.d("Ross","complete " + userResponse.body().getName());
                        //Save Json object as a file to reread later
//                        try {
//                            new Gson().toJson(userResponse.body(),new FileWriter("currentUser"));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("RossLog","Get User Fail: Error!",e);
                    }
                }
        );
    }
}
