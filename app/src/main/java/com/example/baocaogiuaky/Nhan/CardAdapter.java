package com.example.baocaogiuaky.Nhan;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private Context context;
    private List<String> cards;

    public CardAdapter(Context context, List<String> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(context);
            textView.setLayoutParams(new GridView.LayoutParams(
                    GridView.LayoutParams.MATCH_PARENT, 300));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(8, 8, 8, 8);
            textView.setTextSize(24); // Tăng kích thước chữ
             textView.setTextColor(Color.WHITE); // Đặt màu chữ trắng
             textView.setBackgroundColor(Color.parseColor("#004D99"));
        } else {
            textView = (TextView) convertView;
        }

        String card = cards.get(position);
        if (card.isEmpty()) {
            textView.setVisibility(View.INVISIBLE); // Ẩn thẻ nếu nội dung trống
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(card);
        }
        return textView;
    }
}
