package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnswerTrueActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_true);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);


        progressBar.setProgress(50);
        tvProgress.setText("1/2");
        Button btnnext = findViewById(R.id.btn_next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerTrueActivity.this, CheckWriting2.class);
                startActivity(intent);
            }
        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerTrueActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

