package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;
import com.example.baocaogiuaky.R;

public class Slide2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slide2);

        ImageView ic_back_two = findViewById(R.id.ic_back_two);
        ic_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(Slide2Activity.this , Silide1Activity.class);
                startActivity(itent);
            }
        });
        ImageView ic_forward_two = findViewById(R.id.ic_forward_two);
        ic_forward_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(Slide2Activity.this , Slide3Activity.class);
                startActivity(itent);
            }
        });

        ImageView img_return_tiger = findViewById(R.id.img_return_tiger);
        img_return_tiger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(Slide2Activity.this , ShowFlashcardActivity.class);
                startActivity(itent);
            }
        });
    }
}