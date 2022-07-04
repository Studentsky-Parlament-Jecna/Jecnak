package eu.dotteex.jecnak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.github_link);
        TextInputEditText userTextEdit = findViewById(R.id.username);
        TextInputEditText passTextEdit = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String user = userTextEdit.getText().toString();
            String pass = passTextEdit.getText().toString();

            if (!(user.isEmpty() || pass.isEmpty())) {
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", user);
                editor.putString("pass", pass);
                editor.commit();

                startActivity(new Intent(this, MainActivity.class));
            }
        });


        textView.setMovementMethod(LinkMovementMethod.getInstance());



    }

}