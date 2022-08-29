package com.jecnaparlament.jecnak.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.controllers.Controllers;
import com.jecnaparlament.jecnak.databinding.ActivityMainBinding;
import com.jecnaparlament.jecnak.models.Connect;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Controllers controllers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences("login", MODE_PRIVATE).getString("user", "").isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            try {
                controllers = new ControlerConection().execute().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home,
                    R.id.navigation_grades,
                    R.id.navigation_canteen,
                    R.id.navigation_attendance,
                    R.id.navigation_profile
            ).build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);


        }


    }

    public Controllers getControlers() {
        return controllers;
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
                editor.commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

            return new Controllers(connect);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_logout:
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", null);
                editor.putString("pass", null);
                editor.commit();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.menu_about:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Studentsky-Parlament-Jecna/jecnak#readme")));
                return true;
        }
        return false;
    }
}