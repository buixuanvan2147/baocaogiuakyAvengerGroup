package com.example.baocaogiuaky.Nhan;
import android.graphics.Bitmap;
import android.net.Uri;
public class Flashcard1 {
    private String name;
    private String description;
    private Bitmap image;// Đây là tham số kiểu int

    // Constructor với ba tham số
    public Flashcard1(String name, String description, Bitmap image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Bitmap getImage() { return image; }
}
