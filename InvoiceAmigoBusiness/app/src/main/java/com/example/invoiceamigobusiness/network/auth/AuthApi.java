package com.example.invoiceamigobusiness.network.auth;

import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    /**
     * Login
     * @param login - A login model is passed in, contains a password and email
     * @return User (partial) - returns the email, name and token of a successfully logged in user
     */
    @POST("login")
    Single<Response<User>> login(@Body Login login);
}
