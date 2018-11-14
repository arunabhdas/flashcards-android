package com.google.developer.flashcards.reminders;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.preference.PreferenceManager;

import com.google.developer.flashcards.MainActivity;
import com.google.developer.flashcards.R;

public class NotificationService extends IntentService {
    public static final int NOTIFICATION_ID = 8;
    private static final String TAG = NotificationService.class.getSimpleName();
    private NotificationManager mNotificationManager;
    private Context mContext;

    public NotificationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Notification notification = new Notification(R.drawable.widget_preview, "Notify Alarm strart", System.currentTimeMillis());
        Intent intent = new Intent(this , MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext.getApplicationContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.sym_action_email)
                .setContentTitle(getString(R.string.time_to_practice))
                .setContentText(getString(R.string.it_is_time_to_practice))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo(getString(R.string.time_to_practice));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String remindersKey = getString(R.string.pref_key_reminders);
        boolean enabled = sharedPreferences.getBoolean(remindersKey, false);
        if (enabled) {
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }
}
