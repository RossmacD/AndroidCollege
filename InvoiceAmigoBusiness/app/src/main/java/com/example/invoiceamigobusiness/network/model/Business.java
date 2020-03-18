package com.example.invoiceamigobusiness.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Business {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("business_name")
    @Expose
    private String businessName;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("postcode")
    @Expose
    private String postcode;

    public Business(int id, String businessName, String website, String address, String postcode) {
        this.id = id;
        this.businessName = businessName;
        this.website = website;
        this.address = address;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusiness_name() {
        return businessName;
    }

    public void setBusiness_name(String business_name) {
        this.businessName = business_name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
