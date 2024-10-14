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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TNBai2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TNBai2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TNBai2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TBBai2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TNBai2Fragment newInstance(String param1, String param2) {
        TNBai2Fragment fragment = new TNBai2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment
        View view = inflater.inflate(R.layout.fragment_t_n_bai2, container, false);

        // Khởi tạo các nút
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        Button btnPrevious = view.findViewById(R.id.tracnghiem2_previous);
        Button btnNext = view.findViewById(R.id.tracnghiem2_next);

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

        // Thiết lập sự kiện cho nút "tc_btn_back"
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.replaceFragment(new TNBai2Fragment());
                }
            }
        });

        // Thiết lập sự kiện cho nút "tc_btn_next"
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang bài tiếp theo (TrinhchieuConchoFragment)
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.replaceFragment(new TNBai3Fragment());
                }
            }
        });

        return view; // Trả về view đã được inflate
    }
}