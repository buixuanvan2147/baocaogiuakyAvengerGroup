package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_email, txt_pass;
    private Button button_login;
    private TextView txt_forgetpass, txt_sigin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        txt_email = findViewById(R.id.txt_email);
        txt_pass = findViewById(R.id.txt_pass);

        ImageView icon_eye = findViewById(R.id.eye_icon);
        icon_eye.setImageResource(R.drawable.hide);
        icon_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_pass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    txt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    icon_eye.setImageResource(R.drawable.hide);
                } else {
                    txt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    icon_eye.setImageResource(R.drawable.show);
                }
            }
        });

        txt_sigin = findViewById(R.id.textsign);
        txt_sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        txt_forgetpass = findViewById(R.id.textforgetpassword);
        txt_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() || !validatePassword()) {
                    return;
                } else {
                    String userEmail = txt_email.getText().toString().trim();
                    String userPassword = txt_pass.getText().toString().trim();

                    auth.signInWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Đăng nhập thành công
                                    Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Sai email hoặc mật khẩu
                                    Toast.makeText(LoginActivity.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    public boolean validateEmail() {
        String val = txt_email.getText().toString().trim();
        if (val.isEmpty()) {
            txt_email.setError("Email cannot be empty");
            return false;
        } else {
            txt_email.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = txt_pass.getText().toString().trim();
        if (val.isEmpty()) {
            txt_pass.setError("Password cannot be empty");
            return false;
        } else {
            txt_pass.setError(null);
            return true;
        }
    }


}
