package com.example.wow;

public class FirebaseUploadItem {
    private String upName;
    private String upImageUrl;
    private String upID;

    public FirebaseUploadItem() {
        //empty constructor needed
    }

    public FirebaseUploadItem(String id,String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        upID=id;
        upName= name;
        upImageUrl = imageUrl;
    }
    //Get&Set method
    public String getID() {
        return upID;
    }
    public void setID(String iD) {
        upID=iD;
    }

    public String getName() {
        return upName;
    }

    public void setName(String name) {
        upName= name;
    }


    public String getImageUrl() {
        return upImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        upImageUrl = imageUrl;
    }
}