package com.example.baocaogiuaky;

public class Folder {
    private String  name_Folder;
    private String name_User;
    private int image;

    public Folder(String name_User, String name_Folder, int image) {
        this.name_User = name_User;
        this.name_Folder = name_Folder;
        this.image = image;
    }

    public String getName_Folder() {
        return name_Folder;
    }

    public String getName_User() {
        return name_User;
    }

    public int getImage() {
        return image;
    }

    public void setName_Folder(String name_Folder) {
        this.name_Folder = name_Folder;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName_User(String name_User) {
        this.name_User = name_User;
    }
}
