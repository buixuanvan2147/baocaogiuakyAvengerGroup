package com.example.baocaogiuaky.Nhan;

import android.view.View;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
public class MarginItemDecoration extends RecyclerView.ItemDecoration{
    private final int margin;

    // Constructor để thiết lập khoảng cách
    public MarginItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Thiết lập khoảng cách cho các cạnh của item
        outRect.left = margin;
        outRect.right = margin;
        outRect.top = margin;
        outRect.bottom = margin;
    }
}
