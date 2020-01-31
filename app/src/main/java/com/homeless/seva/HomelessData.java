package com.homeless.seva;


public class HomelessData {
    String work;
    String mobile;
    String location;
    String image;


    public HomelessData() {


    }


    public HomelessData(String work, String mobile, String location, String image) {
        this.work = work;
        this.mobile = mobile;
        this.location = location;
        this.image = image;


    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
