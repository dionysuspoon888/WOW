package com.example.wow;

/**
 * Created by D on 1/29/2018.
 */

//Contructor item for cardview
public class ShoppingItem {

    private int imageResource;
    private String product;
    private double price;
    private int quantity;

    public ShoppingItem(int imageResources,String products,double prices,int quantitys){
        imageResource=imageResources;
        product=products;
        price=prices;
        quantity=quantitys;

    }

    //Custom Method
   public int getImageResource(){return imageResource;}
   public String getProduct(){return product;}
   public double getPrice(){return price;}
   public int getQuantity(){return quantity;}


}
