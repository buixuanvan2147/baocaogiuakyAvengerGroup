package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.EditFolderActivity;
import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Van.Choice0Activity;
import com.example.baocaogiuaky.Van.Silide1Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowFlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_flashcard);

        FloatingActionButton button_add_card = findViewById(R.id.button_add_card);
        button_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this, CreateNewflashcardActivity.class);
                startActivity(intent);
            }
        });

        ImageView icon_menu = findViewById(R.id.icon_three_menu);
        icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , EditFolderActivity.class);
                startActivity(intent);
            }
        });

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , HomeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layout_cow = findViewById(R.id.layout_cow);
        layout_cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , ShowDetailCowActivity.class );
                startActivity(intent);
            }
        });
        LinearLayout layout_cat = findViewById(R.id.layout_cat);
        layout_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , ShowFlashcardcatActivity.class );
                startActivity(intent);
            }
        });

        Button btn_KTV = findViewById(R.id.btn_KTV);
        btn_KTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , CheckWritting1Activity.class);
                startActivity(intent);
            }
        });
        Button btn_show_card = findViewById(R.id.btn_show_card);
        btn_show_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , Silide1Activity.class);
                startActivity(intent);
            }
        });

        Button bnt_TN = findViewById(R.id.bnt_TN);
        bnt_TN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowFlashcardActivity.this , Choice0Activity.class);
                startActivity(intent);
            }
        });

    }
}