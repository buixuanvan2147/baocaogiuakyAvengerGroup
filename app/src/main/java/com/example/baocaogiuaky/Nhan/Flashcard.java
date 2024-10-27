package com.example.baocaogiuaky.Nhan;

public class Flashcard {
    private String frontText;
    private String backText;

    public Flashcard(String frontText, String backText) {
        this.frontText = frontText;
        this.backText = backText;
    }

    public String getFrontText() {
        return frontText;
    }

    public String getBackText() {
        return backText;
    }
}

