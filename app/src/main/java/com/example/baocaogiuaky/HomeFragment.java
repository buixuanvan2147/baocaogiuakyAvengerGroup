package com.example.baocaogiuaky;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;  // Sử dụng đúng lớp SearchView
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baocaogiuaky.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rcvfolder;
    private SearchView searchView;
    private FolderAdapter folderAdapter;
    private List<Folder> folderList;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Tìm kiếm SearchView
        searchView = view.findViewById(R.id.searchView);
        TextView searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTextSize(20);

        // Khởi tạo RecyclerView
        rcvfolder = view.findViewById(R.id.recycleView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvfolder.setLayoutManager(gridLayoutManager);

        folderList = new ArrayList<>();
        folderAdapter = new FolderAdapter(getContext(), folderList);
        rcvfolder.setAdapter(folderAdapter);

        // Lấy UID của người dùng hiện tại
        String uid = getCurrentUserId();
        if (uid == null) {
            Toast.makeText(getContext(), "Không thể xác thực người dùng", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Lấy danh sách folders từ Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(uid) // Chỉ tải folders thuộc về tài khoản hiện tại
                .child("folders");

        loadFoldersFromFirebase();

        // Thiết lập SearchView để tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query); // Lọc danh sách khi nhấn submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText); // Lọc danh sách khi text thay đổi
                return false;
            }
        });

        return view;
    }

    // Lấy UID của người dùng hiện tại
    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        }
        return null; // Người dùng chưa đăng nhập
    }

    // Lấy danh sách folders từ Firebase
    private void loadFoldersFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                folderList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Folder folder = dataSnapshot.getValue(Folder.class);
                    if (folder != null) {
                        folder.setId_Folder(dataSnapshot.getKey()); // Gán ID từ key
                        Log.d("FolderData", "Folder name: " + folder.getName_Folder());
                        folderList.add(folder);
                    }
                }
                folderAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không thể tải dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Lọc danh sách thư mục
    private void filterList(String query) {
        List<Folder> filteredList = new ArrayList<>();
        for (Folder folder : folderList) {
            if (folder.getName_Folder().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(folder);
            }
        }
        folderAdapter.setFilteredList(filteredList); // Cập nhật danh sách đã lọc trong adapter
        folderAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    // Thêm thư mục mới vào danh sách (nếu cần thêm từ một Activity khác)
    public void addFolder(Folder folder) {
        folderList.add(folder);
        folderAdapter.notifyItemInserted(folderList.size() - 1); // Cập nhật RecyclerView
    }
}
