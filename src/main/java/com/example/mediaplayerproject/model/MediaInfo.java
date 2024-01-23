package com.example.mediaplayerproject.model;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class MediaInfo {
    private int mediaId;
    private String mediaTitle;
    private String mediaPath;
    private ArrayList<Integer> occursOnPlaylists = new ArrayList<>();
    //ArrayList<String> creatorName = new ArrayList<>(); TBD
    private int mediaSize;

    //region Getters and setters
    public ArrayList<Integer> getOccursOnPlaylists() {
        return getOccursOnPlaylists();
    }
    public void addToOccursOnPlaylists(int playlistId) {
        occursOnPlaylists.add(playlistId);
    }
    public void delItemOnOccursOnPlaylists(int playlistId) {
        for (int i : occursOnPlaylists) {
            occursOnPlaylists.remove(occursOnPlaylists.indexOf(playlistId));
        }
    }
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

//    public ArrayList<String> getCreatorName() {
//        return creatorName;
//    }
//
//    public void setCreatorName(String creatorName) {
//        this.creatorName.add(creatorName);
//    }

    public int getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(int mediaSize) {
        this.mediaSize = mediaSize;
    }
    //endregion
}
