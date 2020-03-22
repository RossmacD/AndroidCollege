package com.example.invoiceamigobusiness.network;

import android.util.Log;

import com.example.invoiceamigobusiness.network.deserializer.InvoiceListDeserializer;
import com.example.invoiceamigobusiness.network.deserializer.UserDeserializer;
import com.example.invoiceamigobusiness.network.model.Invoice;
import com.example.invoiceamigobusiness.network.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static final String BASE_URL ="http://10.0.2.2:8000/api/";
    private static String authToken;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson =new GsonBuilder()
            .registerTypeAdapter(User.class, new UserDeserializer())
            .registerTypeAdapter(new TypeToken<List<Invoice>>() {}.getType(), new InvoiceListDeserializer())
            .create();
    private static Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    public static <S> S createService(Class<S> serviceClass){
        return  retrofit.create(serviceClass);
    }

    /**
     * Rebuild retrofit to contain auth token
     * @param _authToken - The AuthToken to add as a default header
     */
    public static void addAuthToken(String _authToken){
        authToken=_authToken;
        //Add Authorisation header
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Authorization", authToken).build();
            return chain.proceed(request);
        });
        //Add headers to send and receive JSON - filters out html, etc.. on bad request
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Content-Type", "application/json").build();
            return chain.proceed(request);
        });

        //Add Logging interceptor - use filter OkHttp to view these responses
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }
}
