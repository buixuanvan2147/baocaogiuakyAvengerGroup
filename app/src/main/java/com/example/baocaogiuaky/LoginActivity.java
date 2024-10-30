package com.example.baocaogiuaky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private EditText usernameEditText, passwordEditText;
    private CheckBox rememberMeCheckBox;

    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "rememberMe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText2);
        rememberMeCheckBox = findViewById(R.id.linearLayout7).findViewById(R.id.checkbox);
        ImageView eyeIcon = findViewById(R.id.eye_icon);
        Button loginBtn = findViewById(R.id.button_login);

        // Set onClick for eye icon to show/hide password
        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeIcon.setImageResource(R.drawable.pikpng_com_cute_anime_eyes_png_850945);
                    isPasswordVisible = false;
                } else {
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeIcon.setImageResource(R.drawable.pikpng_com_close_icon_png_905213);
                    isPasswordVisible = true;
                }
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        // Load saved credentials if "Remember Me" is checked
        loadCredentials();

        // Set onClick for "Login" button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save credentials if "Remember Me" is checked
                if (rememberMeCheckBox.isChecked()) {
                    saveCredentials(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } else {
                    clearCredentials();
                }

                // Start HomeActivity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Set onClick for "Forgot Password" link
        TextView textforgetpassword = findViewById(R.id.textforgetpassword);
        textforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        // Set onClick for "Sign Up" link
        TextView textsign = findViewById(R.id.textsign);
        textsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER_ME, true);
        editor.apply();
    }

    private void loadCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
        if (rememberMe) {
            String username = sharedPreferences.getString(KEY_USERNAME, "");
            String password = sharedPreferences.getString(KEY_PASSWORD, "");
            usernameEditText.setText(username);
            passwordEditText.setText(password);
            rememberMeCheckBox.setChecked(true);
        }
    }

    private void clearCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_REMEMBER_ME);
        editor.apply();
    }
}
