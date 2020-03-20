package com.example.invoiceamigobusiness.network.api;

import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface UserApi {
    /**
     *Get the current User
     * @return - the current authorized user
     */
    @GET("user")
    Single<Response<User>> getUser();
}
