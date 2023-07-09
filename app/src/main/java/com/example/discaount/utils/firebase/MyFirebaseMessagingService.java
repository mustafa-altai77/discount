package com.example.discaount.utils.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.discaount.R;
import com.example.discaount.views.activity.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = "MyFirebaseMessagingSer";

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {

            String data_type = remoteMessage.getData().get("data_type");
            String message = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();
            showNotification(message, data_type, title);

//                intent.putExtra("withdraw", data_type);
//                intent.putExtra("message", message);
//                intent.putExtra("title", title);


            Log.d("ANA", "" +remoteMessage.getData());
            if (remoteMessage.getData().get("data_type").equals("news")) {
                data_type = "news";
//
            } else {
                showNotification(message, data_type, title);
            }
        } catch (Exception e) {
            Log.d("ANA", "" + e);
        }
    }

//    private RemoteViews getCustomDesign(String title, String message) {
////        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName());
////        remoteViews.setTextViewText(R.id.title_text_view, title);
////        remoteViews.setTextViewText(R.id.message_text_view, message);
////        remoteViews.setImageViewResource(R.id.image_view_photo, R.drawable.logo);
//        return remoteViews;
//    }

    private void showNotification(String message, String data_type, String title) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.putExtra("news", data_type);
//        intent.putExtra("message", message);
//        intent.putExtra("title", title);
        // SharedToken.getInstance(getApplication()).storeNotification(data_type,message,title);
        Log.d("anarasta", data_type);

        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent).setSound(customSoundUri)
                .setAutoCancel(true);

        int notificationId = (int) System.currentTimeMillis();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel =
                    new NotificationChannel("",
                            "ZoFirmChannel_1", NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            notificationChannel.setSound(customSoundUri, attributes);

            mNotificationManager.createNotificationChannel(notificationChannel);
            mBuilder.setChannelId("");
            int notificationId2 = (int) System.currentTimeMillis();
            mNotificationManager.notify(notificationId2, mBuilder.build());

            return;

        }

        notificationManager.notify(notificationId, mBuilder.build());
    }

}
