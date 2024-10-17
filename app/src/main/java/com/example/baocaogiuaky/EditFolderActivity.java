package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;

public class EditFolderActivity extends AppCompatActivity {

    private ImageView editIconwrite;
    private EditText editText;
    private ImageView iconreturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_folder);

        iconreturn = findViewById(R.id.icon_return);
        iconreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditFolderActivity.this, ShowFlashcardActivity.class);
                startActivity(intent);
            }
        });

        // Sau khi setContentView, gọi findViewById
        editIconwrite = findViewById(R.id.editIcon);
        editText = findViewById(R.id.txt_editfolder);

        // Điều chỉnh cho phù hợp với các insets của hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Xử lý sự kiện khi nhấn vào TextView có ID txt_deltefolder
        TextView txt_deltefolder = findViewById(R.id.txt_deltefolder);
        txt_deltefolder.setOnClickListener(v -> {
            // Hiển thị DeleteFolderDialogFragment dưới dạng dialog
            DeleteFolderDialogFragment dialogFragment = new DeleteFolderDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "DeleteFolderDialog");
        });

        // Sự kiện khi nhấn vào biểu tượng chỉnh sửa
        editIconwrite.setOnClickListener(v -> {
            editText.setEnabled(true);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        });
        // Lắng nghe sự kiện focus change của EditText
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Khi EditText mất focus, nó sẽ bị khóa lại
                editText.setEnabled(false);
                editText.clearFocus();
            }
        });
        editText.setEnabled(false);
    }
}
