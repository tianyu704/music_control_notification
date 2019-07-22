package hugo.guo.music_control_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class NotificationBar {
    private Context parent;
    private NotificationManager nManager;
    private NotificationCompat.Builder nBuilder;
    private RemoteViews remoteView;
    private String title;
    private String author;
    private boolean play;

    public NotificationBar(Context parent, String title, boolean play) {
        this.parent = parent;
        this.title = title;
        this.play = play;

        nBuilder = new NotificationCompat.Builder(parent, "music_control_notification")
                .setSmallIcon(R.drawable.ic_music_black_24dp)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(this.play)
                .setOnlyAlertOnce(true)
                .setVibrate(new long[]{0L})
                .setSound(null);

        remoteView = new RemoteViews(parent.getPackageName(), R.layout.notification_bar);

        remoteView.setTextViewText(R.id.title, title);
//        remoteView.setTextViewText(R.id.author, author);

        if (this.play) {
            remoteView.setImageViewResource(R.id.btn_play, R.drawable.ic_pause_black_24dp);
        } else {
            remoteView.setImageViewResource(R.id.btn_play, R.drawable.ic_play_black_24dp);
        }

        setListeners(remoteView);
        nBuilder.setContent(remoteView);

        Notification notification = nBuilder.build();

        nManager = (NotificationManager) parent.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(1, notification);
    }

    public void setListeners(RemoteViews view) {
        //
        Intent intent = new Intent(parent, NotificationReceiver.class)
                .setAction("toggle")
                .putExtra("title", this.title)
//                .putExtra("author", this.author)
                .putExtra("action", !this.play ? "play" : "pause");
        // 播放暂停
        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btn_play, pendingIntent);

        // 下一首
        Intent nextIntent = new Intent(parent, NotificationReceiver.class)
                .setAction("next");
        PendingIntent pendingNextIntent = PendingIntent.getBroadcast(parent, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btn_next, pendingNextIntent);

        // 上一首
        Intent prevIntent = new Intent(parent, NotificationReceiver.class)
                .setAction("prev");
        PendingIntent pendingPrevIntent = PendingIntent.getBroadcast(parent, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btn_pre, pendingPrevIntent);

        // 点击
        Intent selectIntent = new Intent(parent, NotificationReceiver.class)
                .setAction("select");
        PendingIntent selectPendingIntent = PendingIntent.getBroadcast(parent, 0, selectIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        view.setOnClickPendingIntent(R.id.layout, selectPendingIntent);

        // 关闭
        Intent closeIntent = new Intent(parent, NotificationReceiver.class)
                .setAction("close");
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(parent, 0, closeIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        view.setOnClickPendingIntent(R.id.btn_close, closePendingIntent);


    }


    public void notificationCancel() {
        nManager.cancel(1);
    }
}

