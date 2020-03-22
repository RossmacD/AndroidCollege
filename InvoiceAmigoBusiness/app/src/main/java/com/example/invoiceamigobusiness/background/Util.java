package com.example.invoiceamigobusiness.background;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class Util {
    // schedule the start of the service every 10 - 30 seconds
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleJob(Context context) {
        Log.d("Ross","Scheduled");
//        ComponentName serviceComponent = new ComponentName(context, NotificationJobService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
//        builder.setMinimumLatency(1 * 1000); // wait at least
//        builder.setOverrideDeadline(3 * 1000); // maximum delay
//        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
//        jobScheduler.schedule(builder.build());
        PeriodicWorkRequest workRequest =new PeriodicWorkRequest.Builder(NotificationWorker.class, 15,TimeUnit.MINUTES).build();
        WorkManager.getInstance(context).enqueue(workRequest);

    }
}
