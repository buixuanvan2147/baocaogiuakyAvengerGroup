package com.example.baocaogiuaky.Nhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.baocaogiuaky.R;

import java.util.List;

public class ViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Flashcard1> flashcardList;
    public ViewAdapter(Context context, List<Flashcard1> flashcardList) {
        this.context = context;
        this.flashcardList = flashcardList;
    }

    @Override
    public int getCount() {
        return flashcardList.size();  // Số lượng trang dựa trên danh sách flashcards
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void addFlashcard(Flashcard1 flashcard) {
        flashcardList.add(flashcard);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Lấy flashcard từ danh sách dựa trên vị trí
        Flashcard1 flashcard = flashcardList.get(position);

        // Chọn layout cho từng flashcard (hoặc giữ layout chung nếu cần)
        int layoutResId = R.layout.flashcard_item1;  // Đảm bảo layout này có các views cần thiết
        View view = layoutInflater.inflate(layoutResId, container, false);

        // Tìm front và back layout trong trang hiện tại
        LinearLayout frontCard = view.findViewById(R.id.new_front_card);
        LinearLayout backCard = view.findViewById(R.id.new_back_card);

        // Đặt dữ liệu vào front và back card
        TextView nameTextView = view.findViewById(R.id.show_word);
        TextView descriptionTextView = view.findViewById(R.id.show_meaning);
        ImageView flashcardImageView = view.findViewById(R.id.imageView2);

        nameTextView.setText(flashcard.getName());
        descriptionTextView.setText(flashcard.getDescription());
        flashcardImageView.setImageBitmap(flashcard.getImage());

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
