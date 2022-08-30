package com.jecnaparlament.jecnak.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.jecnaparlament.jecnak.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_ARE_NOTIFICATION_ENABLED = "notification_enabled";
    public static final String KEY_NOTIFICATION_INTERVAL = "notification_interval";
    public static final String KEY_NOTIFICATION_ON_CELLULAR = "notification_cellular_enabled";
    public static final String KEY_GRADES = "notification_grades";
    public static final String KEY_NEWS = "notification_news";
    public static final String KEY_LATE_ARRIVAL = "notification_late_arrival";
    public static final String KEY_DND = "features_dnd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}