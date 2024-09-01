package com.example.notificationservice;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationService extends NotificationListenerService {
    private Context context;
    private FileUtils fileUtils;

    private static final  String TAG = "NotificationService";
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        this.fileUtils = new FileUtils(context);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "Listener connected.");
    }

    String replaceString(String text){
        if(!text.isEmpty()){
            text = text.replace(",", " ");
            text = text.replace("\n", " ");
        }
        return text;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.i(TAG, "Notification posted.");
        Bundle bundle = sbn.getNotification().extras;
        String ticker, title, text, pack;

        StringBuilder actions = new StringBuilder();
        if(sbn.getNotification().actions != null){
            for(Notification.Action action: sbn.getNotification().actions){
                String actionTitle = action.title.toString();
                actionTitle = replaceString(actionTitle);
                actions.append(actionTitle).append("|");
            }
        }


        pack = sbn.getPackageName();
        ticker = (String) sbn.getNotification().tickerText;
        title = ""+bundle.getCharSequence(Notification.EXTRA_TITLE);
        text = ""+bundle.getCharSequence(Notification.EXTRA_TEXT);

        if(ticker == null) ticker = "";
        if(title.isEmpty() && text.isEmpty() && ticker.isEmpty()) return;

        title = replaceString(title);
        text = replaceString(text);
        ticker = replaceString(ticker);

        String data = pack+","+ticker+","+title+","+text+","+actions;
        fileUtils.saveData(data+"\n", "NotificationService.txt", true);
        fileUtils.sendMsg(data);
    }
}
