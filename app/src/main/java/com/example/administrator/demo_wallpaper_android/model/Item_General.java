package com.example.administrator.demo_wallpaper_android.model;

import java.io.Serializable;

/**
 * Created by Administrator on 11/13/2017.
 */

public class Item_General implements Serializable {
    private int id;
    private String name;
    private String image;
    private int cid;

    public Item_General() {
    }

    public Item_General(int id,String name, String image, int cid) {
        this.id=id;
        this.name = name;
        this.image = image;
        this.cid= cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
