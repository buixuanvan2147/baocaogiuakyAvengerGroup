package com.example.baocaogiuaky.Tracnghiem.TracnghiemKetqua;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baocaogiuaky.MainActivity;
import com.example.baocaogiuaky.MainFragment;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Tracnghiem.TNBai1Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TNKetquaSaiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TNKetquaSaiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TNKetquaSaiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TNKetquaSaiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TNKetquaSaiFragment newInstance(String param1, String param2) {
        TNKetquaSaiFragment fragment = new TNKetquaSaiFragment();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_t_n_ketqua_sai, container, false);

        // Khởi tạo nút "Chơi lại" và "Thoát"
        Button btnChoiLai = view.findViewById(R.id.tn_kq_choilai);
        Button btnThoat = view.findViewById(R.id.tn_kq_thoat);

        // Sự kiện khi ấn nút "Chơi lại"
        btnChoiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    // Gọi phương thức 'replaceFragment' để chuyển về TNBai1Fragment
                    activity.replaceFragment(new TNBai1Fragment());
                }
            }
        });

        // Sự kiện khi ấn nút "Thoát"
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về trang chính
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.replaceFragment(new MainFragment());
                }
            }
        });

        return view;
    }
}