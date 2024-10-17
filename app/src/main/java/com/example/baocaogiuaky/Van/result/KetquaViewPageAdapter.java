package com.example.baocaogiuaky.Van.result;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class KetquaViewPageAdapter extends FragmentStateAdapter{
    public KetquaViewPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TNketquaTatcaFragment();
            case 1:
                return new TNKetquaDungFragment();
            case 2:
                return new TNKetquaSaiFragment();
            default:
                return new TNketquaTatcaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}