package com.example.invoiceamigobusiness.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dash {
    @SerializedName("invoicesCreated")
    @Expose
    private int invoicesCreated;

    @SerializedName("totalIncome")
    @Expose
    private int totalIncome;

    @SerializedName("totalOutstanding")
    @Expose
    private int totalOutstanding;

    @SerializedName("paidCount")
    @Expose
    private int paidCount;

    @SerializedName("unseenCount")
    @Expose
    private int unseenCount;


    public Dash(int invoicesCreated, int totalIncome, int totalOutstanding, int paidCount, int unseenCount) {
        this.invoicesCreated = invoicesCreated;
        this.totalIncome = totalIncome;
        this.totalOutstanding = totalOutstanding;
        this.paidCount = paidCount;
        this.unseenCount = unseenCount;
    }

    public int getInvoicesCreated() {
        return invoicesCreated;
    }

    public void setInvoicesCreated(int invoicesCreated) {
        this.invoicesCreated = invoicesCreated;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(int totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public int getPaidCount() {
        return paidCount;
    }

    public void setPaidCount(int paidCount) {
        this.paidCount = paidCount;
    }

    public int getUnseenCount() {
        return unseenCount;
    }

    public void setUnseenCount(int unseenCount) {
        this.unseenCount = unseenCount;
    }
}
