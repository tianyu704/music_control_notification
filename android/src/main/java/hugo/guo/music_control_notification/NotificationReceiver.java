package hugo.guo.music_control_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by hugo on 2019-07-22
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "prev":
                MusicControlNotificationPlugin.callEvent("prev");
                break;
            case "next":
                MusicControlNotificationPlugin.callEvent("next");
                break;
            case "toggle":
                String title = intent.getStringExtra("title");
                String action = intent.getStringExtra("action");

                MusicControlNotificationPlugin.show(title, action.equals("play"));
                MusicControlNotificationPlugin.callEvent(action);
                break;
            case "select":
                Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                context.sendBroadcast(closeDialog);
                String packageName = context.getPackageName();
                PackageManager pm = context.getPackageManager();
                Intent launchIntent = pm.getLaunchIntentForPackage(packageName);
                context.startActivity(launchIntent);

                MusicControlNotificationPlugin.callEvent("select");
                break;
            case "close":
                MusicControlNotificationPlugin.callEvent("close");
                break;
        }
    }
}
