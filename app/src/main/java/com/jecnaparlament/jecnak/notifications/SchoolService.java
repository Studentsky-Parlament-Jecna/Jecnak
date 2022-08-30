package com.jecnaparlament.jecnak.notifications;

import static com.jecnaparlament.jecnak.activities.SettingsActivity.KEY_ARE_NOTIFICATION_ENABLED;
import static com.jecnaparlament.jecnak.enums.CardType.*;
import static com.jecnaparlament.jecnak.notifications.App.CHANNEL_GRADES_ID;
import static com.jecnaparlament.jecnak.notifications.App.CHANNEL_NEWS_ID;
import static com.jecnaparlament.jecnak.notifications.App.CHANNEL_RECORD_ID;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.controllers.CardController;
import com.jecnaparlament.jecnak.controllers.Controllers;
import com.jecnaparlament.jecnak.enums.CardType;
import com.jecnaparlament.jecnak.helpers.exception.LoginException;
import com.jecnaparlament.jecnak.models.Card;
import com.jecnaparlament.jecnak.models.Connect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SchoolService extends JobService {
    SharedPreferences settings;
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.d("SchoolService", "Job started");
        if (settings.getBoolean(KEY_ARE_NOTIFICATION_ENABLED,false)){
            doBackgroundWork(jobParameters);
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("SchoolService", "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }


    private void doBackgroundWork(JobParameters jobParameters) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        CardController cardController = getCardController();
                        ArrayList<Card> freshCards = cardController.getCards();
                        ArrayList<Card> oldCards = cardController.getOldCards();

                        //Go through all fresh cards and check if they are new
                        //If they are new, send notification
                        if (oldCards != null)
                        for (int i = 0; i < freshCards.size(); i++) {
                            if (!oldCards.contains(freshCards.get(i))) {
                                switch (freshCards.get(i).getType()) {
                                    case GRADE:
                                        sendNotification(
                                                freshCards.get(i).getTitle(),
                                                freshCards.get(i).getContent(),
                                                CHANNEL_GRADES_ID
                                        );
                                        break;
                                    case NEWS:
                                        sendNotification(
                                                freshCards.get(i).getTitle(),
                                                freshCards.get(i).getContent(),
                                                CHANNEL_NEWS_ID
                                        );
                                        break;
                                    case RECORD:
                                        sendNotification(
                                                freshCards.get(i).getTitle(),
                                                freshCards.get(i).getContent(),
                                                CHANNEL_RECORD_ID
                                        );
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        jobFinished(jobParameters, false);
                    }
                }
        ).start();
    }

    private void sendNotification(String title, String description, final String CHANNEL_ID) {
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.parlament_jecna)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(new Random().nextInt(), notification);
    }

    private CardController getCardController(){
        SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
        String user = sh.getString("user", "");
        String password = sh.getString("pass", "");
        Controllers controllers = null;
        try {
            controllers = new Controllers(new Connect(user, password));
        } catch (LoginException | IOException e) {
            e.printStackTrace();
        }
        return new CardController(
                controllers.getNewsController(),
                controllers.getGradeController(),
                controllers.getRecordController(),
                getApplicationContext()
        );
    }
}
