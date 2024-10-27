package com.example.baocaogiuaky.Nhan;
public class Animal {
    private String name;
    private String translation;
    private int soundResource;

    public Animal(String name, String translation, int soundResource) {
        this.name = name;
        this.translation = translation;
        this.soundResource = soundResource;
    }

    public String getName() {
        return name;
    }

    public String getTranslation() {
        return translation;
    }

    public int getSoundResource() {
        return soundResource;
    }
}


