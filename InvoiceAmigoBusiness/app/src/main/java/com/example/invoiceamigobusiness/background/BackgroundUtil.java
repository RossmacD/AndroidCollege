package com.example.invoiceamigobusiness.background;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class BackgroundUtil {
    // schedule the start of the service every 10 - 30 seconds
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleJob(Context context) {
        PeriodicWorkRequest workRequest =new PeriodicWorkRequest.Builder(NotificationWorker.class, 15,TimeUnit.MINUTES).build();
        WorkManager.getInstance(context).enqueue(workRequest);
    }
}
