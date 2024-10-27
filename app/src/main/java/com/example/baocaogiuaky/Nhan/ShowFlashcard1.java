package com.example.baocaogiuaky.Nhan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

public class ShowFlashcard1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_flashcardcat);
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        
        super.onBackPressed();  
    }
}
