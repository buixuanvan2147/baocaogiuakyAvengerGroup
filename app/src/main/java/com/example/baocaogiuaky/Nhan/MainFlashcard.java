package com.example.baocaogiuaky.Nhan;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.baocaogiuaky.HomeActivity;
import com.example.baocaogiuaky.R;
import com.example.baocaogiuaky.Van.Choice0Activity;
import com.example.baocaogiuaky.Van.Silide1Activity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.opencsv.CSVReader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFlashcard extends AppCompatActivity {
    ViewPager viewPager;
    DotsIndicator dot1;
    ViewAdapter viewAdapter;
    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Flashcard1> originalList;
    private Button buttonSort;
    private String currentSortOrder = "Thứ tự gốc";
    private TextView textViewOriginalOrder, textViewAlphabeticalOrder;
    private static final int REQUEST_CODE_IMPORT_CSV = 1;
    private List<Flashcard1> flashcardList;
    private static final int EDIT_REQUEST_CODE = 2;
    private static final int DELETE_REQUEST_CODE = 3;
    private DatabaseReference databaseReference;
    private String folderId;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashcard_item);
        folderId = getIntent().getStringExtra("folderId");
        if (folderId == null || folderId.isEmpty()) {
            throw new IllegalArgumentException("Folder ID is required");
        }
        viewPager = findViewById(R.id.viewPager);
        dot1 = findViewById(R.id.dot1);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
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
            if (flashcardList.isEmpty()) {
                adapter.loadAnimalsFromFirebase(databaseReference, () -> {
                    
                    Log.d("MainFlashcard", "FlashcardList after load: " + flashcardList);
                    createWritingTests();
                });
            } else {
                createWritingTests();
            }
        });

        Button btngame = findViewById(R.id.trochoi);
        btngame.setOnClickListener(v -> {
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> descriptions = new ArrayList<>();

            for (Flashcard1 flashcard : flashcardList) {
                names.add(flashcard.getName());
                descriptions.add(flashcard.getDescription());
            }

            Intent intent = new Intent(MainFlashcard.this, GameActivity.class);
            intent.putStringArrayListExtra("names", names);
            intent.putStringArrayListExtra("descriptions", descriptions);
            intent.putExtra("folderId", folderId);
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

        int margin = 16;
        recyclerView.addItemDecoration(new MarginItemDecoration(margin));

        flashcardList = new ArrayList<>();
        originalList = new ArrayList<>(flashcardList);

        adapter = new AnimalAdapter(this,flashcardList, folderId);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("folders")
                .child(folderId)
                .child("cards");
        adapter.loadAnimalsFromFirebase(databaseReference, () -> {
            originalList.addAll(flashcardList);
            viewAdapter.loadFlashcardsFromFirebase(databaseReference);
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.loadAnimalsFromFirebase(databaseReference, () -> {
                viewAdapter.loadFlashcardsFromFirebase(databaseReference);
                viewPager.setAdapter(viewAdapter);
                dot1.setViewPager(viewPager);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            });
        });
    }


    private void createWritingTests() {
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<String> imagePaths = new ArrayList<>(); 

        for (int i = 0; i < flashcardList.size(); i++) {
            Flashcard1 flashcard = flashcardList.get(i);
            questions.add(flashcard.getDescription());
            answers.add(flashcard.getName());
            imagePaths.add(flashcard.getImagePath()); 
        }

        Intent intent = new Intent(MainFlashcard.this, CheckWriting1.class);
        intent.putStringArrayListExtra("questions", questions);
        intent.putStringArrayListExtra("answers", answers);
        intent.putStringArrayListExtra("imagePaths", imagePaths); 
        intent.putExtra("totalQuestions", flashcardList.size());
        intent.putExtra("currentQuestionIndex", 1);
        intent.putExtra("folderId", folderId);
        startActivity(intent);
    }


    private String saveImageToInternalStorage(Bitmap bitmap, String fileName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath() + "/" + fileName;
    }

    private Bitmap decodeBase64ToBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }


    private void refreshData() {

        adapter.updateAnimals(flashcardList);
        adapter.notifyDataSetChanged();


        viewAdapter.updateFlashcards(flashcardList);
        viewAdapter.notifyDataSetChanged();


        dot1.setViewPager(viewPager);

        Log.d("RefreshData", "Dữ liệu được làm mới và giao diện được cập nhật.");
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
            flashcardList = new ArrayList<>(originalList);
            flashcardList.sort((a, b) -> a.getName().compareTo(b.getName()));
        } else {

            flashcardList = new ArrayList<>(originalList);
        }


        adapter.updateAnimals(flashcardList);
        viewAdapter.notifyDataSetChanged();
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
            startActivityForResult(intent, 1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_IMPORT_CSV && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                importCSV(uri);
            }
        }


        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String newWord = data.getStringExtra("NEW_WORD");
            String newMeaning = data.getStringExtra("NEW_MEANING");
            String newImageBase64 = data.getStringExtra("NEW_IMAGE_BASE64");
            String newSoundUrl = data.getStringExtra("NEW_SOUND_URL");

            if (newWord != null && newMeaning != null && newImageBase64 != null && newSoundUrl != null) {

                Flashcard1 newFlashcard = new Flashcard1(newWord, newMeaning, newImageBase64, newSoundUrl);
                flashcardList.add(newFlashcard);
                originalList.add(newFlashcard);

                saveFlashcardsToFirebase(Collections.singletonList(newFlashcard));
                refreshData();
            }
        }
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String updatedName = data.getStringExtra("name");
            String updatedTranslation = data.getStringExtra("translation");
            String updatedImageBase64 = data.getStringExtra("imageBase64");

            for (Flashcard1 flashcard : flashcardList) {
                if (flashcard.getName().equals(updatedName)) {
                    flashcard.setName(updatedName);
                    flashcard.setDescription(updatedTranslation);
                    flashcard.setImagePath(updatedImageBase64);
                    break;
                }
            }

            for (Flashcard1 flashcard : originalList) {
                if (flashcard.getName().equals(updatedName)) {
                    flashcard.setName(updatedName);
                    flashcard.setDescription(updatedTranslation);
                    flashcard.setImagePath(updatedImageBase64);
                    break;
                }
            }

            refreshData();
        }
        if (requestCode == DELETE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (data.getBooleanExtra("delete", false)) {
                String name = data.getStringExtra("name");

                flashcardList.removeIf(flashcard -> flashcard.getName().equals(name));
                originalList.removeIf(flashcard -> flashcard.getName().equals(name));

                refreshData();
            }
        }
    }

    private void importCSV(Uri uri) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());

        int lineCount = countLines(uri);
        if (lineCount == 0) {
            Toast.makeText(this, "The CSV file is empty or unreadable", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                List<Flashcard1> flashcards = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length == 4) {
                        String name = fields[0];
                        String description = fields[1];
                        String imageUrl = fields[2];
                        String soundUrl = fields[3];


                        mainHandler.post(() -> {
                            loadBitmapFromUrl(imageUrl, bitmap -> {
                                String imagePath = null;
                                if (bitmap != null) {
                                    imagePath = saveImageToInternalStorage(bitmap, "image_" + UUID.randomUUID().toString() + ".png");
                                }

                                Flashcard1 flashcard = new Flashcard1(name, description, imagePath, soundUrl);
                                flashcards.add(flashcard);


                                if (flashcards.size() == lineCount) {
                                    mainHandler.post(() -> {
                                        saveFlashcardsToFirebase(flashcards);


                                        viewAdapter.updateFlashcards(flashcards);
                                        viewPager.setAdapter(viewAdapter);
                                        dot1.setViewPager(viewPager);
                                        adapter = new AnimalAdapter(MainFlashcard.this, flashcardList, folderId);
                                        recyclerView.setAdapter(adapter);
                                        Toast.makeText(MainFlashcard.this, "Import completed successfully", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            });
                        });
                    }
                }

                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
                mainHandler.post(() -> {
                    Toast.makeText(MainFlashcard.this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


    private int countLines(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void loadBitmapFromUrl(String url, OnBitmapLoadedListener listener) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            Picasso.get().load(url).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    listener.onBitmapLoaded(bitmap);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    listener.onBitmapLoaded(null);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        });
    }

    interface OnBitmapLoadedListener {
        void onBitmapLoaded(Bitmap bitmap);
    }



    private void saveFlashcardsToFirebase(List<Flashcard1> flashcards) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {

            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flashcardsRef = database.getReference("users")
                .child(userId)
                .child("folders").child("folderId1").child("cards");


        for (Flashcard1 flashcard : flashcards) {
            String flashcardId = flashcardsRef.push().getKey();
            if (flashcardId != null) {
                Map<String, Object> flashcardData = new HashMap<>();
                flashcardData.put("name", flashcard.getName());
                flashcardData.put("description", flashcard.getDescription());
                flashcardData.put("imagePath", flashcard.getImagePath());
                flashcardData.put("soundUrl", flashcard.getSoundUrl());


                flashcardsRef.child(flashcardId).setValue(flashcardData)
                        .addOnSuccessListener(aVoid -> {
                            refreshData();
                            Log.d("Firebase", "Flashcard saved successfully");
                        })
                        .addOnFailureListener(e -> {
                            Log.w("Firebase", "Error saving flashcard", e);
                        });
            }
        }
    }



    private void exportToCSV() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy UID người dùng hiện tại
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("folders")
                .child(folderId)
                .child("cards");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder csvContent = new StringBuilder("Term,Definition,ImagePath\n");

                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    String term = cardSnapshot.child("name").getValue(String.class);
                    String definition = cardSnapshot.child("description").getValue(String.class);
                    String imagePath = cardSnapshot.child("imagePath").getValue(String.class);

                    csvContent.append(term).append(",")
                            .append(definition).append(",")
                            .append(imagePath).append("\n");
                }

                if (csvContent.length() == "Term,Definition,ImagePath\n".length()) {
                    Toast.makeText(MainFlashcard.this, "No data found in Realtime Database", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Uri uri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, "flashcards.csv");
                        contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                        contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/Flashcards");

                        Uri fileUri = getContentResolver().insert(uri, contentValues);
                        if (fileUri != null) {
                            try (OutputStream outputStream = getContentResolver().openOutputStream(fileUri)) {
                                outputStream.write(csvContent.toString().getBytes());
                                Toast.makeText(MainFlashcard.this, "Exported to Downloads/Flashcards", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainFlashcard.this, "Failed to export CSV", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (ContextCompat.checkSelfPermission(MainFlashcard.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainFlashcard.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            return;
                        }

                        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Flashcards");
                        if (!exportDir.exists()) {
                            exportDir.mkdirs();
                        }
                        File file = new File(exportDir, "flashcards.csv");
                        try (FileWriter writer = new FileWriter(file)) {
                            writer.write(csvContent.toString());
                            Toast.makeText(MainFlashcard.this, "Exported to " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainFlashcard.this, "Error saving CSV file", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RealtimeDatabaseError", "Failed to load cards data from Realtime Database", databaseError.toException());
                Toast.makeText(MainFlashcard.this, "Failed to load cards data from Realtime Database", Toast.LENGTH_SHORT).show();
            }
        });
    }




}


