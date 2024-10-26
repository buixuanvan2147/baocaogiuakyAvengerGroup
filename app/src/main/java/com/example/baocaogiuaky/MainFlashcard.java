package com.example.baocaogiuaky;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class MainFlashcard extends AppCompatActivity {
    ViewPager viewPager;
    DotsIndicator dot1;
    ViewAdapter viewAdapter;
    private Button buttonSort;
    private String currentSortOrder = "Thứ tự gốc";
    private TextView textViewOriginalOrder, textViewAlphabeticalOrder;
    private LinearLayout layoutContainer;
    private LinearLayout layoutCat, layoutCow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_item);
        viewPager = findViewById(R.id.viewPager);
        dot1=findViewById(R.id.dot1);
        layoutContainer = findViewById(R.id.layoutContainer);
        layoutCat = findViewById(R.id.layout_cat);
        layoutCow = findViewById(R.id.layout_cow);
        viewAdapter = new ViewAdapter(this);
        viewPager.setAdapter(viewAdapter);
        dot1.setViewPager(viewPager);
        Button buttonSound1 = findViewById(R.id.speaker);
        Button buttonSound2 = findViewById(R.id.speaker1);

        View.OnClickListener soundClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                MediaPlayer mediaPlayer;
                if (view.getId() == R.id.speaker) {
                    mediaPlayer = MediaPlayer.create(MainFlashcard.this, R.raw.cow); 
                } else {
                    mediaPlayer = MediaPlayer.create(MainFlashcard.this, R.raw.cat); 
                }
                mediaPlayer.start();

                
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            }
        };

        buttonSound1.setOnClickListener(soundClickListener);
        buttonSound2.setOnClickListener(soundClickListener);
        buttonSort = findViewById(R.id.textView8); 

        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortOptions();
            }
        });

    }
    private void showSortOptions() {
        
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        textViewOriginalOrder = bottomSheetView.findViewById(R.id.textViewOriginalOrder);
        textViewAlphabeticalOrder = bottomSheetView.findViewById(R.id.textViewAlphabeticalOrder);

        
        updateTick();

        
        textViewOriginalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSortOrder = "Thứ tự gốc"; 
                updateTick();
                sortLayouts(false);
                bottomSheetDialog.dismiss(); 
            }
        });

        textViewAlphabeticalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSortOrder = "Bảng chữ cái"; 
                updateTick();
                sortLayouts(true); 
                bottomSheetDialog.dismiss(); 
            }
        });

        bottomSheetDialog.show(); 
    }
    private void sortLayouts(boolean alphabetical) {
        layoutContainer.removeAllViews(); 

        if (alphabetical) {
            
            layoutContainer.addView(layoutCat);
            layoutContainer.addView(layoutCow);
        } else {
            
            layoutContainer.addView(layoutCow);
            layoutContainer.addView(layoutCat);
        }
    }
    private void updateTick() {
        
        textViewOriginalOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        textViewAlphabeticalOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        
        if (currentSortOrder.equals("Thứ tự gốc")) {
            textViewOriginalOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_24, 0); 
        } else {
            textViewAlphabeticalOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_check_24, 0); 
        }

        
        buttonSort.setText(currentSortOrder);
    }

}
