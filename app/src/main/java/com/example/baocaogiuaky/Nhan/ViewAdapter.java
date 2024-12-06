package com.example.baocaogiuaky.Nhan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.baocaogiuaky.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Flashcard1> flashcardList;

    public ViewAdapter(Context context) {
        this.context = context;
        this.flashcardList = new ArrayList<>();
    }

    public void loadFlashcardsFromFirebase(DatabaseReference databaseReference) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Flashcard1> tempFlashcards = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flashcard1 flashcard = snapshot.getValue(Flashcard1.class);
                    if (flashcard != null) {
                        flashcard.setCardId(snapshot.getKey());
                        flashcard.setName(snapshot.child("name").getValue(String.class));
                        flashcard.setDescription(snapshot.child("description").getValue(String.class));
                        flashcard.setImagePath(snapshot.child("imagePath").getValue(String.class));
                        flashcard.setSoundUrl(snapshot.child("soundUrl").getValue(String.class));
                        tempFlashcards.add(flashcard);
                    }
                }
                updateFlashcards(tempFlashcards); 
                Log.d("ViewAdapter", "Flashcards loaded: " + tempFlashcards.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ViewAdapter", "Error loading data from Firebase", databaseError.toException());
            }
        });
    }

    @Override
    public int getCount() {
        return flashcardList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Flashcard1 flashcard = flashcardList.get(position);

        int layoutResId = R.layout.flashcard_item1;
        View view = layoutInflater.inflate(layoutResId, container, false);

        LinearLayout frontCard = view.findViewById(R.id.new_front_card);
        LinearLayout backCard = view.findViewById(R.id.new_back_card);

        TextView nameTextView = view.findViewById(R.id.show_word);
        TextView descriptionTextView = view.findViewById(R.id.show_meaning);
        ImageView flashcardImageView = view.findViewById(R.id.imageView2);

        nameTextView.setText(flashcard.getName());
        descriptionTextView.setText(flashcard.getDescription());

        
        if (flashcard.getImagePath() != null && !flashcard.getImagePath().isEmpty()) {
            flashcardImageView.setImageBitmap(BitmapFactory.decodeFile(flashcard.getImagePath()));
        }

        frontCard.setCameraDistance(8000 * context.getResources().getDisplayMetrics().density);
        backCard.setCameraDistance(8000 * context.getResources().getDisplayMetrics().density);

        setupFlipEvent(frontCard, backCard);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void updateFlashcards(List<Flashcard1> newFlashcards) {
        flashcardList.clear();
        flashcardList.addAll(newFlashcards);
        notifyDataSetChanged();
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
}
