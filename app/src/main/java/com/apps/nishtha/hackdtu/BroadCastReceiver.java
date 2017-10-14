package com.apps.nishtha.hackdtu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Nishita Aggarwal on 14-10-2017.
 */

public class BroadCastReceiver extends BroadcastReceiver {
    BroadcastReceiver alarmReceiver;
    @Override
    public void onReceive(Context context, Intent intent) {
        IntentFilter intentFilter = new IntentFilter();
        //intentFilter.addAction(ALARM);
        //intentFilter.addAction(INTENT_ACTION_ALARM);

        alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // DO SOMETHING

            }
        };

        context.registerReceiver(alarmReceiver, intentFilter);
    }
}
