package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class PersonalFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        Button btn_change_pass = view.findViewById(R.id.btn_change_pass);
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        // Tìm ImageView với id ic_camera_profile
        ImageView cameraIcon = view.findViewById(R.id.ic_camera_profile);

        // Đặt sự kiện khi nhấn vào icon camera
        cameraIcon.setOnClickListener(v -> {
            // Tạo một instance của ChangeAccountImageFragment
            ChangeAccountImageFragment dialogFragment = new ChangeAccountImageFragment();

            // Hiển thị dialog
            dialogFragment.show(getParentFragmentManager(), "ChangeAccountImageFragment");
        });

        return view;
    }
}