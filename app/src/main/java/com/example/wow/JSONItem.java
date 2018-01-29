package com.example.wow;



//Set up JSON Object and Constructor
public class JSONItem {
    private String imageUrl;
    private String creator;
    private int like;
    private int view;

    public JSONItem(String imageUrls, String creators, int likes,int views) {
        imageUrl = imageUrls;
        creator = creators;
        like = likes;
        view = views;
    }
  //Method to retrieve the data
    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreator() {
        return creator;
    }

    public int getLikeCount() {
        return like;
    }

    public int getViewCount(){ return view;}
}