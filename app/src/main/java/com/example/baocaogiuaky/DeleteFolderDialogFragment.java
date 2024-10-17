package com.example.baocaogiuaky;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteFolderDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Gán layout cho dialog
        View view = inflater.inflate(R.layout.fragment_delete_folder_dialog, container, false);

        // Thêm xử lý sự kiện cho nút 'Hủy'
        Button cancelButton = view.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(v -> {
            // Đóng dialog khi nhấn nút Hủy
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Đảm bảo rằng dialog xuất hiện ở trung tâm và có kích thước phù hợp
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setGravity(android.view.Gravity.CENTER);
        }
    }
}
