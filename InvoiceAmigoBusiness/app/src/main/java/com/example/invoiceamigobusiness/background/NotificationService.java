package com.example.invoiceamigobusiness.background;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.invoiceamigobusiness.R;
import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.network.RetrofitService;
import com.example.invoiceamigobusiness.network.model.Invoice;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class NotificationService extends Service {
    private static int notificationID = 1;
    private final IBinder mBinder = new MyBinder();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Ross","Doing Service");
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        String token = sharedPref.getString("token",null);
        if(token!=null){
            //Add Bearer token to header
            RetrofitService.addAuthToken("Bearer " +token);
            //Rebuild to update intercepters and callback factories
            Repository.getInstance().rebuild();
        }
        createNotificationChannel();
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Single invoiceResponse= Repository.getInstance().executeGetInvoices();
        invoiceResponse.subscribe(
                new DisposableSingleObserver<Response<List<Invoice>>>() {
                    @Override
                    public void onSuccess(Response<List<Invoice>> invoiceResponse) {
                        //Get old date
                        SharedPreferences checksumPref = getApplicationContext().getSharedPreferences("checksum", MODE_PRIVATE);
                        String oldChecksum = checksumPref.getString("dateChecksum","");

                        Log.d("Ross"," Old Checksum" + oldChecksum);
                        //Get Invoice with largest updatedAt date
                        Invoice lastUpdatedInvoice = Collections.max(invoiceResponse.body() , (o1, o2) -> {
//                                String o1Checksum=o1.getUpdatedAt();
//                                //Check for notifications
//                                Log.d("Ross","o1 against check"+o1Checksum.compareToIgnoreCase(oldChecksum)+" | o1Checksum"+o1Checksum +"| o1 id: "+o1.getId());
//                                if(!oldChecksum.equals("") && o1Checksum.compareToIgnoreCase(oldChecksum)>0){
//                                    if(o1.getStatus().equals("paid")){
//                                        Log.d("Ross","Paid - sending notification");
//                                        final String message="Invoice #"+o1.getInvoiceNumber()+" paid";
//                                        notificationManager.notify(notificationID, buildNotification(message,pendingIntent).build());
//                                        notificationID++;
//                                    }else{
//                                        //DO NOTHING: ADD NOTIFICATION FOR NEW INVOICE SENT?????
//                                        final String message="Invoice #"+o1.getInvoiceNumber()+" paid";
//                                    }
//                                }
                                return o1.getUpdatedAt().compareToIgnoreCase(o2.getUpdatedAt());
                        });

                        if(!oldChecksum.equals("")){
                            for (Invoice invoice: invoiceResponse.body()){
                                Log.d("Ross",""+invoice.getUpdatedAt().compareToIgnoreCase(oldChecksum));
                                if(invoice.getUpdatedAt().compareToIgnoreCase(oldChecksum)>0){
                                    final String message="Invoice #"+invoice.getInvoiceNumber()+" paid";
                                        Log.d("Ross",message);
                                        notificationManager.notify(notificationID, buildNotification(message,pendingIntent).build());
                                        notificationID++;
                                }
                            }
                        }



                        Log.d("Ross",lastUpdatedInvoice.getUpdatedAt());
                        //Rewrite checksum
                        SharedPreferences.Editor editor = checksumPref.edit();
                        editor.putString("dateChecksum", lastUpdatedInvoice.getUpdatedAt() );
                        editor.apply();


                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("RossLog","Get User Fail: Error!",e);
                    }
                }
        );



        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        NotificationService getService() {
            return NotificationService.this;
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("IA:B Channel", "Notification From InvoiceAmigo", importance);
                channel.setDescription("Invoice Amigo");
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager.createNotificationChannel(channel);
            }
    }

    private NotificationCompat.Builder buildNotification(String message, PendingIntent pendingIntent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "IA:B Channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("InvoiceAmigo: Business")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        return builder;
    }
}
