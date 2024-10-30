package com.example.baocaogiuaky.Nhan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_CODE = 101;
    ImageView imageView;
    Button buttonSave, buttonCancel, buttonOpenCamera;
    TextView textViewImage;
    private boolean isEditingWord = false;
    private boolean isEditingMeaning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_newflashcard);

        imageView = findViewById(R.id.image_placeholder);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        buttonOpenCamera = findViewById(R.id.open_camera);
        textViewImage = findViewById(R.id.textView);

        imageView.setOnClickListener(v -> openImageChooser());
        textViewImage.setOnClickListener(v -> openImageChooser());

        buttonOpenCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(CreateActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            } else {
                openCamera();
            }
        });
        EditText inputWord = findViewById(R.id.input_word);
        Button buttonEditWord = findViewById(R.id.button_edit);
        EditText inputMeaning = findViewById(R.id.input_meaning);
        Button buttonEditMeaning = findViewById(R.id.button_edit1);
        buttonSave.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            String word = inputWord.getText().toString();
            String meaning = inputMeaning.getText().toString();

            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            resultIntent.putExtra("NEW_WORD", word);
            resultIntent.putExtra("NEW_MEANING", meaning);
            resultIntent.putExtra("NEW_IMAGE", byteArray);

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> finish());



        
        inputWord.setEnabled(false);
        inputMeaning.setEnabled(false);
        ImageView iconCancelWord;
        iconCancelWord = findViewById(R.id.icon_cancel);
        
        buttonEditWord.setOnClickListener(v -> {
            isEditingWord = !isEditingWord;
            inputWord.setEnabled(isEditingWord);
            if (isEditingWord) {
                buttonEditWord.setBackground(ContextCompat.getDrawable(CreateActivity.this, R.drawable.icon_cancel));
            } else {
                buttonEditWord.setBackground(ContextCompat.getDrawable(CreateActivity.this, R.drawable.icon_edit));
            }
        });



        
        buttonEditMeaning.setOnClickListener(v -> {
            isEditingMeaning = !isEditingMeaning;
            inputMeaning.setEnabled(isEditingMeaning);
            if (isEditingMeaning) {
                buttonEditMeaning.setBackground(ContextCompat.getDrawable(CreateActivity.this, R.drawable.icon_cancel));
            } else {
                buttonEditMeaning.setBackground(ContextCompat.getDrawable(CreateActivity.this, R.drawable.icon_edit));
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_CAPTURE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }
}
