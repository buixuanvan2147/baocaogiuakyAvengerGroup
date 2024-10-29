package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baocaogiuaky.Nhan.MainFlashcard;

public class MainActivity extends AppCompatActivity {
    private MotionLayout motionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        motionLayout = findViewById(R.id.motionLayout);
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                // Navigate to LoginActivity once animation completes
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }

            // Other overridden methods can remain empty
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) { }
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) { }
            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) { }
        });
    }

}