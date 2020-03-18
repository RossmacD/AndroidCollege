package com.example.invoiceamigobusiness.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    //TODO remove contexrt
    public void login(MainFragmentBinding mainFragmentBinding, Context context) {
        //Show Loading state in UI
        mainFragmentBinding.setLoading(true);
//        mainFragmentBinding.email.getText().toString() , mainFragmentBinding.password.getText().toString())
        Single response= Repository.getInstance().executeLogin(new Login("ultan.on98@gmail.com","secret"));
        response.subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        Log.d("RossLog",userResponse.body().getToken());
                        //Add Bearer token to header
                        RetrofitService.addAuthToken("Bearer " + userResponse.body().getToken());
                        //Rebuild to update intercepters and callback factories
                        Repository.getInstance().rebuild();
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RossLog","Login Fail: Error!",e);
                        mainFragmentBinding.setLoading(false);
                    }
                }
        );
    }

}

