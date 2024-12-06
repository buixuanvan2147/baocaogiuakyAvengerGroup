package com.example.baocaogiuaky.Nhan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private GridView gridView;
    private TextView tvScore ,tvPointChange, tvTimer;
    private int score = 0; // Điểm khởi đầu là 50
    private int targetScore = 100; // Điểm mục tiêu để chiến thắng
    private String folderId;
    private List<String> names;
    private List<String> descriptions;
    private List<String> cards;
    private String selectedCard = null;
    private int selectedCardPosition = -1;
    private int timeLeft = 60;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trochoi);

        gridView = findViewById(R.id.gridView);
        tvScore = findViewById(R.id.tvScore);
        tvTimer = findViewById(R.id.tvTimer);
        names = getIntent().getStringArrayListExtra("names");
        descriptions = getIntent().getStringArrayListExtra("descriptions");
        folderId = getIntent().getStringExtra("folderId");
        cards = new ArrayList<>();
        cards.addAll(names);
        cards.addAll(descriptions);
        Collections.shuffle(cards);
        tvPointChange = findViewById(R.id.tvPointChange);
        CardAdapter adapter = new CardAdapter(this, cards);
        gridView.setAdapter(adapter);
        Button btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(v -> {
            // Thoát ứng dụng hoặc quay lại màn hình chính
            Intent intent = new Intent(GameActivity.this, MainFlashcard.class);
            intent.putExtra("folderId", folderId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Xóa tất cả các activity trước đó
            startActivity(intent);
            finish(); // Đảm bảo kết thúc activity hiện tại
        });
        tvScore.setText("Score: " + score); // Hiển thị điểm khởi đầu
        startTimer();
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String clickedCard = cards.get(position);
            TextView textView = (TextView) view;

            if (selectedCard == null) {
                selectedCard = clickedCard;
                selectedCardPosition = position;
                textView.setBackgroundColor(Color.BLUE);
            } else {
                if (selectedCardPosition == position) {
                    return;
                }

                textView.setBackgroundColor(Color.BLUE);

                if (isMatchingPair(selectedCard, clickedCard)) {
                    score += 10; // Cộng điểm khi đúng
                    tvScore.setText("Score: " + score);
                    showPointChange("+10", Color.GREEN);
                    view.setBackgroundColor(Color.GREEN);
                    gridView.getChildAt(selectedCardPosition).setBackgroundColor(Color.GREEN);
                    cards.set(selectedCardPosition, "");
                    cards.set(position, "");
                    adapter.notifyDataSetChanged();
                    if (allCardsMatched()) {
                        timer.cancel(); // Dừng bộ đếm ngược
                        showWinDialog();
                    }




                    Toast.makeText(this, "Correct Match!", Toast.LENGTH_SHORT).show();
                } else {
                    if (score > 0) {
                        score -= 5; // Trừ 5 điểm
                        if (score < 0) {
                            score = 0; // Nếu điểm < 0, giữ nguyên là 0
                        }
                    }
                    tvScore.setText("Score: " + score);
                    showPointChange("-5", Color.RED);

                    // Đổi màu nền của 2 ô thành đỏ
                    view.setBackgroundColor(Color.RED);
                    gridView.getChildAt(selectedCardPosition).setBackgroundColor(Color.RED);

                    if (score == 0) {
                        // Kiểm tra nếu điểm về 0 và hết thời gian
                        if (timeLeft <= 0) {
                            showGameOverDialog();
                        }
                        animateShake(view);
                        animateShake(gridView.getChildAt(selectedCardPosition));
                        new Handler().postDelayed(this::resetCardColors, 1000);
                        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
                    } else {
                        animateShake(view);
                        animateShake(gridView.getChildAt(selectedCardPosition));
                        new Handler().postDelayed(this::resetCardColors, 1000);
                        Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }

                selectedCard = null;
                selectedCardPosition = -1;
            }
        });
    }
    private void startTimer() {
        timer = new CountDownTimer(60000, 1000) { // 60 giây, mỗi giây đếm 1 lần
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000); // Cập nhật thời gian còn lại
                tvTimer.setText("Time: " + timeLeft + "s"); // Hiển thị thời gian còn lại
            }

            @Override
            public void onFinish() {
                timeLeft = 0; // Hết thời gian
                showGameOverDialog(); // Hiển thị thông báo thua cuộc
            }
        };
        timer.start();

    }
    private boolean allCardsMatched() {
        for (String card : cards) {
            if (!card.isEmpty()) return false;
        }
        return true;
    }
    private void animateShake(View view) {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(5));
        view.startAnimation(shake);
    }

    private void resetCardColors() {
        for (int i = 0; i < gridView.getChildCount(); i++) {
            View child = gridView.getChildAt(i);
            if (child instanceof TextView) {
                child.setBackgroundColor(Color.parseColor("#004D99")); // Trở về màu gốc
            }
        }
    }

    private boolean isMatchingPair(String card1, String card2) {
        for (int i = 0; i < names.size(); i++) {
            if ((card1.equals(names.get(i)) && card2.equals(descriptions.get(i))) ||
                    (card2.equals(names.get(i)) && card1.equals(descriptions.get(i)))) {
                return true;
            }
        }
        return false;
    }
    private void showPointChange(String text, int color) {
        tvPointChange.setText(text);
        tvPointChange.setTextColor(color);
        tvPointChange.setVisibility(View.VISIBLE);

        // Hiệu ứng di chuyển lên
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvPointChange, "translationY", 0, -50);
        animator.setDuration(800);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tvPointChange.setVisibility(View.GONE);
                tvPointChange.setTranslationY(0);
            }
        });
        animator.start();
    }

    private void showGameOverDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Hết thời gian! Điểm của bạn: " + score)
                .setPositiveButton("Chơi lại", (dialog, which) -> restartGame())
                .setNegativeButton("Thoát", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void showWinDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("Bạn đã hoàn thành! Điểm của bạn: " + score)
                .setPositiveButton("Chơi lại", (dialog, which) -> restartGame())
                .setNegativeButton("Thoát", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void restartGame() {
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        intent.putStringArrayListExtra("names", (ArrayList<String>) names);
        intent.putStringArrayListExtra("descriptions", (ArrayList<String>) descriptions);
        intent.putExtra("folderId", folderId);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel(); // Dừng bộ đếm thời gian
        }
    }

}
