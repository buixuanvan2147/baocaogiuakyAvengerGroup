package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.Nhan.MainFlashcard;
import com.example.baocaogiuaky.R;

public class Silide1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_silide1);
        ImageView img_return_cow = findViewById(R.id.img_return_cow);
        img_return_cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Silide1Activity.this, MainFlashcard.class);
                startActivity(intent);
            }
        });

        ImageView ic_forward_one = findViewById(R.id.ic_forward_one);
        ic_forward_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Silide1Activity.this, Slide2Activity.class);
                startActivity(intent);
            }
        });


    }
}