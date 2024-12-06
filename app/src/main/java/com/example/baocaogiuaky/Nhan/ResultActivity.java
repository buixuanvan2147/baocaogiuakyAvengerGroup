package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private ArrayList<String> imagePaths;
    private String folderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_writing);

        
        ArrayList<String> questions = getIntent().getStringArrayListExtra("questions");
        ArrayList<String> answers = getIntent().getStringArrayListExtra("answers");
        ArrayList<String> userAnswers = getIntent().getStringArrayListExtra("userAnswers");
        imagePaths = getIntent().getStringArrayListExtra("imagePaths");
        int correctCount = 0;
        int totalQuestions = questions.size();

        
        for (int i = 0; i < totalQuestions; i++) {
            if (answers.get(i).equalsIgnoreCase(userAnswers.get(i))) {
                correctCount++;
            }
        }
        folderId = getIntent().getStringExtra("folderId");

        
        TextView tvCorrectCount = findViewById(R.id.tv_correct_count);
        TextView tvWrongCount = findViewById(R.id.tv_wrong_count);
        ProgressBar progressCircle = findViewById(R.id.progress_circle);
        Button btnNewTest = findViewById(R.id.btn_new_test);

        tvCorrectCount.setText("Đúng: " + correctCount);
        tvWrongCount.setText("Sai: " + (totalQuestions - correctCount));

        
        int correctProgress = (correctCount * 100) / totalQuestions;
        int wrongProgress = 100 - correctProgress;

        
        progressCircle.setMax(100);
        progressCircle.setProgress(correctProgress);
        progressCircle.setSecondaryProgress(100); 

        
        Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(v -> {
            
            Intent intent = new Intent(ResultActivity.this, MainFlashcard.class);
            intent.putExtra("folderId", folderId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
            startActivity(intent);
            finish(); 
        });

        btnNewTest.setOnClickListener(v -> {
            
            if (questions != null && answers != null && imagePaths != null) {
                Intent intent = new Intent(ResultActivity.this, CheckWriting1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
                intent.putStringArrayListExtra("questions", new ArrayList<>(questions));
                intent.putStringArrayListExtra("answers", new ArrayList<>(answers));
                intent.putStringArrayListExtra("imagePaths", imagePaths); 
                intent.putExtra("totalQuestions", questions.size());
                intent.putExtra("currentQuestionIndex", 1); 
                intent.putExtra("folderId", folderId);
                startActivity(intent);
                finish(); 
            } else {
                Toast.makeText(ResultActivity.this, "Dữ liệu không đầy đủ để làm mới!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
