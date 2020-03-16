package com.example.invoiceamigobusiness.network.auth;

import com.example.invoiceamigobusiness.network.model.Login;
import com.example.invoiceamigobusiness.network.model.User;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthApi {

    /**
     * @param login - A login model is passed in, contains a password and email
     * @return User - returns the email, name and token of a successfully logged in user
     */
    @POST("login")
    Call<User> login(@Body Login login);

//    @GET("User")
//    Call<User>

}
