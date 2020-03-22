package com.example.invoiceamigobusiness.background;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {
    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("Ross","Doing work");
        Intent service = new Intent(getApplicationContext(), NotificationService.class);
        getApplicationContext().startService(service);
        return Result.success();
    }
}
