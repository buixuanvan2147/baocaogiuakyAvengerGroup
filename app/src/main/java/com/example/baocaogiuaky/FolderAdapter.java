package com.example.baocaogiuaky;

import android.content.Context;
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

public class FolderAdapter extends  RecyclerView.Adapter<FolderAdapter.FolderViewHolder>{
    private List<Folder> mListFolder;
    private Context mContext;
    public void setFilteredList(List<Folder> filteredList) {
        this.mListFolder = filteredList;
        notifyDataSetChanged();
    }

    public FolderAdapter(Context context, List<Folder> mListFolder) {
        this.mContext = context;
        this.mListFolder = mListFolder;
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder,parent,false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        Folder folder = mListFolder.get(position);
        if (folder == null) {
            return;
        }

        holder.image.setImageResource(folder.getImage());
        holder.namefolder.setText(folder.getName_Folder());
        holder.nameuser.setText(folder.getName_User());

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition(); // Lấy vị trí hiện tại của item
                if (currentPosition == 0) {
                    Intent intent = new Intent(mContext, ShowFlashcardActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if(mListFolder != null){
            return mListFolder.size();
        }
        return 0;
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView namefolder;
        private TextView nameuser;
        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView2);
            namefolder = itemView.findViewById(R.id.name_folder);
            nameuser = itemView.findViewById(R.id.name_user);
        }
    }
}
