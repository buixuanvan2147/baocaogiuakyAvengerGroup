package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private boolean isPasswordVisible = false;
    private EditText usernameEditText, passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        TextView textforgetpassword = findViewById(R.id.textforgetpassword);
        textforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        TextView textsign = findViewById(R.id.textsign);
        textsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        Button login_btn = findViewById(R.id.button_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        usernameEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText2);
        ImageView eyeIcon = findViewById(R.id.eye_icon);
        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Ẩn mật khẩu
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeIcon.setImageResource(R.drawable.pikpng_com_cute_anime_eyes_png_850945);
                    isPasswordVisible = false;
                } else {
                    // Hiện mật khẩu
                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeIcon.setImageResource(R.drawable.pikpng_com_close_icon_png_905213);
                    isPasswordVisible = true;
                }

                // Đảm bảo con trỏ vẫn ở cuối văn bản sau khi thay đổi chế độ
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
    }
}