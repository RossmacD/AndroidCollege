package com.example.invoiceamigobusiness.ui.invoices;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invoiceamigobusiness.databinding.InvoiceRecyclerItemBinding;
import com.example.invoiceamigobusiness.network.model.Invoice;

import java.util.List;

public class InvoiceRecyclerViewAdapter extends RecyclerView.Adapter<InvoiceRecyclerViewAdapter.InvoiceViewHolder> {
    private List<Invoice> invoices;
    public InvoiceRecyclerViewAdapter(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvoiceRecyclerItemBinding invoiceRecyclerItemBinding =InvoiceRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new InvoiceViewHolder(invoiceRecyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice currentInvoice = invoices.get(position);
        holder.bind(currentInvoice);

    }

    @Override
    public int getItemCount() {
        if (invoices != null) {
            return invoices.size();
        } else {
            return 0;
        }
    }

    class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private InvoiceRecyclerItemBinding invoiceRecyclerItemBinding;
        public InvoiceViewHolder(@NonNull InvoiceRecyclerItemBinding invoiceRecyclerItemBinding) {
            super(invoiceRecyclerItemBinding.getRoot());
            this.invoiceRecyclerItemBinding = invoiceRecyclerItemBinding;
        }
        void bind(Invoice invoice){
            invoiceRecyclerItemBinding.setInvoice(invoice);
            invoiceRecyclerItemBinding.executePendingBindings();
        }
    }

}
