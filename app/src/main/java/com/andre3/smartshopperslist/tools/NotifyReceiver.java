package com.andre3.smartshopperslist.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ODBBROW on 10/27/2016.
 */

public class NotifyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("Alarm fired!!!!");
    }
}
