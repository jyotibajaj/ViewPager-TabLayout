package com.example.android.miwok;

/**
 * Created by aashi on 12/03/17.
 */

public class Words {
    private String defaultTranslation;
    private String hindiTranslation;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mAudioResourceId;

    private int mImageResourceId = NO_IMAGE_PROVIDED;
    /*
    steps to include audio file:
    1. Modify list item layout to include play button.
    2.Handle clicking on play button to play audio
    3.Add in all audio files.
    4.play correct audio file as per word.
     */

    public Words(String defaultTranslation, String hindiTranslation, int imageResourceId, int mAudioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.hindiTranslation = hindiTranslation;
        this.mImageResourceId = imageResourceId;
        this.mAudioResourceId = mAudioResourceId;

    }


    public Words(String defaultTranslation, String hindiTranslation, int mAudioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.hindiTranslation = hindiTranslation;
        this.mAudioResourceId = mAudioResourceId;


    }

    //getter for default
    public String getDefaultTranslation() {
        this.getClass();
        return defaultTranslation;
    }


    //getter for miwok
    public String getHindiTranslation() {
        return hindiTranslation;
    }

    //getter for IMage
    public int getImageResourceId() {
        return mImageResourceId;
    }


    //getter for IMage
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}


