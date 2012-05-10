package org.antosha;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 10.05.12
 */
public class NotifyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}