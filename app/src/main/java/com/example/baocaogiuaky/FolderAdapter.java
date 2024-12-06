package com.example.baocaogiuaky;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.Nhan.MainFlashcard;

import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private List<Folder> mListFolder;
    private Context mContext;

    // Constructor
    public FolderAdapter(Context context, List<Folder> mListFolder) {
        this.mContext = context;
        this.mListFolder = mListFolder;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder folder = mListFolder.get(position);
        holder.namefolder.setText(folder.getName_Folder());

        holder.itemView.setOnClickListener(v -> {
            String folderId = folder.getId_Folder();
            Log.d("FolderAdapter", "Folder ID: " + folderId);
            Intent intent = new Intent(mContext, MainFlashcard.class);
            intent.putExtra("folderId", folder.getId_Folder());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (mListFolder != null) ? mListFolder.size() : 0;
    }

    public void setFilteredList(List<Folder> filteredList) {
        this.mListFolder = filteredList;
        notifyDataSetChanged();
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        private TextView namefolder;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            namefolder = itemView.findViewById(R.id.name_folder);
        }
    }
}
