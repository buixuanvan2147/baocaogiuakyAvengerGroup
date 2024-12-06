package com.example.baocaogiuaky.Nhan;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.baocaogiuaky.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Button openCameraButton;
    private boolean isEditingWord = false;
    private boolean isEditingMeaning = false;
    private String imagePath = null;

    
    private String originalName;
    private String originalTranslation;
    private String originalImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_flashcards);

        Button btnBack = findViewById(R.id.button_huy);
        imageView = findViewById(R.id.imageView);
        openCameraButton = findViewById(R.id.open_camera);

        EditText inputWord = findViewById(R.id.input_word);
        Button buttonEditWord = findViewById(R.id.button_edit);

        EditText inputMeaning = findViewById(R.id.input_meaning);
        Button buttonEditMeaning = findViewById(R.id.button_edit1);

        
        String name = getIntent().getStringExtra("name");
        String translation = getIntent().getStringExtra("translation");
        imagePath = getIntent().getStringExtra("imagePath");

        
        originalName = name;
        originalTranslation = translation;
        originalImagePath = imagePath;

        inputWord.setText(name);
        inputMeaning.setText(translation);

        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.purple_search_bkg); 
            }
        }

        inputWord.setEnabled(false);
        inputMeaning.setEnabled(false);

        
        Button btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(v -> {
            String updatedName = inputWord.getText().toString();
            String updatedTranslation = inputMeaning.getText().toString();

            
            if (updatedName.isEmpty() || updatedTranslation.isEmpty()) {
                Toast.makeText(EditActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", updatedName);
            resultIntent.putExtra("translation", updatedTranslation);
            resultIntent.putExtra("imagePath", imagePath);

            
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String folderId = getIntent().getStringExtra("folderId");
            String cardId = getIntent().getStringExtra("cardId");

            if (userId != null && folderId != null && cardId != null) {
                DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("users")
                        .child(userId)
                        .child("folders")
                        .child(folderId)
                        .child("cards")
                        .child(cardId);

                Map<String, Object> update = new HashMap<>();
                update.put("name", updatedName);
                update.put("description", updatedTranslation);
                update.put("imagePath", imagePath);

                cardRef.updateChildren(update)
                        .addOnSuccessListener(aVoid -> {
                            setResult(RESULT_OK, resultIntent);
                            Toast.makeText(EditActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            e.printStackTrace();
                            Toast.makeText(EditActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        openCameraButton.setOnClickListener(v -> openGallery());
        
        btnBack.setOnClickListener(v -> {
            
            inputWord.setText(originalName);
            inputMeaning.setText(originalTranslation);

            if (originalImagePath != null && !originalImagePath.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(originalImagePath);
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(R.drawable.purple_search_bkg);
                }
            } else {
                imageView.setImageResource(R.drawable.purple_search_bkg);
            }

            
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", originalName);
            resultIntent.putExtra("translation", originalTranslation);
            resultIntent.putExtra("imagePath", originalImagePath);
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });

        
        buttonEditWord.setOnClickListener(v -> toggleEditing(inputWord, buttonEditWord));
        buttonEditMeaning.setOnClickListener(v -> toggleEditing(inputMeaning, buttonEditMeaning));
    }

    private void toggleEditing(EditText editText, Button button) {
        if (editText.isEnabled()) {
            editText.setEnabled(false);
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_edit));
        } else {
            editText.setEnabled(true);
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_cancel));
        }
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
                imagePath = saveImageToInternalStorage(bitmap, "image_" + UUID.randomUUID().toString() + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String saveImageToInternalStorage(Bitmap bitmap, String fileName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath() + "/" + fileName;
    }
}

