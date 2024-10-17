package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

public class CheckWritting1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_writting1);

        ImageView img_return = findViewById(R.id.img_return);
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWritting1Activity.this , ShowFlashcardActivity.class);
                startActivity(intent);
            }
        });
        Button btn_next_one  = findViewById(R.id.btn_next_one);
        btn_next_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWritting1Activity.this , CheckWritting2Activity.class);
                startActivity(intent);
            }
        });

        Button btn_examine  = findViewById(R.id.btn_examine);
        btn_examine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWritting1Activity.this ,AnswerTrueActivity.class);
                startActivity(intent);
            }
        });

    }
}