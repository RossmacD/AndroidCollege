package com.example.invoiceamigobusiness.network.deserializer;

import android.util.Log;

import com.example.invoiceamigobusiness.network.model.Invoice;
import com.example.invoiceamigobusiness.network.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class InvoiceListDeserializer implements JsonDeserializer<List<Invoice>> {

    /**
     * Turns the received invoice API request into a list of deserialized POJOs
     * Unwraps response.outgoingInvoices.data[]
     *
     * @param je - The JSON element to be deserialized
     * @param typeOfT - The Type to deserialize
     * @param context
     * @return - Returns a List of deserialized invoices
     */
    @Override
    public List<Invoice> deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Invoice> invoices = new ArrayList<>();
        JsonElement content;
        if (je.getAsJsonObject().has("outgoingInvoices")) {
            content = je.getAsJsonObject().get("outgoingInvoices").getAsJsonObject().get("data");
            for(JsonElement invoicesJsonElement : content.getAsJsonArray()){
                //Deserialize each invoice as normal in array
                invoices.add(new Gson().fromJson(invoicesJsonElement.getAsJsonObject(), Invoice.class));
            }
        }
        return invoices;
    }
}
