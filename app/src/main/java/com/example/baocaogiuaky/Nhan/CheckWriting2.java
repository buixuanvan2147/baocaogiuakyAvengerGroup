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

public class CheckWriting2 extends AppCompatActivity {
    private boolean isEditingWord = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_writing2);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView tvProgress = findViewById(R.id.tv_progress);


        progressBar.setProgress(100);
        tvProgress.setText("2/2");

        Button btnPrevious = findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        Button btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWriting2.this, ResultActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btncheck = findViewById(R.id.btn_check);
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWriting2.this, AnswerFalseActivity.class);
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
                    buttonEditWord.setBackground(ContextCompat.getDrawable(CheckWriting2.this, R.drawable.icon_edit));
                } else {

                    inputWord.setEnabled(true);
                    buttonEditWord.setBackground(ContextCompat.getDrawable(CheckWriting2.this, R.drawable.icon_cancel));
                }
                isEditingWord = !isEditingWord;
            }
        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckWriting2.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

