package hugo.guo.music_control_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * MusicControlNotificationPlugin
 */
public class MusicControlNotificationPlugin implements MethodCallHandler {
    private static final String CHANNEL_ID = "music_control_notification";
    private static Registrar registrar;
    private static NotificationBar notificationBar;
    private static MethodChannel channel;

    private MusicControlNotificationPlugin(Registrar r) {
        registrar = r;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        MusicControlNotificationPlugin plugin = new MusicControlNotificationPlugin(registrar);
        MusicControlNotificationPlugin.channel = new MethodChannel(registrar.messenger(), CHANNEL_ID);
        MusicControlNotificationPlugin.channel.setMethodCallHandler(plugin);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        switch (call.method) {
            case "show":
                final String title = call.argument("title");
                final boolean play = call.argument("play");
                show(title, play);
                result.success(null);
                break;
            case "hide":
                hide();
                result.success(null);
                break;
            default:
                result.notImplemented();
        }
    }

    public static void callEvent(String event) {
        MusicControlNotificationPlugin.channel.invokeMethod(event, null, new Result() {
            @Override
            public void success(Object o) {
                // this will be called with o = "some string"
            }

            @Override
            public void error(String s, String s1, Object o) {
            }

            @Override
            public void notImplemented() {
            }
        });
        if (event.equals("close")) {
            notificationBar.notificationCancel();
        }
    }

    public static void show(String title, boolean play) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.enableVibration(false);
            channel.setSound(null, null);
            NotificationManager notificationManager = registrar.context().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationBar = new NotificationBar(registrar.context(), title, play);
    }

    private void hide() {
        notificationBar.notificationCancel();
    }
}
