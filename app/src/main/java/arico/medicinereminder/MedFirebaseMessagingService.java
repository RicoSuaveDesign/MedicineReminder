// Vanilla FirebaseMessagingService to receive push notifications while in-app.

package arico.medicinereminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by starb on 4/9/2018.
 */

public class MedFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MedFirebaseMsgService";
    private NotificationManager notificationManager;

    public MedFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if(remoteMessage == null)
        {
            return;
        }
        if(remoteMessage.getNotification() != null)
        {
            handleNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());

        }
    }
    public void handleNotification(String title, String message)
    {
        sendNotification(title, message);
    }

    public void sendNotification(String title, String message)
    {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}