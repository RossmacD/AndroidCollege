package com.example.invoiceamigobusiness.network.api;

import com.example.invoiceamigobusiness.network.model.Dash;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface DashApi {

    @GET("dashboard")
    Single<Response<Dash>> getDash();
}
