package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;
import com.example.baocaogiuaky.R;

public class Choice2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice2);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice2Activity.this, ShowFlashcardActivity.class);
                startActivity(intent);
            }
        });

        Button tracnghiem1_previous = findViewById(R.id.tracnghiem1_previous);
        tracnghiem1_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice2Activity.this, Choice1Activity.class);
                startActivity(intent);
            }
        });
        Button tracnghiem1_next = findViewById(R.id.tracnghiem1_next);
        tracnghiem1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice2Activity.this, Choice3Activity.class);
                startActivity(intent);
            }
        });
    }
}