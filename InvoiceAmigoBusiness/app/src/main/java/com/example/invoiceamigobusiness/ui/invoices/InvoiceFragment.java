package com.example.invoiceamigobusiness.ui.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invoiceamigobusiness.R;
import com.example.invoiceamigobusiness.databinding.InvoiceFragmentBinding;
import com.example.invoiceamigobusiness.network.model.Invoice;



public class InvoiceFragment extends Fragment {
    private InvoiceViewModel mViewModel;
    private InvoiceFragmentBinding invoiceFragmentBinding;

    public static InvoiceFragment newInstance() {
        return new InvoiceFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    //Inflate for AndroidX DataBinding
    invoiceFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.invoice_fragment, container, false);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(RecyclerView.VERTICAL);
    invoiceFragmentBinding.recyclerView.setLayoutManager(layoutManager);
    //Create ViewModelProvider
    mViewModel =new ViewModelProvider(this).get(InvoiceViewModel.class);
        mViewModel.getInvoices(invoiceFragmentBinding);



    return invoiceFragmentBinding.getRoot();
    }
}
