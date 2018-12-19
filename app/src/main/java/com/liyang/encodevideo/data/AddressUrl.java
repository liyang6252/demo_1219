package com.liyang.encodevideo.data;

import org.litepal.crud.LitePalSupport;

public class AddressUrl extends LitePalSupport {

    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
