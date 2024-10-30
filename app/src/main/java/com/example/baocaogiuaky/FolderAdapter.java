package com.example.baocaogiuaky;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.Nhan.MainFlashcard;

import java.util.Collections;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private List<Folder> mListFolder;
    private Context mContext;

    // Constructor
    public FolderAdapter(Context context, List<Folder> mListFolder) {
        this.mContext = context;
        this.mListFolder = mListFolder;
    }

    // Phương thức để cập nhật danh sách đã lọc
    public void setFilteredList(List<Folder> filteredList) {
        this.mListFolder = filteredList;
        notifyDataSetChanged();
    }

    // Phương thức để thêm một thư mục mới vào danh sách
    public void addFolder(Folder newFolder) {
        mListFolder.add(newFolder);
        notifyItemInserted(mListFolder.size() - 1);
    }
    // Phương thức để hoán đổi vị trí các thư mục
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mListFolder, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mListFolder, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
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
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition == 0) {
                    Intent intent = new Intent(mContext, MainFlashcard.class);
                    mContext.startActivity(intent);
                }
            }
        });
        holder.btnMoreOptions.setOnClickListener(v -> showPopupMenu(v, holder.getAdapterPosition()));
    }
    // Hiển thị menu khi nhấn nút 3 chấm
    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.inflate(R.menu.folder_options_menu); // Tham chiếu đến file menu XML

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.option_remove) {
                // Xóa folder
                removeFolder(position);
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }

    // Phương thức để xóa folder
    private void removeFolder(int position) {
        mListFolder.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mListFolder.size());
        Toast.makeText(mContext, "Bộ flashcard đã được xóa", Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return (mListFolder != null) ? mListFolder.size() : 0;
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView namefolder;
        private TextView nameuser;
        private ImageView btnMoreOptions;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView2);
            namefolder = itemView.findViewById(R.id.name_folder);
            nameuser = itemView.findViewById(R.id.name_user);
            btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);
        }
    }
}
