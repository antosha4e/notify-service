package org.antosha.sms;

import android.app.Activity;
import android.os.Bundle;

/**
 * User: arsentyev
 * Date: 10.05.12
 */
public class NotifyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(org.antosha.sms.R.layout.main);
    }
}