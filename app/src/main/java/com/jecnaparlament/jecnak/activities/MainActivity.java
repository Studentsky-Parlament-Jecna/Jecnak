package com.jecnaparlament.jecnak.activities;

import static com.jecnaparlament.jecnak.activities.SettingsActivity.KEY_ARE_NOTIFICATION_ENABLED;
import static com.jecnaparlament.jecnak.activities.SettingsActivity.KEY_NOTIFICATION_INTERVAL;
import static com.jecnaparlament.jecnak.activities.SettingsActivity.KEY_NOTIFICATION_ON_CELLULAR;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.controllers.Controllers;
import com.jecnaparlament.jecnak.databinding.ActivityMainBinding;
import com.jecnaparlament.jecnak.models.Connect;
import com.jecnaparlament.jecnak.notifications.SchoolService;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static Controllers controllers;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getSharedPreferences("login", MODE_PRIVATE).getString("user", "").isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (controllers == null){
                try {
                    controllers = new ControlerConection().execute().get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            settings = PreferenceManager.getDefaultSharedPreferences(this);

            //Set notifications
            if (settings.getBoolean(KEY_ARE_NOTIFICATION_ENABLED, false)) scheduleJob();

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home,
                    R.id.navigation_schedule,
                    R.id.navigation_grades,
                    R.id.navigation_canteen,
                    R.id.navigation_profile
            ).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }


    }

    private class ControlerConection extends AsyncTask<Void, Void, Controllers> {

        @Override
        protected Controllers doInBackground(Void... arg0) {
            SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
            String user = sh.getString("user", "");
            String password = sh.getString("pass", "");
            Connect connect = null;
            try {
                connect = new Connect(user, password);
            } catch (Exception e) {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", null);
                editor.putString("pass", null);
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            return new Controllers(connect);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_logout:
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", null);
                editor.putString("pass", null);
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Studentsky-Parlament-Jecna/jecnak#readme")));
                return true;
        }
        return false;
    }

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, SchoolService.class);
        // TODO: 30.08.2022 - Apply parameters from settings
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setPersisted(true)
                // Turn hours to milliseconds and set the periodicity
                .setPeriodic(12*3600*1000)
                //.setPeriodic((long) Integer.parseInt(settings.getString(KEY_NOTIFICATION_INTERVAL, "")) *3600*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("SchoolService", "Job scheduled");
        } else {
            Log.d("SchoolService", "Job scheduling failed");
        }
    }

    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d("SchoolService", "Job cancelled");
    }
}