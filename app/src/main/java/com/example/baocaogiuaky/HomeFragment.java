package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvfolder;
    private GridLayoutManager gridLayoutManager;
    private SearchView searchView;
    private FolderAdapter folderAdapter;
    private List<Folder> folderList;
    private static final int CREATE_FOLDER_REQUEST_CODE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SearchView searchView = view.findViewById(R.id.searchView);
        TextView searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTextSize(20);

        rcvfolder = view.findViewById(R.id.recycleView);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvfolder.setLayoutManager(gridLayoutManager);

        // Lấy danh sách Folder
        folderList = getListFolder();
        folderAdapter = new FolderAdapter(getContext(), folderList); // Pass context here
        rcvfolder.setAdapter(folderAdapter);

        // Thiết lập SearchView
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }


        });
        // Thiết lập ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                folderAdapter.moveItem(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Không xử lý vuốt (swipe) trong trường hợp này
            }
        });
        itemTouchHelper.attachToRecyclerView(rcvfolder);

        return view;
    }
    private void filterList(String newText) {
        List<Folder> filteredList = new ArrayList<>();
        for (Folder item : folderList) {
            if (item.getName_Folder().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        folderAdapter.setFilteredList(filteredList);
    }

    public void addFolder(Folder folder) {
        folderList.add(folder); // Thêm thư mục mới vào danh sách
        folderAdapter.notifyItemInserted(folderList.size() - 1); // Cập nhật RecyclerView
    }


    // Danh sách thư mục mẫu
    private List<Folder> getListFolder() {
        List<Folder> list = new ArrayList<>();
        list.add(new Folder("Avenger_group", "Wild Animal", R.drawable.muc));
        list.add(new Folder("Avenger_group", "Flower", R.drawable.muc));
        list.add(new Folder("Avenger_group", "Vegetable", R.drawable.muc));
        list.add(new Folder("Avenger_group", "Sport", R.drawable.muc));
        list.add(new Folder("Avenger_group", "Family", R.drawable.muc));
        list.add(new Folder("Avenger_group", "Household Items", R.drawable.muc));

        return list;
    }

}
