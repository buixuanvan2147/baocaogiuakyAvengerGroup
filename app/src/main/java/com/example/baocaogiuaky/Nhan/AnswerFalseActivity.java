package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;

public class AnswerFalseActivity extends AppCompatActivity {

    private ImageView flashcardImageView;
    private TextView flashcardNameTextView;
    private TextView textCorrect;
    private TextView flashcardDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_false);

        flashcardImageView = findViewById(R.id.id_imageview);
        flashcardNameTextView = findViewById(R.id.id_show);
        flashcardDescriptionTextView = findViewById(R.id.id_inputword);
        textCorrect = findViewById(R.id.textView6);

        
        String imagePath = getIntent().getStringExtra("imagePath");
        String flashcardName = getIntent().getStringExtra("questions");
        String flashcardDescription = getIntent().getStringExtra("answers");
        String flashcardCorrect = getIntent().getStringExtra("correctAnswer");

        
        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                flashcardImageView.setImageBitmap(bitmap);
            } else {
                Log.e("AnswerFalseActivity", "Failed to decode image from path: " + imagePath);
            }
        }
        flashcardNameTextView.setText(flashcardName);
        flashcardDescriptionTextView.setText(flashcardCorrect);
        textCorrect.setText(flashcardDescription);

        
        Button btnNext = findViewById(R.id.btn_end);
        btnNext.setOnClickListener(v -> finish());

        
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AnswerFalseActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
