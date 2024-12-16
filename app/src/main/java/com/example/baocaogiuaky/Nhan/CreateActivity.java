package com.example.baocaogiuaky.Nhan;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.baocaogiuaky.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import android.database.Cursor;
import android.provider.OpenableColumns;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private static final int PICK_MUSIC_REQUEST = 3; // Mã request
    private Uri musicUri; // Lưu đường dẫn nhạc
    private TextView textViewMusicName;
    private Button buttonAddMusic;

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
            String word = inputWord.getText().toString();
            String meaning = inputMeaning.getText().toString();

            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);

            saveFlashcardToDatabase(word, meaning, bitmap);
        });


        buttonCancel.setOnClickListener(v -> finish());

        inputWord.setEnabled(false);
        inputMeaning.setEnabled(false);

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
        textViewMusicName = findViewById(R.id.id_input_music);
        buttonAddMusic = findViewById(R.id.button_add_music);

        buttonAddMusic.setOnClickListener(v -> openMusicChooser());

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
    private void openMusicChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*"); // Only audio files
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_MUSIC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MUSIC_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            musicUri = data.getData();

            // Handle permission for reading external storage if needed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                // If permission is granted or the device version is lower, proceed
                String fileName = getFileName(musicUri);
                textViewMusicName.setText(fileName);
            }

        } else if (requestCode == CAMERA_CAPTURE_CODE && resultCode == RESULT_OK) {
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
        if (requestCode == 1) { // for external storage
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, handle accordingly
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) result = cursor.getString(index);
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private void saveFlashcardToDatabase(String word, String meaning, Bitmap bitmap) {
        String imagePath = saveImageToInternalStorage(bitmap, "image_" + UUID.randomUUID().toString() + ".png");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> flashcard = new HashMap<>();
        flashcard.put("name", word);
        flashcard.put("description", meaning);
        flashcard.put("imagePath", imagePath);
        if (musicUri != null) {
            flashcard.put("soundUrl", musicUri.toString());
        }

        DatabaseReference folderRef = usersRef.child(userId).child("folders").child("folderId1").child("cards");

        String cardId = folderRef.push().getKey();

        if (cardId != null) {
            folderRef.child(cardId).setValue(flashcard)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(CreateActivity.this, "Flashcard saved", Toast.LENGTH_SHORT).show();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("NEW_WORD", word);
                        resultIntent.putExtra("NEW_MEANING", meaning);
                        resultIntent.putExtra("NEW_IMAGE_PATH", imagePath);
                        if (musicUri != null) {
                            resultIntent.putExtra("NEW_MUSIC_URI", musicUri.toString());
                        }
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(CreateActivity.this, "Error saving flashcard", Toast.LENGTH_SHORT).show());
        }
    }




}
