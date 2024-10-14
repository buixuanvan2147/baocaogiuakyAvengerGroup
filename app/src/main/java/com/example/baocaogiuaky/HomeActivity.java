package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_flashcard);
        Button btn_add = findViewById(R.id.button_add);
        Button btn_kiemtraviet = findViewById(R.id.btn_KTV);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });
        btn_kiemtraviet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CheckWriting1.class);
                startActivity(intent);
            }
        });
        LinearLayout layoutCow = findViewById(R.id.layout_cow);
        LinearLayout layoutTiger = findViewById(R.id.layout_tiger);

        
        layoutCow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("animal", "cow");
                startActivity(intent);
            }
        });
    }
}