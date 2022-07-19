package eu.dotteex.jecnak.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import eu.dotteex.jecnak.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}