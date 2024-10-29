package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.Nhan.MainFlashcard;
import com.example.baocaogiuaky.R;

public class Slide4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slide4);
        ImageView img_return_cat = findViewById(R.id.img_return_cat);
        img_return_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Slide4Activity.this , MainFlashcard.class);
                startActivity(intent);
            }
        });

        ImageView ic_back_four = findViewById(R.id.ic_back_four);
        ic_back_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Slide4Activity.this , Slide3Activity.class);
                startActivity(intent);
            }
        });
    }
}