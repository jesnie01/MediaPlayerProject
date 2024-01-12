package com.example.mediaplayerproject.model;

import java.util.ArrayList;

public class MedieInfo {
    private int mediaId;
    private String mediaTitle;
    private String mediaPath;
    private String mediaLength;
    private int mediasize;
    private ArrayList<String> creator;
    
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

    public String getMediaLength() {
        return mediaLength;
    }

    public void setMediaLength(String mediaLength) {
        this.mediaLength = mediaLength;
    }

    public int getMediasize() {
        return mediasize;
    }

    public void setMediasize(int mediasize) {
        this.mediasize = mediasize;
    }
    
}
