package com.example.baocaogiuaky.Nhan;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.R;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<Animal> animalList;
    private Context context;

    public AnimalAdapter(List<Animal> animalList, Context context) {
        this.animalList = animalList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        holder.nameTextView.setText(animal.getName());
        holder.translationTextView.setText(animal.getTranslation());

        
        holder.speakerButton.setOnClickListener(v -> playSound(animal.getSoundResource()));
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    private void playSound(int soundResource) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, soundResource);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> mp.release());
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
