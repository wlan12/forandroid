package com.example.lastproject;

import android.graphics.Bitmap;

public class listData {
    private Bitmap bImage;
    private String name;
    private String info;

    public listData(Bitmap bImage, String name, String info){
        this.bImage = bImage;
        this.name = name;
        this.info = info;
    }

    public Bitmap getbImage(){
        return bImage;
    }

    public void setbImage(Bitmap bImage){
        this.bImage = bImage;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getInfo(){
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }
}
