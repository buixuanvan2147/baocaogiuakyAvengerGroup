package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;
import com.example.baocaogiuaky.R;

public class Slide3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slide3);

        ImageView ic_back_three = findViewById(R.id.ic_back_three);
        ic_back_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(Slide3Activity.this , Slide2Activity.class);
                startActivity(itent);
            }
        });
        ImageView ic_forward_three = findViewById(R.id.ic_forward_three);
        ic_forward_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(Slide3Activity.this , Slide4Activity.class);
                startActivity(itent);
            }
        });

        ImageView img_return_dog = findViewById(R.id.img_return_dog);
        img_return_dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(Slide3Activity.this , ShowFlashcardActivity.class);
                startActivity(itent);
            }
        });
    }
}