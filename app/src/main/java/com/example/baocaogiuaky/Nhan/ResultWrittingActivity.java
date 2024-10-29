package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

public class ResultWrittingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_writting2);
        Button btn_exit = findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultWrittingActivity.this , MainFlashcard.class);
                startActivity(intent);
            }
        });

        TextView re_card_cow = findViewById(R.id.re_card_cow);
        re_card_cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultWrittingActivity.this , ShowFlashcardcowActivity.class);
                startActivity(intent);
            }
        });

        TextView re_card_cat = findViewById(R.id.re_card_cat);
        re_card_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultWrittingActivity.this , ShowFlashcardcatActivity.class);
                startActivity(intent);
            }
        });

        Button btn_replay = findViewById(R.id.btn_replay);
        btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultWrittingActivity.this , CheckWritting1Activity.class);
                startActivity(intent);
            }
        });
    }
}