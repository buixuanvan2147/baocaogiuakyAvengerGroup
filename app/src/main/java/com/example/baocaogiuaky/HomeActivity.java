package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baocaogiuaky.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private static final int CREATE_FOLDER_REQUEST_CODE = 100;

    ActivityHomeBinding binding;  // Đổi thành ActivityHomeBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng ActivityHomeBinding thay vì ActivityMainBinding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        // Sử dụng bottomNavigationView từ binding
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.add) {
                Intent intent = new Intent(HomeActivity.this, CreateFolderActivity.class);
                startActivityForResult(intent, CREATE_FOLDER_REQUEST_CODE);// Thay vì startActivity, dùng startActivityForResult với requestCode là 1

            } else if (itemId == R.id.person) {
                replaceFragment(new PersonalFragment());
            }
            return true;
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FOLDER_REQUEST_CODE && resultCode == RESULT_OK) {
            String newFolderName = data.getStringExtra("newFolderName");
            if (newFolderName != null) {
                // Tạo đối tượng Folder mới
                Folder newFolder = new Folder("Avenger_group", newFolderName, R.drawable.muc);

                // Gửi thư mục mới đến HomeFragment để cập nhật RecyclerView
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_first);
                if (homeFragment != null) {
                    homeFragment.addFolder(newFolder);
                }
            }
        }
    }




    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_first, fragment);
        fragmentTransaction.commit();
    }
}
