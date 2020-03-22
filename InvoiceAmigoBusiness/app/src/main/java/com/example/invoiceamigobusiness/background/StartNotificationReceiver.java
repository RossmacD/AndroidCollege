package com.example.invoiceamigobusiness.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class StartNotificationReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Ross","REGISTERED RECIVER!!!!!!!!");
        BackgroundUtil.scheduleJob(context);
    }
}
