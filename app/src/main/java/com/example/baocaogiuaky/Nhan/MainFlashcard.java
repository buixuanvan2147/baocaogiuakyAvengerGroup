package com.example.baocaogiuaky.Nhan;





import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Van.Choice0Activity;
import com.example.baocaogiuaky.Van.Silide1Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.opencsv.CSVReader;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainFlashcard extends AppCompatActivity {
    ViewPager viewPager;
    DotsIndicator dot1;
    ViewAdapter viewAdapter;
    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Animal> animalList;
    private List<Animal> originalList;
    private Button buttonSort;
    private String currentSortOrder = "Thứ tự gốc";
    private TextView textViewOriginalOrder, textViewAlphabeticalOrder;
    private static final int REQUEST_CODE_IMPORT_CSV = 1;
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
            Intent intent = new Intent(MainFlashcard.this, Silide1Activity.class);
            startActivity(intent);
        });

        
        Button kiemtra = findViewById(R.id.kiemtra);
        kiemtra.setOnClickListener(v -> {
            Intent intent = new Intent(MainFlashcard.this, CheckWriting1.class);
            startActivity(intent);
        });

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainFlashcard.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        Button hoc = findViewById(R.id.hoc);
        hoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainFlashcard.this, Choice0Activity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        animalList = new ArrayList<>();

        // Thiết lập khoảng cách giữa các item bằng MarginItemDecoration
        int margin = 16; // khoảng cách giữa các item (đơn vị là pixel)
        recyclerView.addItemDecoration(new MarginItemDecoration(margin));

        animalList.add(new Animal("Cow", "Con bò", R.raw.cow));
        animalList.add(new Animal("Cat", "Con mèo", R.raw.cat));


        originalList = new ArrayList<>(animalList);

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
            
            animalList.sort((a, b) -> originalList.indexOf(a) - originalList.indexOf(b));
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
        LinearLayout layoutlive = bottomSheetView.findViewById(R.id.layoutLive);
        layoutlive.setOnClickListener(v -> openFileChooser());
        LinearLayout layoutvideo = bottomSheetView.findViewById(R.id.layoutVideo);
        layoutvideo.setOnClickListener(v -> exportToCSV());
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
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");
        startActivityForResult(intent, REQUEST_CODE_IMPORT_CSV);
    }
    private void updateOriginalList() {
        originalList.clear();
        originalList.addAll(animalList); 
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMPORT_CSV && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                importCSV(uri);
            }
        }
    }

    
    private void importCSV(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(","); 
                if (fields.length >= 3) { 
                    String term = fields[0].trim();
                    String definition = fields[1].trim();
                    String audioFile = fields[2].trim();

                    int audioResId = getResources().getIdentifier(audioFile, "raw", getPackageName());

                    Animal animal = new Animal(term, definition, audioResId);
                    animalList.add(animal);
                }
            }

            adapter.notifyDataSetChanged();
            updateOriginalList();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV", Toast.LENGTH_SHORT).show();
        }
    }
    private void exportToCSV() {
        
        StringBuilder csvContent = new StringBuilder("Term,Definition,AudioFile\n");

        
        for (Animal animal : animalList) {
            csvContent.append(animal.getName()).append(",")
                    .append(animal.getTranslation()).append(",")
                    .append(getResources().getResourceEntryName(animal.getSoundResource()))
                    .append("\n");
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { 
                
                Uri uri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Downloads.DISPLAY_NAME, "animal_flashcards.csv");
                contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Flashcards");

                Uri fileUri = getContentResolver().insert(uri, contentValues);
                if (fileUri != null) {
                    try (OutputStream outputStream = getContentResolver().openOutputStream(fileUri)) {
                        outputStream.write(csvContent.toString().getBytes());
                        Toast.makeText(this, "Exported to Downloads/Flashcards", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Failed to export CSV", Toast.LENGTH_SHORT).show();
                }
            } else { 
                
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return;
                }

                
                File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Flashcards");
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }
                File file = new File(exportDir, "animal_flashcards.csv");
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(csvContent.toString());
                    Toast.makeText(this, "Exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving CSV file", Toast.LENGTH_SHORT).show();
        }
    }


}


