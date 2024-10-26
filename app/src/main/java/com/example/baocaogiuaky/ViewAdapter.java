package com.example.baocaogiuaky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.baocaogiuaky.R;

public class ViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public ViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2; // Số lượng trang
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Chọn layout cho từng trang
        int layoutResId = (position == 0) ? R.layout.flashcard_item_cow : R.layout.flashcard_item_cat; // layout1 và layout2 là hai layout chứa front và back
        View view = layoutInflater.inflate(layoutResId, container, false);

        // Tìm front và back layout trong trang hiện tại
        LinearLayout frontCard = view.findViewById(R.id.new_front_card);
        LinearLayout backCard = view.findViewById(R.id.new_back_card);

        // Đặt khoảng cách camera cho hiệu ứng lật
        frontCard.setCameraDistance(8000 * context.getResources().getDisplayMetrics().density);
        backCard.setCameraDistance(8000 * context.getResources().getDisplayMetrics().density);

        // Thiết lập sự kiện lật cho từng layout front và back
        setupFlipEvent(frontCard, backCard);

        // Thêm view vào ViewPager
        container.addView(view);
        return view;
    }

    private void setupFlipEvent(LinearLayout frontCard, LinearLayout backCard) {
        frontCard.setOnClickListener(v -> flipCard(frontCard, backCard));
        backCard.setOnClickListener(v -> flipCard(backCard, frontCard));
    }

    private void flipCard(LinearLayout front, LinearLayout back) {
        if (front.getVisibility() == View.VISIBLE) {
            front.animate().rotationX(90f).setDuration(250).withEndAction(() -> {
                front.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                back.setRotationX(-90f);
                back.animate().rotationX(0f).setDuration(250).start();
            }).start();
        } else if (back.getVisibility() == View.VISIBLE) {
            back.animate().rotationX(90f).setDuration(250).withEndAction(() -> {
                back.setVisibility(View.GONE);
                front.setVisibility(View.VISIBLE);
                front.setRotationX(-90f);
                front.animate().rotationX(0f).setDuration(250).start();
            }).start();
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
