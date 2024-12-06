package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baocaogiuaky.HomeFragment;
import com.example.baocaogiuaky.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    // Khai báo ActivityResultLauncher để thay thế startActivityForResult
    private ActivityResultLauncher<Intent> createFolderLauncher;

    ActivityHomeBinding binding;  // Đổi thành ActivityHomeBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng ActivityHomeBinding thay vì ActivityMainBinding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thay thế startActivityForResult bằng ActivityResultLauncher
        createFolderLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String newFolderName = data.getStringExtra("newFolderName");
                            if (newFolderName != null) {
                                // Tạo đối tượng Folder mới
                                Folder newFolder = new Folder(newFolderName, newFolderName);

                                // Gửi thư mục mới đến HomeFragment để cập nhật RecyclerView
                                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_first);
                                if (homeFragment != null) {
                                    homeFragment.addFolder(newFolder);
                                }
                            }
                        }
                    }
                });

        // Thay thế phương thức load fragment trong BottomNavigationView
        replaceFragment(new HomeFragment());

        // Sử dụng bottomNavigationView từ binding
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.add) {
                // Thay vì startActivityForResult, gọi launcher
                Intent intent = new Intent(HomeActivity.this, CreateFolderActivity.class);
                createFolderLauncher.launch(intent);  // Sử dụng launcher thay vì startActivityForResult
            } else if (itemId == R.id.person) {
                replaceFragment(new PersonalFragment());
            }
            return true;
        });
    }

    // Phương thức để thay thế fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_first, fragment);
        fragmentTransaction.commit();
    }
}
