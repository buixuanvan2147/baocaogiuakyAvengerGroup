package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;

public class AnswerTrueActivity extends AppCompatActivity {

    private ImageView flashcardImageView;
    private TextView flashcardNameTextView;
    private TextView flashcardDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_true);

        flashcardImageView = findViewById(R.id.id_imageview);
        flashcardNameTextView = findViewById(R.id.id_show);
        flashcardDescriptionTextView = findViewById(R.id.id_inputword);

        // Lấy dữ liệu từ Intent
        byte[] imageBytes = getIntent().getByteArrayExtra("imageBytes");
        String flashcardName = getIntent().getStringExtra("questions");
        String flashcardDescription = getIntent().getStringExtra("answers");
        // Hiển thị dữ liệu
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            flashcardImageView.setImageBitmap(bitmap);
        }
        flashcardNameTextView.setText(flashcardName);
        flashcardDescriptionTextView.setText(flashcardDescription);

        // Tiến trình


        // Chuyển màn hình sau khi nhấn nút
        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> {
            finish();
        });

        // Quay lại HomeActivity
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(AnswerTrueActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
