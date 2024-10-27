package com.example.baocaogiuaky.Nhan;





import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.baocaogiuaky.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainFlashcard extends AppCompatActivity {
    ViewPager viewPager;
    DotsIndicator dot1;
    ViewAdapter viewAdapter;
    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Animal> animalList;
    private Button buttonSort;
    private String currentSortOrder = "Thứ tự gốc";
    private TextView textViewOriginalOrder, textViewAlphabeticalOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_item);

        
        viewPager = findViewById(R.id.viewPager);
        dot1 = findViewById(R.id.dot1);

        viewAdapter = new ViewAdapter(this);
        viewPager.setAdapter(viewAdapter);
        dot1.setViewPager(viewPager);

        
        buttonSort = findViewById(R.id.textView8);
        buttonSort.setOnClickListener(v -> showSortOptions());

        
        Button menuButton = findViewById(R.id.btnmenu);
        menuButton.setOnClickListener(v -> showBottomSheetDialog());

        
        Button btnSave = findViewById(R.id.ghinho);
        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(MainFlashcard.this, DetailActivity.class);
            startActivity(intent);
        });

        
        Button kiemtra = findViewById(R.id.kiemtra);
        kiemtra.setOnClickListener(v -> {
            Intent intent = new Intent(MainFlashcard.this, CheckWriting1.class);
            startActivity(intent);
        });

        
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        
        animalList = new ArrayList<>();
        animalList.add(new Animal("Cow", "Con bò", R.raw.cow));
        animalList.add(new Animal("Cat", "Con mèo", R.raw.cat));

        
        adapter = new AnimalAdapter(animalList, this);
        recyclerView.setAdapter(adapter);
    }

    private void showSortOptions() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        textViewOriginalOrder = bottomSheetView.findViewById(R.id.textViewOriginalOrder);
        textViewAlphabeticalOrder = bottomSheetView.findViewById(R.id.textViewAlphabeticalOrder);

        updateTick();

        textViewOriginalOrder.setOnClickListener(v -> {
            currentSortOrder = "Thứ tự gốc";
            updateTick();
            sortLayouts(false);
            bottomSheetDialog.dismiss();
        });

        textViewAlphabeticalOrder.setOnClickListener(v -> {
            currentSortOrder = "Bảng chữ cái";
            updateTick();
            sortLayouts(true);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void sortLayouts(boolean alphabetical) {
        if (alphabetical) {
            
            animalList.sort((a, b) -> a.getName().compareTo(b.getName()));
        } else {
            
            animalList.clear();
            animalList.add(new Animal("Cow", "Con bò", R.raw.cow));
            animalList.add(new Animal("Cat", "Con mèo", R.raw.cat));
        }
        
        adapter.notifyDataSetChanged();
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

    public void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainFlashcard.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottomfloat, findViewById(R.id.menu_root_container));
        bottomSheetDialog.setContentView(bottomSheetView);
        LinearLayout layoutAdd = bottomSheetView.findViewById(R.id.layoutAdd);
        layoutAdd.setOnClickListener(v -> {
            
            Intent intent = new Intent(MainFlashcard.this, CreateActivity.class);
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        bottomSheetView.setTranslationY(1000);
        bottomSheetView.setAlpha(0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(bottomSheetView, "translationY", 0f);
        animator.setDuration(500);
        ObjectAnimator fadeAnimator = ObjectAnimator.ofFloat(bottomSheetView, "alpha", 1f);
        fadeAnimator.setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, fadeAnimator);
        animatorSet.start();

        bottomSheetDialog.show();
    }
}
