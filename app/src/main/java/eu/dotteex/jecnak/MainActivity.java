package eu.dotteex.jecnak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutionException;

import eu.dotteex.jecnak.controllers.Controllers;
import eu.dotteex.jecnak.databinding.ActivityMainBinding;
import eu.dotteex.jecnak.models.Connect;

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
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            BottomNavigationView navView = findViewById(R.id.nav_view);

            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_grades, R.id.navigation_profile)
                    .build();
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



}