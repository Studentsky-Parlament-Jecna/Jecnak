package com.jecnaparlament.jecnak.activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import com.jecnaparlament.jecnak.controllers.Controllers;
import com.jecnaparlament.jecnak.helpers.exception.LoginException;
import com.jecnaparlament.jecnak.models.Connect;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.notifications.SchoolService;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View layout = findViewById(android.R.id.content);
        TextView textView = findViewById(R.id.github_link);
        TextInputEditText userTextEdit = findViewById(R.id.username);
        TextInputEditText passTextEdit = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(v -> {
            String user = userTextEdit.getText().toString();
            String pass = passTextEdit.getText().toString();

            if (!(user.isEmpty() || pass.isEmpty())) {
                new Thread() {
                    public void run() {
                        try {
                            Connect connect = new Connect(user, pass);
                            savePreferences(user, pass);
                        } catch (LoginException e) {
                            Snackbar.make(layout, "Zkontrolujte zadané přihlašovací údaje", Snackbar.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Snackbar.make(layout, "Zkontrolujte internetové připojení", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }.start();
            }
        });

        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void savePreferences(String user, String pass) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.apply();

        startActivity(new Intent(this, MainActivity.class));
    }



}