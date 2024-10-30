package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class CreateFolderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_folder);

        ImageView imageView = findViewById(R.id.ic_return_create);
        Button saveButton = findViewById(R.id.saveButton);
        EditText folderNameEditText = findViewById(R.id.editTextFolderName);

        imageView.setOnClickListener(v -> finish());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFolderName = folderNameEditText.getText().toString().trim();
                if (!newFolderName.isEmpty()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newFolderName", newFolderName); // Sửa lại key thành "newFolderName"
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }
}
