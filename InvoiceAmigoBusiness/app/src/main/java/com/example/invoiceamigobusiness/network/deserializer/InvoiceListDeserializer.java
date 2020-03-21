package com.example.invoiceamigobusiness.network.deserializer;

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

public class InvoiceListDeserializer implements JsonDeserializer<List<Invoice>> {

    @Override
    public List<Invoice> deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Invoice> invoices = new ArrayList<>();
        JsonElement content;
        if (je.getAsJsonObject().has("outgoingInvoices")) {
            content = je.getAsJsonObject().get("outgoingInvoices").getAsJsonObject().get("data");
            for(JsonElement invoicesJsonElement:content.getAsJsonArray()){
                invoices.add(new Gson().fromJson(invoicesJsonElement.getAsJsonObject(), Invoice.class));
            }
        }
        return invoices;
    }
}
