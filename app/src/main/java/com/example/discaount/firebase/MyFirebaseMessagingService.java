package com.example.discaount.firebase;


import android.util.Log;

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

//            String data_type = remoteMessage.getData().get("data_type");
//            String message = remoteMessage.getNotification().getBody();
//            String title = remoteMessage.getNotification().getTitle();
//            Intent intent = new Intent(this, Splash.class);
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

//            if (remoteMessage.getData().get("data_type").equals("news")) {
//                data_type = "news";
//               // showNotification(message, data_type, title);
//                Intent intent = new Intent(this, NewsActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//                notificationManager.cancel(1);
//            } else if (remoteMessage.getData().get("data_type").equals("withdraw")){
//                data_type = "withdraw";
//                //showNotification(message, data_type, title);
//                Intent intent = new Intent(this, LastOperations.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//                notificationManager.cancel(1);
//                //showNotification(message, data_type, title);
//            }
            //   else{}
        } catch (Exception e) {
            Log.d("ANA", "" + e);
        }
    }
//    private RemoteViews getCustomDesign(String title, String message) {
//        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
//        remoteViews.setTextViewText(R.id.titleNo, title);
//        remoteViews.setTextViewText(R.id.messageNo, message);
//        remoteViews.setImageViewResource(R.id.imageNo, R.drawable.logo);
//        return remoteViews;
//    }

//    private void showNotification(String message, String data_type, String title) {
//        Intent intent =null;
//        if (data_type.equals("news"))
//        {
//            intent = new  Intent(this, NewsActivity.class);
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.putExtra("data_type", data_type);
//            intent.putExtra("message", message);
//            intent.putExtra("title", title);
//            startActivity(intent);
//        }
//        else if (data_type.equals("withdraw"))
//        {
//            intent = new  Intent(this, LastOperations.class);
//            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.putExtra("data_type", data_type);
//            intent.putExtra("message", message);
//            intent.putExtra("title", title);
//            startActivity(intent);
//        }
//        // SharedToken.getInstance(getApplication()).storeNotification(data_type,message,title);
//        int fileId = R.raw.consequence;
//        Log.d("anarasta", data_type);
//
//        Uri customSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + fileId);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent).setSound(customSoundUri)
//                .setAutoCancel(true);
//
//        int notificationId = (int) System.currentTimeMillis();
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationChannel notificationChannel =
//                    new NotificationChannel("",
//                            "ZoFirmChannel_1", NotificationManager.IMPORTANCE_HIGH);
//
//            AudioAttributes attributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                    .build();
//
//            notificationChannel.setSound(customSoundUri, attributes);
//
//            mNotificationManager.createNotificationChannel(notificationChannel);
//            mBuilder.setChannelId("");
//            int notificationId2 = (int) System.currentTimeMillis();
//            mNotificationManager.notify(notificationId2, mBuilder.build());
//            return;
//        }
//
//        notificationManager.notify(notificationId, mBuilder.build());
//    }

}

