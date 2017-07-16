package com.ashanudeshitha.instaapp;

/**
 * Created by ashanudeshitha on 7/16/17.
 */

public class insta {
    private String title,desc,image;

    public insta(){

    }
    public insta(String title,String desc,String image){
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
