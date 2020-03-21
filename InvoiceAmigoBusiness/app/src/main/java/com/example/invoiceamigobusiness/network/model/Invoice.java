package com.example.invoiceamigobusiness.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invoice {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("invoice_number")
    @Expose
    private int invoiceNumber;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("total_cost")
    @Expose
    private int totalCost;


    public Invoice(int id, int invoiceNumber, String invoiceDate, String dueDate, String status, String note, int totalCost) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.status = status;
        this.note = note;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
