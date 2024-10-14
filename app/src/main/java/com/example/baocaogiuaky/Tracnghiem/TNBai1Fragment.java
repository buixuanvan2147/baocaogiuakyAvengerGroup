package com.example.baocaogiuaky.Tracnghiem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.baocaogiuaky.MainFragment;
import androidx.fragment.app.Fragment;

import com.example.baocaogiuaky.MainActivity;
import com.example.baocaogiuaky.R;

public class TNBai1Fragment extends Fragment {

    public TNBai1Fragment() {
        // Cần constructor công khai rỗng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment
        View view = inflater.inflate(R.layout.fragment_t_n_bai1, container, false);

        // Khởi tạo các nút
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        Button btnNext = view.findViewById(R.id.tracnghiem1_next);

        // Thiết lập sự kiện cho nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về trang chính
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.replaceFragment(new MainFragment());
                }
            }
        });

        // Thiết lập sự kiện cho nút "Bài tiếp"
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang bài tiếp theo (ví dụ: TNBai2Fragment)
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.replaceFragment(new TNBai2Fragment());
                }
            }
        });

        return view; // Trả về view đã được inflate
    }
}