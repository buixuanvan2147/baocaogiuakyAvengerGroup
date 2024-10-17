package com.example.baocaogiuaky.Van.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.baocaogiuaky.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TNKetquaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TNKetquaFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private View mView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TNKetquaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TNKetquaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TNKetquaFragment newInstance(String param1, String param2) {
        TNKetquaFragment fragment = new TNKetquaFragment();
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
        mView = inflater.inflate(R.layout.fragment_t_n_ketqua, container, false);

        tabLayout = mView.findViewById(R.id.ketquatablayout);
        viewPager = mView.findViewById(R.id.ketqua_viewpager);

        KetquaViewPageAdapter adapter = new KetquaViewPageAdapter(this);
        viewPager.setAdapter(adapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Tất cả");
                    break;
                case 1:
                    tab.setText("Đúng");
                    break;
                case 2:
                    tab.setText("Sai");
                    break;
            }
        }).attach();

        return mView;
    }
}