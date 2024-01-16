package com.example.mediaplayerproject.model;

import java.util.ArrayList;

public class MediaInfo {
    public int mediaId;
    public String mediaTitle;
    public String mediaPath;
    ArrayList<String> creatorName = new ArrayList<>();
    public int mediaSize;

    //region Getters and setters
    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public ArrayList<String> getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName.add(creatorName);
    }

    public int getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(int mediaSize) {
        this.mediaSize = mediaSize;
    }
    //endregion
}
