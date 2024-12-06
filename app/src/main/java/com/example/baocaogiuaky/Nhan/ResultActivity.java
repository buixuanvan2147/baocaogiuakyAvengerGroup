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
    private ArrayList<byte[]> imageBytes;
    private String folderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_writing);

        // Nhận dữ liệu
        ArrayList<String> questions = getIntent().getStringArrayListExtra("questions");
        ArrayList<String> answers = getIntent().getStringArrayListExtra("answers");
        ArrayList<String> userAnswers = getIntent().getStringArrayListExtra("userAnswers");
        imageBytes = (ArrayList<byte[]>) getIntent().getSerializableExtra("imageBytes");
        int correctCount = 0;
        int totalQuestions = questions.size();

        // Tính số câu đúng
        for (int i = 0; i < totalQuestions; i++) {
            if (answers.get(i).equalsIgnoreCase(userAnswers.get(i))) {
                correctCount++;
            }
        }
        folderId = getIntent().getStringExtra("folderId");
        // Cập nhật UI
        TextView tvCorrectCount = findViewById(R.id.tv_correct_count);
        TextView tvWrongCount = findViewById(R.id.tv_wrong_count);
        ProgressBar progressCircle = findViewById(R.id.progress_circle);
        Button btnNewTest = findViewById(R.id.btn_new_test);

        tvCorrectCount.setText("Đúng: " + correctCount);
        tvWrongCount.setText("Sai: " + (totalQuestions - correctCount));

        // Tính phần trăm đúng và sai
        int correctProgress = (correctCount * 100) / totalQuestions;
        int wrongProgress = 100 - correctProgress;

        // Cập nhật màu sắc và tiến trình cho ProgressBar
        progressCircle.setMax(100);
        progressCircle.setProgress(correctProgress);
        progressCircle.setSecondaryProgress(100); // Secondary progress sẽ là 100 để hiển thị phần sai rõ ràng

        // Chức năng của các nút
        Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(v -> {
            // Thoát ứng dụng hoặc quay lại màn hình chính
            Intent intent = new Intent(ResultActivity.this, MainFlashcard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Xóa tất cả các activity trước đó
            startActivity(intent);
            finish(); // Đảm bảo kết thúc activity hiện tại
        });

        btnNewTest.setOnClickListener(v -> {
            // Kiểm tra dữ liệu có null hay không
            if (questions != null && answers != null && imageBytes != null) {
                Intent intent = new Intent(ResultActivity.this, CheckWriting1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Xóa stack cũ nếu có
                intent.putStringArrayListExtra("questions", new ArrayList<>(questions));
                intent.putStringArrayListExtra("answers", new ArrayList<>(answers));
                intent.putExtra("imageBytes", imageBytes); // Truyền lại hình ảnh
                intent.putExtra("totalQuestions", questions.size());
                intent.putExtra("currentQuestionIndex", 1); // Reset về câu hỏi đầu tiên
                intent.putExtra("folderId", folderId);
                startActivity(intent);
                finish(); // Kết thúc màn hình hiện tại
            } else {
                Toast.makeText(ResultActivity.this, "Dữ liệu không đầy đủ để làm mới!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
