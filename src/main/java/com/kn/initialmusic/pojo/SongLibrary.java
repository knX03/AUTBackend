package com.kn.initialmusic.pojo;

public class SongLibrary {
    private Integer id;
    private String song_ID;
    private String singer_ID;
    private String album_ID;
    private String addDate;

    public SongLibrary() {
    }

    public SongLibrary(Integer id, String song_ID, String singer_ID, String album_ID, String addDate) {
        this.id = id;
        this.song_ID = song_ID;
        this.singer_ID = singer_ID;
        this.album_ID = album_ID;
        this.addDate = addDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSong_ID() {
        return song_ID;
    }

    public void setSong_ID(String song_ID) {
        this.song_ID = song_ID;
    }

    public String getSinger_ID() {
        return singer_ID;
    }

    public void setSinger_ID(String singer_ID) {
        this.singer_ID = singer_ID;
    }

    public String getAlbum_ID() {
        return album_ID;
    }

    public void setAlbum_ID(String album_ID) {
        this.album_ID = album_ID;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "songLibrary{" +
                "id='" + id + '\'' +
                ", song_ID='" + song_ID + '\'' +
                ", singer_ID='" + singer_ID + '\'' +
                ", album_ID='" + album_ID + '\'' +
                ", addDate='" + addDate + '\'' +
                '}';
    }
}
