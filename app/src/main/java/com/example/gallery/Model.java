package com.example.gallery;

public class Model {


    private String imageURL;
    private String TimeDate;
    public Model(String s){

    }
    public Model(String imageURL, String TimeDate){
        this.imageURL = imageURL;
        this.TimeDate = TimeDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTimeDate(){
        return TimeDate;
    }

    public void setTimeDate(){
        this.TimeDate = TimeDate;
    }



}