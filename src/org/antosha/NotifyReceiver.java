package org.antosha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 10.05.12
 */
public class NotifyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "Loooolka");
        wl.acquire();

        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
            public void run() {
                wl.release();
            }
        }, 10, TimeUnit.SECONDS);
    }
}