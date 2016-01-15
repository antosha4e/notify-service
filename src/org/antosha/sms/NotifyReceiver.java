package org.antosha.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: arsentyev
 * Date: 10.05.12
 */
public class NotifyReceiver extends BroadcastReceiver {
    private static final String TAG = NotifyReceiver.class.getName();
    public static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private static final String BANK_NAME = Build.FINGERPRINT.startsWith("generic") ? "123" : "Otkritie_FC";

//    @Override
    public void onReceive1(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        final PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Loooolka");
        wl.acquire();

        service.schedule(new Runnable() {
            public void run() {
                wl.release();
            }
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_LONG).show();
        //Log.e(TcsWidget.class.getName(), "SUKA!!!!! - " + intent.getAction());
        //Ловим наш Broadcast, проверяем и выводим сообщение
        final String action = intent.getAction();
        String balance = "";

        //Telephony.Sms.Intents.SMS_RECEIVED_ACTION
        if(SMS_RECEIVED.equals(action)) {
//            Log.e(TcsWidget.class.getName(), "SMS CAME!");
            //---get the SMS message passed in---
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg = "", from = "";

            if(bundle != null) {
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];

                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    from = msgs[i].getOriginatingAddress();
                    msg = msgs[i].getMessageBody();

                    if(BANK_NAME.equalsIgnoreCase(from)) {
                        balance = parseMsg(msg, context);
                        if(balance != null) {
//                            setText(context, balance);
                        }
                    }
                }

                Log.e(TAG, "FROM: " + from);
                Log.e(TAG, "MSG: " + msg);
            }
        }
    }

    private String parseMsg(String msg, Context context) {
        String code = null;

        Pattern pattern = Pattern.compile("пароль - (.*?) ");
//        pattern = Pattern.compile(" - (.*?) ");
        Matcher matcher = pattern.matcher(msg);

        if(matcher.find() && matcher.group().length() > 1) {
//            System.out.println(matcher.group(1));

            int sdk = android.os.Build.VERSION.SDK_INT;

            if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(matcher.group(1));
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("text label", matcher.group(1));
                clipboard.setPrimaryClip(clip);
            }
        }

        return code;
    }
}