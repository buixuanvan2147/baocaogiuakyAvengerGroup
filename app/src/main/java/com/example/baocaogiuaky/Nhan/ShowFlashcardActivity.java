package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.EditFolderActivity;
import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Van.Choice0Activity;
import com.example.baocaogiuaky.Van.Silide1Activity;

import java.util.ArrayList;
import java.util.List;

public class ShowFlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_flashcard);


        ImageView icon_menu = findViewById(R.id.icon_three_menu);
        icon_menu.setOnClickListener(v -> startActivity(new Intent(ShowFlashcardActivity.this, EditFolderActivity.class)));

        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(v -> startActivity(new Intent(ShowFlashcardActivity.this, HomeActivity.class)));

        Button btn_KTV = findViewById(R.id.btn_KTV);
        btn_KTV.setOnClickListener(v -> startActivity(new Intent(ShowFlashcardActivity.this, CheckWritting1Activity.class)));

        Button btn_show_card = findViewById(R.id.btn_show_card);
        btn_show_card.setOnClickListener(v -> startActivity(new Intent(ShowFlashcardActivity.this, Silide1Activity.class)));

        Button bnt_TN = findViewById(R.id.bnt_TN);
        bnt_TN.setOnClickListener(v -> startActivity(new Intent(ShowFlashcardActivity.this, Choice0Activity.class)));


    }}


