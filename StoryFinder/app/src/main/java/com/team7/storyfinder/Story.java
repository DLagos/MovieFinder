package com.team7.storyfinder;

import android.graphics.Bitmap;
import java.util.UUID;


public class Story {
    private UUID mUuid;
    private String mName;
    private String mDescription;
    private Bitmap img;

    public Story(UUID mUuid, String mName, String mDescription, Bitmap img) {
        this.mUuid = mUuid;
        this.mName = mName;
        this.mDescription = mDescription;
        this.img = img;
    }

    public UUID getmUuid() {
        return mUuid;
    }

    public void setmUuid(UUID mUuid) {
        this.mUuid = mUuid;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
