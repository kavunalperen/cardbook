/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cardbook.android.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cardbook.android.R;
import com.cardbook.android.activities.AppMainTabActivity;
import com.cardbook.android.adapters.NotificationListener;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.models.CBNotification;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService implements NotificationListener{
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Bundle extras;
    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "Cardbook GCM";

    @Override
    protected void onHandleIntent(Intent intent) {
        extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
        	
        	Log.v("MEssage", extras.toString());
//            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
//                sendNotification("Send error: " + extras.toString());
//            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
//                sendNotification("Deleted messages on server: " + extras.toString());
//            // If it's a regular GCM message, do some work.
//            } else
              if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                  AppConstants.notificationListener=this;
                  AppConstants.getCompanyList(null, true);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification() {
       AppConstants.notificationListener=null;
    	Resources res = getResources();
//    	BitmapDrawable icon = new BitmapDrawable(res,"ic_launcher.png");
    	Bitmap icon2=BitmapFactory.decodeResource(this.getResources(), R.drawable.app_icon);
    	mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        JSONObject jNotification=null;
        try {
            jNotification=new JSONObject(extras.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jNotification!=null){
            CBNotification notificaiton=new CBNotification(jNotification);





            Intent intent=new Intent(this, AppMainTabActivity.class);

            intent.putExtra(CBNotification.NOTIFICATION_TYPE,notificaiton.getNotificationType());
            intent.putExtra(CBNotification.DETAIL_ID,notificaiton.getDetailId());

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_CANCEL_CURRENT);


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.app_icon)
            .setLargeIcon(icon2)
            .setContentTitle("CardBook")
            .setContentText(notificaiton.getAlert())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    //        .setAutoCancel(true);
    //        .setFullScreenIntent(contentIntent, true);
    //        .setSound(RingtoneManager.getDefaultUri(CBNotification.DEFAULT_SOUND))

            mBuilder.setContentIntent(contentIntent);
            Notification ntf=mBuilder.build();
            ntf.flags=Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(NOTIFICATION_ID, ntf);
        }
    }
    
    public static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            return null;
        }

        return bitmap;
    }

    @Override
    public void showNotification() {
        sendNotification();
    }
}
