package com.example.invoiceamigobusiness.network.model;

import android.util.Log;

import com.example.invoiceamigobusiness.network.deserializer.UserDeserializer;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

//@JsonAdapter(UserDeserializer.class)
public class User {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("token")
    @Expose
    private String token;

//    @SerializedName("business")
//    @Expose
//    private Business business;


    public User(int id, String email, String name, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.token = token;
        Log.d("Ross","DOWLER");
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}


