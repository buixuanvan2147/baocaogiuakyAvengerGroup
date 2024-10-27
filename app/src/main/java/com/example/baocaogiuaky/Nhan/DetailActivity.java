package com.example.baocaogiuaky.Nhan;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baocaogiuaky.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_flashcards);
        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                View customDialogView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                builder.setView(customDialogView);

                AlertDialog alertDialog = builder.create();

                Button buttonDelete = customDialogView.findViewById(R.id.button_delete);
                Button buttonCancel = customDialogView.findViewById(R.id.button_cancel);

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailActivity.this, MainFlashcard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        alertDialog.dismiss();
                        finish();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();


            }
        });
        Button btnEdit = findViewById(R.id.button_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainFlashcard.class);
                startActivity(intent);
            }
        });
    }

}
