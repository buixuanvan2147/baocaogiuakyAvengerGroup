package com.example.baocaogiuaky;

public class Folder {
    private String id_Folder;
    private String name_Folder;

    // Constructor mặc định (bắt buộc)
    public Folder() {
    }

    // Constructor đầy đủ (nếu cần)
    public Folder(String name_Folder, String id_Folder) {
        this.id_Folder = id_Folder;
        this.name_Folder = name_Folder;
    }

    // Getter and Setter
    public String getId_Folder() {
        return id_Folder;
    }

    public void setId_Folder(String id_Folder) {
        this.id_Folder = id_Folder;
    }

    public String getName_Folder() {
        return name_Folder;
    }

    public void setName_Folder(String name_Folder) {
        this.name_Folder = name_Folder;
    }
}
