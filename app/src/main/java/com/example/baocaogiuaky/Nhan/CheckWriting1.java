package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;

public class CheckWriting1 extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvProgress;
    private boolean isEditingWord = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_writing1);

        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);


        progressBar.setProgress(50);
        tvProgress.setText("1/2");

        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWriting1.this, CheckWriting2.class);
                startActivity(intent);
            }
        });
        Button btncheck = findViewById(R.id.btn_check);
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWriting1.this, AnswerTrueActivity.class);
                startActivity(intent);
            }
        });

        Button btnPrevious = findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWriting1.this, MainFlashcard.class);
                startActivity(intent);
            }
        });
        EditText inputWord = findViewById(R.id.input_word);
        Button buttonEditWord = findViewById(R.id.button_edit);
        inputWord.setEnabled(false);
        buttonEditWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditingWord) {

                    inputWord.setEnabled(false);
                    buttonEditWord.setBackground(ContextCompat.getDrawable(CheckWriting1.this, R.drawable.icon_edit));
                } else {

                    inputWord.setEnabled(true);
                    buttonEditWord.setBackground(ContextCompat.getDrawable(CheckWriting1.this, R.drawable.icon_cancel));
                }
                isEditingWord = !isEditingWord;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar.setProgress(50);
        tvProgress.setText("1/2");
    }

}
