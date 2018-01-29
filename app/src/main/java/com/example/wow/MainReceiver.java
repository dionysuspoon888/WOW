package com.example.wow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by D on 1/29/2018.
 */

public class MainReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {

            Toast.makeText(context, "Phone Call is Coming!!!", Toast.LENGTH_LONG).show();
        }

    }
}
