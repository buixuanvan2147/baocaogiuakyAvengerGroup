package com.example.baocaogiuaky.Nhan;

public class Flashcard1 {
    private String name;
    private String description;
    private String imageBase64;
    private String soundUrl;
    private String cardId;
    public Flashcard1() {
    }
    public Flashcard1(String name, String description, String imageBase64, String soundUrl) {
        this.name = name;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.soundUrl = soundUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
