package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.ComfirmChangePasswordFragment;
import com.example.baocaogiuaky.R;

public class ShowDetailCowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_detail_cow);
        ImageView img_return = findViewById(R.id.img_return);
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailCowActivity.this , ShowFlashcardActivity.class);
                startActivity(intent);
            }
        });
        TextView txt_repair = findViewById(R.id.txt_repair);
        txt_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailCowActivity.this , EditFlashcardActivity.class);
                startActivity(intent);
            }
        });

        TextView txt_cancel_card = findViewById(R.id.txt_cancel_card);
        txt_cancel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComfirmChangePasswordFragment dialogFragment = new ComfirmChangePasswordFragment();
                dialogFragment.show(getSupportFragmentManager(), "ConFirmDeleteCardFragment");
            }
        });
    }
}