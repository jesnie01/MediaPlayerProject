package com.example.mediaplayerproject.model;

public class PlaylistInfo {
    private int playlistId;
    private String playlistTitle;
    private String playlistOwner;

    public int getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public String getPlaylistOwner() {
        return playlistOwner;
    }

    public void setPlaylistOwner(String playlistOwner) {
        this.playlistOwner = playlistOwner;
    }
}
