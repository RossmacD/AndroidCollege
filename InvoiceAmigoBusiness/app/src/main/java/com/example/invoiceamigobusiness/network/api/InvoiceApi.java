package com.example.invoiceamigobusiness.network.api;

import com.example.invoiceamigobusiness.network.model.Invoice;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface InvoiceApi {
    @GET("invoices")
    Single<Response<List<Invoice>>> getInvoices();
}
