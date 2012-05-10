package org.antosha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 10.05.12
 */
public class NotifyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent serviceIntent = new Intent();
//		serviceIntent.setAction("org.antosha.NotifyService");
//		context.startService(serviceIntent);

        Intent startServiceIntent = new Intent(context, NotifyService.class);
        context.startService(startServiceIntent);
        Log.e("AAA", "===============Looloo4===============");
    }
}