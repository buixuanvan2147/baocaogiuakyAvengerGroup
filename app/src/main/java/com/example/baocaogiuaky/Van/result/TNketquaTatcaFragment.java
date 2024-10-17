package com.example.baocaogiuaky.Van.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Van.Choice1Activity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TNketquaTatcaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TNketquaTatcaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TNketquaTatcaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TNketquaTatcaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TNketquaTatcaFragment newInstance(String param1, String param2) {
        TNketquaTatcaFragment fragment = new TNketquaTatcaFragment();
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
        View view = inflater.inflate(R.layout.fragment_t_nketqua_tatca, container, false);

        // Khởi tạo nút
        Button btnChoiLai = view.findViewById(R.id.tn_kq_choilai);
        Button btnThoat = view.findViewById(R.id.tn_kq_thoat);

        // Thiết lập sự kiện click cho nút "Chơi lại"
        btnChoiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Choice1Activity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện click cho nút "Thoát"
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về trang chính
                Intent intent = new Intent(getActivity(), ShowFlashcardActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}