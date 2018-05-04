package com.cherryzpsoft.nanaldle;

import java.net.URL;

public class WriteItem {

    private String Content;
    private URL img;
    private String tag;
    private String emoticon;
    private String Date;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public URL getImg() {
        return img;
    }

    public void setImg(URL img) {
        this.img = img;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public WriteItem(String content, URL img, String tag, String emoticon, String date) {
        Content = content;
        this.img = img;
        this.tag = tag;
        this.emoticon = emoticon;
        Date = date;
    }

}
