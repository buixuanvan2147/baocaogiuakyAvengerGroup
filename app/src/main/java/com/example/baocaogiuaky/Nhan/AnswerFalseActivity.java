package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;

public class AnswerFalseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_false);
        Button btnPrevious = findViewById(R.id.btn_previous); 
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                finish();
            }
        });
        Button btnend = findViewById(R.id.btn_end);
        btnend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerFalseActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerFalseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
