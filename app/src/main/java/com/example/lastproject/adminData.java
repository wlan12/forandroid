package com.example.lastproject;

import android.graphics.Bitmap;

public class adminData {
    private Bitmap bImage;
    private String id;
    private String name;
    private String info;

    public adminData(Bitmap bImage, String id, String name, String info){
        this.bImage = bImage;
        this.id = id;
        this.name = name;
        this.info = info;
    }

    public Bitmap getbImage(){
        return bImage;
    }

    public void setbImage(Bitmap bImage){
        this.bImage = bImage;
    }
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
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
