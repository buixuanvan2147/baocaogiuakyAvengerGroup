package com.example.baocaogiuaky.Nhan;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Button openCameraButton;
    private boolean isEditingWord = false;
    private boolean isEditingMeaning = false;
    private String imageBase64 = null;

    // Biến lưu trạng thái ban đầu
    private String originalName;
    private String originalTranslation;
    private String originalImageBase64;

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

        // Lấy dữ liệu từ Intent
        String name = getIntent().getStringExtra("name");
        String translation = getIntent().getStringExtra("translation");
        imageBase64 = getIntent().getStringExtra("imageBase64");

        // Lưu trạng thái ban đầu
        originalName = name;
        originalTranslation = translation;
        originalImageBase64 = imageBase64;

        inputWord.setText(name);
        inputMeaning.setText(translation);

        if (imageBase64 != null && !imageBase64.isEmpty()) {
            byte[] decodedString = android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        } else {
            imageView.setImageResource(R.drawable.purple_search_bkg); // Hình mặc định nếu không có hình
        }

        inputWord.setEnabled(false);
        inputMeaning.setEnabled(false);

        // Nút Lưu
        Button btnSave = findViewById(R.id.button_save);
        btnSave.setOnClickListener(v -> {
            String updatedName = inputWord.getText().toString();
            String updatedTranslation = inputMeaning.getText().toString();

            // Kiểm tra các giá trị nhập liệu
            if (updatedName.isEmpty() || updatedTranslation.isEmpty()) {
                Toast.makeText(EditActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo Intent để trả kết quả về
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", updatedName);
            resultIntent.putExtra("translation", updatedTranslation);
            resultIntent.putExtra("imageBase64", imageBase64);

            // Cập nhật dữ liệu vào Firebase
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
                update.put("imageBase64", imageBase64);

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
        // Nút Hủy
        btnBack.setOnClickListener(v -> {
            // Khôi phục dữ liệu ban đầu
            inputWord.setText(originalName);
            inputMeaning.setText(originalTranslation);

            if (originalImageBase64 != null && !originalImageBase64.isEmpty()) {
                byte[] decodedString = android.util.Base64.decode(originalImageBase64, android.util.Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            } else {
                imageView.setImageResource(R.drawable.purple_search_bkg);
            }

            // Trả kết quả gốc về DetailActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", originalName);
            resultIntent.putExtra("translation", originalTranslation);
            resultIntent.putExtra("imageBase64", originalImageBase64);
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });

        // Các xử lý khác (như chỉnh sửa từ/ý nghĩa) không thay đổi
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
                imageBase64 = bitmapToBase64(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
    }
}
