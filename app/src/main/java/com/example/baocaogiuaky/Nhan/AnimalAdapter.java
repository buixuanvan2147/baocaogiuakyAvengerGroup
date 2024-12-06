package com.example.baocaogiuaky.Nhan;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<Flashcard1> flashcardList;
    private Context context;
    private String folderId;
    public AnimalAdapter(Context context,List<Flashcard1> flashcardList, String folderId) {
        this.context = context;
        this.flashcardList =flashcardList;
        this.folderId = folderId;
    }

    // Cập nhật danh sách dữ liệu từ bên ngoài


    // Tải dữ liệu từ Firebase vào Adapter
    public void loadAnimalsFromFirebase(DatabaseReference databaseReference, Runnable onComplete) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Flashcard1> tempList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Flashcard1 flashcard = snapshot.getValue(Flashcard1.class);
                    if (flashcard != null) {
                        // Lưu cardId từ snapshot key vào đối tượng Flashcard1
                        flashcard.setCardId(snapshot.getKey());
                        flashcard.setName(snapshot.child("name").getValue(String.class));
                        flashcard.setDescription(snapshot.child("description").getValue(String.class));
                        flashcard.setImageBase64(snapshot.child("imageBase64").getValue(String.class));
                        flashcard.setSoundUrl(snapshot.child("soundUrl").getValue(String.class));

                        tempList.add(flashcard);
                    }
                }
                updateAnimals(tempList);
                Log.d("AnimalAdapter", "Flashcards loaded: " + tempList.size());
                if (onComplete != null) {
                    onComplete.run(); // Gọi callback sau khi tải xong
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    public void updateAnimals(List<Flashcard1> newAnimals) {
        flashcardList.clear();
        flashcardList.addAll(newAnimals);
        notifyDataSetChanged(); // Cập nhật giao diện
    }


    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Flashcard1 flashcard = flashcardList.get(position);
        holder.nameTextView.setText(flashcard.getName());
        holder.translationTextView.setText(flashcard.getDescription());

        // Phát âm thanh khi bấm nút
        holder.speakerButton.setOnClickListener(v -> playSound(flashcard.getSoundUrl()));

        // Xử lý sự kiện nhấp chuột
        holder.itemView.setOnClickListener(v -> {
            Log.d("Adapter", "Sending imageBase64: " + flashcard.getImageBase64());
            Intent intent = new Intent(context, DetailActivity.class);

            intent.putExtra("name", flashcard.getName());
            intent.putExtra("translation", flashcard.getDescription());
            intent.putExtra("imageBase64", flashcard.getImageBase64());
            intent.putExtra("soundUrl", flashcard.getSoundUrl());
            String cardId = flashcard.getCardId();  // Truyền cardId vào Intent

            intent.putExtra("cardId", cardId);
            intent.putExtra("folderId", folderId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return flashcardList.size();
    }

    private void playSound(String soundUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(soundUrl); // Đặt URL âm thanh
            mediaPlayer.prepareAsync(); // Chuẩn bị tải âm thanh
            mediaPlayer.setOnPreparedListener(MediaPlayer::start); // Phát sau khi tải xong
            mediaPlayer.setOnCompletionListener(MediaPlayer::release); // Giải phóng sau khi phát xong
        } catch (Exception e) {
            Log.e("AnimalAdapter", "Error playing sound", e);
            mediaPlayer.release(); // Đảm bảo giải phóng tài nguyên nếu xảy ra lỗi
        }
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView translationTextView;
        Button speakerButton;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.animalName);
            translationTextView = itemView.findViewById(R.id.animalTranslation);
            speakerButton = itemView.findViewById(R.id.speakerButton);
        }
    }
}
