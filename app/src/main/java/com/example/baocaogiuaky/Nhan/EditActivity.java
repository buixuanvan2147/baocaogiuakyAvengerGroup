package com.example.baocaogiuaky.Nhan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.baocaogiuaky.R;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Button openCameraButton;
    private boolean isEditingWord = false; 
    private boolean isEditingMeaning = false;
    private boolean isEditingPronunciation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_flashcards);
        Button btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
        Button btnBack = findViewById(R.id.button_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
        imageView = findViewById(R.id.imageView); 
        openCameraButton = findViewById(R.id.open_camera);

        
        openCameraButton.setOnClickListener(v -> openGallery());
        EditText inputWord = findViewById(R.id.input_word);
        Button buttonEditWord = findViewById(R.id.button_edit);

        
        EditText inputMeaning = findViewById(R.id.input_meaning);
        Button buttonEditMeaning = findViewById(R.id.button_edit1);

        
        inputWord.setEnabled(false);
        inputMeaning.setEnabled(false);


        
        buttonEditWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditingWord) {
                    
                    inputWord.setEnabled(false); 
                    buttonEditWord.setBackground(ContextCompat.getDrawable(EditActivity.this, R.drawable.icon_edit)); 
                } else {
                    
                    inputWord.setEnabled(true); 
                    buttonEditWord.setBackground(ContextCompat.getDrawable(EditActivity.this, R.drawable.icon_cancel)); 
                }
                isEditingWord = !isEditingWord; 
            }
        });

        
        buttonEditMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditingMeaning) {
                    inputMeaning.setEnabled(false);
                    buttonEditMeaning.setBackground(ContextCompat.getDrawable(EditActivity.this, R.drawable.icon_edit));
                } else {
                    inputMeaning.setEnabled(true);
                    buttonEditMeaning.setBackground(ContextCompat.getDrawable(EditActivity.this, R.drawable.icon_cancel));
                }
                isEditingMeaning = !isEditingMeaning;
            }
        });

        

    }

    private void openGallery() {
        
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}