package com.example.baocaogiuaky.Van;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Van.result.TNKetquaFragment;

public class Choice3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice3);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice3Activity.this , ShowFlashcardActivity.class);
                startActivity(intent);
            }
        });
        Button tracnghiem1_previous = findViewById(R.id.tracnghiem1_previous);
        tracnghiem1_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice3Activity.this , Choice2Activity.class);
                startActivity(intent);
            }
        });

        Button end = findViewById(R.id.tracnghiem1_end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new TNKetquaFragment());
            }
        });

    }
    // Phương thức thay thế Fragment
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.c3fragment_container, fragment); // Chèn Fragment vào FrameLayout với id fragment_container
        fragmentTransaction.addToBackStack(null); // Để quay lại nếu cần
        fragmentTransaction.commit();
    }
}