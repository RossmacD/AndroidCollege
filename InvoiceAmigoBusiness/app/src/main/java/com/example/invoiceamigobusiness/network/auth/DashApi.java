package com.example.invoiceamigobusiness.network.auth;

import com.example.invoiceamigobusiness.network.model.Dash;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface DashApi {

    @GET("dashboard")
    Single<Response<Dash>> getDash();
}
