package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_writing);
        Button btnAnswerTrue = findViewById(R.id.btn_answer_true);
        Button btnAnswerFalse = findViewById(R.id.btn_answer_false);

        
        Button btnRetry = findViewById(R.id.btn_retry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(ResultActivity.this, CheckWriting1.class);
                startActivity(intent);
                finish();
            }
        });

        
        Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        btnAnswerTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(ResultActivity.this, ShowFlashcard2.class);
                startActivity(intent);
            }
        });

        btnAnswerFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(ResultActivity.this, ShowFlashcard1.class);
                startActivity(intent);
            }
        });
    }
}
