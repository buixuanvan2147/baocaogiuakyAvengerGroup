package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button  btnconfirm_change = findViewById(R.id.btnconfirm_change);
        btnconfirm_change.setOnClickListener(v -> {
            // Hiển thị DeleteFolderDialogFragment dưới dạng dialog
            ComfirmChangePasswordFragment dialogFragment = new ComfirmChangePasswordFragment();
            dialogFragment.show(getSupportFragmentManager(), "ComfirmChangePasswordFragment");
        });
        Button  btn_cancel_change= findViewById(R.id.btn_cancel_change);
        btn_cancel_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


    }
}