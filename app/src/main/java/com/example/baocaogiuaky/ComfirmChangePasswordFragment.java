package com.example.baocaogiuaky;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;


public class ComfirmChangePasswordFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Gán layout cho dialog
        View view = inflater.inflate(R.layout.fragment_comfirm_change_password ,container, false);

        // Thêm xử lý sự kiện cho nút 'Hủy'
        Button cancelButton = view.findViewById(R.id.btn_cancel_change_pass);
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