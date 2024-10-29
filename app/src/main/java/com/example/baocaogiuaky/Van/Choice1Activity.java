package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.Nhan.MainFlashcard;
import com.example.baocaogiuaky.R;

public class Choice1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice1);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice1Activity.this, MainFlashcard.class);
                startActivity(intent);
            }
        });
        Button tracnghiem1_next = findViewById(R.id.tracnghiem1_next);
        tracnghiem1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice1Activity.this, Choice2Activity.class);
                startActivity(intent);
            }
       });

        Button tracnghiem1_previous = findViewById(R.id.tracnghiem1_previous);
        tracnghiem1_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice1Activity.this, Choice0Activity.class);
                startActivity(intent);
            }
        });
    }
}