package com.jecnaparlament.jecnak.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {
    public static final String CHANNEL_GRADES_ID = "channel_grades";
    public static final String CHANNEL_NEWS_ID = "channel_news";
    public static final String CHANNEL_IS_LATE_ID = "channel_is_late";
    public static final String CHANNEL_RECORD_ID = "channel_record_id";
    public static final String CHANNEL_CANTEEN_ID = "channel_canteen";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {

        NotificationChannel grades = new NotificationChannel(
                CHANNEL_GRADES_ID,
                "Známky",
                NotificationManager.IMPORTANCE_HIGH
        );
        grades.setDescription("Kanál pro zasílání notifikací o nových známkách, apod...");

        NotificationChannel news = new NotificationChannel(
                CHANNEL_NEWS_ID,
                "Novinky",
                NotificationManager.IMPORTANCE_HIGH
        );
        news.setDescription("Kanál pro zasílání notifikací o novinkách");

        NotificationChannel islate = new NotificationChannel(
                CHANNEL_IS_LATE_ID,
                "Pozdní příchody",
                NotificationManager.IMPORTANCE_HIGH
        );
        islate.setDescription("Kanál pro zasílání notifikací o pozdních příchodech");

        NotificationChannel canteen = new NotificationChannel(
                CHANNEL_CANTEEN_ID,
                "Jídelna",
                NotificationManager.IMPORTANCE_HIGH
        );
        canteen.setDescription("Kanál pro zasílání notifikací o obědech");

        NotificationChannel record = new NotificationChannel(
                CHANNEL_RECORD_ID,
                "Pochvaly, napomenutí apod...",
                NotificationManager.IMPORTANCE_HIGH
        );
        record.setDescription("Kanál pro zasílání notifikací o pochvalách, napomenutích apod...");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(grades);
        manager.createNotificationChannel(news);
        manager.createNotificationChannel(islate);
        manager.createNotificationChannel(canteen);
        manager.createNotificationChannel(record);

    }
}
