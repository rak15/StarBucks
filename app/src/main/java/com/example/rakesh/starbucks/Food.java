package com.example.rakesh.starbucks;

/**
 * Created by Rakesh on 09-02-2017.
 */

public class Food {
    private String name;
    private String description;
    private int img;
    public static final Food[] foods={
            new Food("Burger","Veg Burger",R.drawable.burger),
            new Food("Sandwich","Veg Sandwich",R.drawable.sandwich),
            new Food("Chicken","Fried Chicken",R.drawable.chicken)
    };
    private Food(String name,String description,int img){
        this.name=name;
        this.description=description;
        this.img=img;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public int getImg(){
        return img;
    }
    public String toString(){
        return this.name;
    }
}
