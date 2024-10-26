package com.example.baocaogiuaky;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvfolder;
    private GridLayoutManager gridLayoutManager;
    private SearchView searchView;
    private FolderAdapter folderAdapter;
    private List<Folder> folderList;

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

            private void filterList(String newText) {
                List<Folder> filteredList = new ArrayList<>();
                for (Folder item : folderList) {
                    if (item.getName_Folder().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                if (filteredList.isEmpty()) {
                    Toast.makeText(getContext(), "No folder found", Toast.LENGTH_SHORT).show();
                } else {
                    folderAdapter.setFilteredList(filteredList);
                }
            }
        });

        return view;
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
