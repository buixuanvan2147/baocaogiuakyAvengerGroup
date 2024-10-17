package com.example.baocaogiuaky;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.Nhan.ShowFlashcardActivity;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {

    private List<Folder> mlistFolder;

    public FolderAdapter(List<Folder> mlistFolder) {
        this.mlistFolder = mlistFolder;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder folder = mlistFolder.get(position);
        if (folder != null) {
            holder.imgfolder.setImageResource(folder.getImage());
            holder.txtnamefolder.setText(folder.getName());

            // Set OnClickListener for item click
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = holder.getAdapterPosition(); // Get the current position
                    if (currentPosition == 0) { // Check if it's the first item
                        // Create an Intent to start the ShowFlashcardActivity
                        Intent intent = new Intent(v.getContext(), ShowFlashcardActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mlistFolder != null ? mlistFolder.size() : 0;
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgfolder;
        private TextView txtnamefolder;

        public FolderViewHolder(@NonNull View view) {
            super(view);
            imgfolder = view.findViewById(R.id.img_folder);
            txtnamefolder = view.findViewById(R.id.text_name_folder);
        }
    }
}
