package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class CheckWriting1 extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvProgress, questionTextView;
    private ImageView questionImageView;
    private boolean isEditingWord = false;
    private String correctAnswer;
    private int totalQuestions;
    private int currentQuestionIndex;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> userAnswers = new ArrayList<>();
    private ArrayList<byte[]> imageBytes; 
    private EditText inputWord;
    private String folderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_writing1);

        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tv_progress);
        questionTextView = findViewById(R.id.id_show);
        inputWord = findViewById(R.id.input_word);
        Button buttonEditWord = findViewById(R.id.button_edit);
        questionImageView = findViewById(R.id.id_imageview);

        folderId = getIntent().getStringExtra("folderId");
        questions = getIntent().getStringArrayListExtra("questions");
        answers = getIntent().getStringArrayListExtra("answers");
        imageBytes = (ArrayList<byte[]>) getIntent().getSerializableExtra("imageBytes");
        totalQuestions = getIntent().getIntExtra("totalQuestions", 1);
        currentQuestionIndex = getIntent().getIntExtra("currentQuestionIndex", 1);

        questionTextView.setText(questions.get(currentQuestionIndex - 1));

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes.get(currentQuestionIndex - 1), 0, imageBytes.get(currentQuestionIndex - 1).length);
        questionImageView.setImageBitmap(bitmap);

        
        inputWord.setEnabled(false);
        buttonEditWord.setOnClickListener(v -> {
            if (isEditingWord) {
                inputWord.setEnabled(false);
                buttonEditWord.setBackground(ContextCompat.getDrawable(CheckWriting1.this, R.drawable.icon_edit));
            } else {
                inputWord.setEnabled(true);
                buttonEditWord.setBackground(ContextCompat.getDrawable(CheckWriting1.this, R.drawable.icon_cancel));
            }
            isEditingWord = !isEditingWord;
        });
        userAnswers = getIntent().getStringArrayListExtra("userAnswers");
        if (userAnswers == null) {
            userAnswers = new ArrayList<>();
            for (int i = 0; i < totalQuestions; i++) {
                userAnswers.add(""); 
            }
        }

        
        inputWord.setText(userAnswers.get(currentQuestionIndex - 1));
        
        inputWord.setOnEditorActionListener((v, actionId, event) -> {
            String userAnswer = inputWord.getText().toString().trim(); 

            
            if (userAnswer.isEmpty()) {
                
                Toast.makeText(CheckWriting1.this, "Vui lòng nhập câu trả lời", Toast.LENGTH_SHORT).show();
            } else {
                userAnswers.set(currentQuestionIndex - 1, userAnswer);
                
                if (checkAnswer(userAnswer)) {
                    
                    Intent intent = new Intent(CheckWriting1.this, AnswerTrueActivity.class);
                    intent.putExtra("imageBytes", imageBytes.get(currentQuestionIndex - 1)); 
                    intent.putExtra("answers", answers.get(currentQuestionIndex - 1));  
                    intent.putExtra("questions", questions.get(currentQuestionIndex - 1));
                    intent.putExtra("folderId", folderId);
                    startActivity(intent);
                } else {
                    
                    Intent intent = new Intent(CheckWriting1.this, AnswerFalseActivity.class);
                    intent.putExtra("imageBytes", imageBytes.get(currentQuestionIndex - 1)); 
                    intent.putExtra("answers", answers.get(currentQuestionIndex - 1));  
                    intent.putExtra("questions", questions.get(currentQuestionIndex - 1));  
                    intent.putExtra("correctAnswer", userAnswer);
                    intent.putExtra("folderId", folderId);
                    startActivity(intent);
                }

                
                if (currentQuestionIndex < totalQuestions) {
                    currentQuestionIndex++;  
                    Intent intent = new Intent(CheckWriting1.this, CheckWriting1.class);
                    intent.putStringArrayListExtra("questions", questions);
                    intent.putStringArrayListExtra("answers", answers);
                    intent.putExtra("imageBytes", imageBytes); 
                    intent.putExtra("totalQuestions", totalQuestions);
                    intent.putExtra("currentQuestionIndex", currentQuestionIndex);
                    intent.putStringArrayListExtra("userAnswers", userAnswers);
                    intent.putExtra("folderId", folderId);
                    startActivity(intent);
                    finish();
                } else {
                    
                    Intent intent = new Intent(CheckWriting1.this, ResultActivity.class);
                    intent.putStringArrayListExtra("questions", questions);
                    intent.putStringArrayListExtra("answers", answers);
                    intent.putStringArrayListExtra("userAnswers", userAnswers);
                    intent.putExtra("imageBytes", imageBytes);
                    intent.putExtra("folderId", folderId);
                    startActivity(intent);
                    finish();
                }
            }
            return true; 
        });

        
        
        Button btnCheck = findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(v -> {
            String userAnswer = inputWord.getText().toString().trim(); 

            
            if (userAnswer.isEmpty()) {
                
                Toast.makeText(CheckWriting1.this, "Vui lòng nhập câu trả lời", Toast.LENGTH_SHORT).show();
            } else {
                userAnswers.add(userAnswer);
                
                if (checkAnswer(userAnswer)) {
                    
                    Intent intent = new Intent(CheckWriting1.this, AnswerTrueActivity.class);
                    intent.putExtra("imageBytes", imageBytes.get(currentQuestionIndex - 1)); 
                    intent.putExtra("answers", answers.get(currentQuestionIndex - 1));  
                    intent.putExtra("questions", questions.get(currentQuestionIndex - 1));
                    intent.putExtra("folderId", folderId);
                    startActivity(intent);

                } else {
                    
                    Intent intent = new Intent(CheckWriting1.this, AnswerFalseActivity.class);
                    intent.putExtra("imageBytes", imageBytes.get(currentQuestionIndex - 1)); 
                    intent.putExtra("answers", answers.get(currentQuestionIndex - 1));  
                    intent.putExtra("questions", questions.get(currentQuestionIndex - 1));  
                    intent.putExtra("correctAnswer", userAnswer);
                    intent.putExtra("folderId", folderId);
                    startActivity(intent);
                }
            }
        });

        
        

        Button btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(v -> {
            
            userAnswers.set(currentQuestionIndex - 1, "");

            
            if (currentQuestionIndex == totalQuestions) {
                Intent intent = new Intent(CheckWriting1.this, ResultActivity.class);
                intent.putStringArrayListExtra("questions", questions);
                intent.putStringArrayListExtra("answers", answers);
                intent.putExtra("imageBytes", imageBytes);
                intent.putExtra("totalQuestions", totalQuestions);
                intent.putExtra("currentQuestionIndex", currentQuestionIndex);
                intent.putStringArrayListExtra("userAnswers", userAnswers);
                intent.putExtra("folderId", folderId);
                startActivity(intent);
                finish();
            } else {
                
                currentQuestionIndex++;
                Intent intent = new Intent(CheckWriting1.this, CheckWriting1.class);
                intent.putStringArrayListExtra("questions", questions);
                intent.putStringArrayListExtra("answers", answers);
                intent.putExtra("imageBytes", imageBytes); 
                intent.putExtra("totalQuestions", totalQuestions);
                intent.putExtra("currentQuestionIndex", currentQuestionIndex);
                intent.putStringArrayListExtra("userAnswers", userAnswers);
                intent.putExtra("folderId", folderId);
                startActivity(intent);
                finish();
            }
        });


        


        updateTitle();
        
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(CheckWriting1.this, MainFlashcard.class);
            intent.putExtra("folderId", getIntent().getStringExtra("folderId"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
            startActivity(intent);
            finish(); 
        });
    }
    private void updateTitle() {
        
        String titleText = "Bài " + currentQuestionIndex ;
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(titleText);
        
    }
    private void updateProgressBar() {
        
        int progress = (int) (((double) currentQuestionIndex / totalQuestions) * 100);
        progressBar.setProgress(progress);
        tvProgress.setText(currentQuestionIndex + "/" + totalQuestions);
        updateTitle();
    }

    private boolean checkAnswer(String userAnswer) {
        
        String correctAnswer = answers.get(currentQuestionIndex - 1); 
        return userAnswer.equalsIgnoreCase(correctAnswer);  
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProgressBar();
    }
}

