package com.example.baocaogiuaky.Trinhchieu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.baocaogiuaky.MainFragment;
import androidx.fragment.app.Fragment;

import com.example.baocaogiuaky.MainActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Trinhchieu.TrinhchieuConhoFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrinhchieuConmeoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrinhchieuConmeoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrinhchieuConmeoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrinhchieuConmeoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrinhchieuConmeoFragment newInstance(String param1, String param2) {
        TrinhchieuConmeoFragment fragment = new TrinhchieuConmeoFragment();
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
        View view = inflater.inflate(R.layout.fragment_trinhchieu_conmeo, container, false);

        // Khởi tạo các nút
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        ImageButton btnPrevious = view.findViewById(R.id.tc_btn_back);

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
                    activity.replaceFragment(new TrinhchieuConhoFragment());
                }
            }
        });

        return view; // Trả về view đã được inflate
    }
}