package com.example.invoiceamigobusiness.ui.invoices;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.InvoiceFragmentBinding;
import com.example.invoiceamigobusiness.network.model.Invoice;
import com.example.invoiceamigobusiness.network.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class InvoiceViewModel extends ViewModel {
    public void getInvoices(InvoiceFragmentBinding invoiceFragmentBinding){
        //Call to UI - get invoices
        Single invoiceResponse= Repository.getInstance().executeGetInvoices();

        //Observe response on UI thread then dispose
        invoiceResponse.subscribe(
                new DisposableSingleObserver<Response<List<Invoice>>>() {
                    @Override
                    public void onSuccess(Response<List<Invoice>> invoiceResponse) {
                        invoiceFragmentBinding.recyclerView.setAdapter(new InvoiceRecyclerViewAdapter(invoiceResponse.body()));
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("RossLog","Get User Fail: Error!",e);
                    }
                }
        );
    }
}
