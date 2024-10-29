package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

public class CheckWritting2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_writting2);
        ImageView img_return = findViewById(R.id.img_return);
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWritting2Activity.this , MainFlashcard.class);
                startActivity(intent);
            }
        });

        Button btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWritting2Activity.this ,ResultWrittingActivity.class);
                startActivity(intent);
            }
        });
        Button btn_examine_cat= findViewById(R.id.btn_examine_cat);
        btn_examine_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWritting2Activity.this ,AnswerFalseActivity.class);
                startActivity(intent);
            }
        });
    }
}