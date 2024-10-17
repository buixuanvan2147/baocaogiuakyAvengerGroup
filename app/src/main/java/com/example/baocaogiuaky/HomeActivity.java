package com.example.baocaogiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rcvfolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ImageView icon_account = findViewById(R.id.icon_account);
        icon_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HomeActivity.this , ProfileActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton btn_add_folder = findViewById(R.id.btn_add_folder);
        btn_add_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CreateFolderActivity.class);
                startActivity(intent);
            }
        });
        rcvfolder = findViewById(R.id.rcv_folder);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvfolder.setLayoutManager(gridLayoutManager);

        FolderAdapter adapter = new FolderAdapter(getListFolder());
        rcvfolder.setAdapter(adapter);
    }

    private List<Folder> getListFolder() {
        List<Folder> list = new ArrayList<>();
        list.add(new Folder(R.drawable.wildanimal,"Wild Animal"));
        list.add(new Folder(R.drawable.wildanimal,"Wild Animal"));
        list.add(new Folder(R.drawable.flower,"Flower"));
        list.add(new Folder(R.drawable.flower,"Flower"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        list.add(new Folder(R.drawable.vegetable,"Vegetable"));
        return list;
    }
}