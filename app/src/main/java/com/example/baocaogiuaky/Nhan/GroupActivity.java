package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.MainActivity;
import com.example.baocaogiuaky.R;

import dev.sagar.progress_button.ProgressButton;

public class GroupActivity extends AppCompatActivity {
    ProgressButton pbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_thanhvien);
        pbtn =findViewById(R.id.loading);
        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbtn.loading();
                Handler h= new Handler(); h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pbtn.finished();
                        Intent intent = new Intent(GroupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    }, 3000);
                }
            });
        }
}
