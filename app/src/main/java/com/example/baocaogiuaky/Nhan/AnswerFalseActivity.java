package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
        textCorrect= findViewById(R.id.textView6);
        // Lấy dữ liệu từ Intent
        byte[] imageBytes = getIntent().getByteArrayExtra("imageBytes");
        String flashcardName = getIntent().getStringExtra("questions");
        String flashcardDescription = getIntent().getStringExtra("answers");
        String flashcardCorrect = getIntent().getStringExtra("correctAnswer");
        // Hiển thị dữ liệu
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            flashcardImageView.setImageBitmap(bitmap);
        }
        flashcardNameTextView.setText(flashcardName);
        flashcardDescriptionTextView.setText(flashcardCorrect);
        textCorrect.setText(flashcardDescription);
        // Tiến trình


        // Chuyển màn hình sau khi nhấn nút
        Button btnNext = findViewById(R.id.btn_end);
        btnNext.setOnClickListener(v -> {
            finish();
        });

        // Quay lại HomeActivity
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AnswerFalseActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
