package coshx.com.rewards;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMMessageService extends FirebaseMessagingService {
    public static final String TAG = "FCMMessagingService";
    public FCMMessageService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setLargeIcon((new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.rewards_active))).getBitmap())
                            .setSmallIcon(R.drawable.pango)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setStyle(new NotificationCompat.BigTextStyle())
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody());

            int mNotificationId = 001;
            for (String s : remoteMessage.getData().values()){
                Log.e(TAG, s);
            }
// Sets an ID for the notification
// Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.flags = Notification.FLAG_SHOW_LIGHTS;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.ledARGB = 0xffffffff;
            notification.ledOnMS = 500;
            notification.ledOffMS = 2000;
            Intent notificationIntent = new Intent(this, DealDetailsActivity.class);
            notificationIntent.putExtra("item_id", "1001");
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            notification.contentIntent = contentIntent;
            mNotifyMgr.notify(mNotificationId,  notification);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
