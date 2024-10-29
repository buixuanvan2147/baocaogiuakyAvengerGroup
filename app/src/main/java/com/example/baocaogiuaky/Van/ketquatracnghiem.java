package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baocaogiuaky.Nhan.MainFlashcard;
import com.example.baocaogiuaky.R;

public class ketquatracnghiem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ketquatracnghiem);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ketquatracnghiem.this , MainFlashcard.class);
                startActivity(intent);
            }
        });

        Button btnReTest = findViewById(R.id.btnReTest);
        btnReTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ketquatracnghiem.this , Choice0Activity.class);
                startActivity(intent);
            }
        });

        Button btnReviewFlashcards = findViewById(R.id.btnReviewFlashcards);
        btnReviewFlashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ketquatracnghiem.this , Silide1Activity.class);
                startActivity(intent);
            }
        });
    }
}