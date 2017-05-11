package com.example.rakesh.starbucks;

/**
 * Created by Rakesh on 08-02-2017.
 */

public class Drink {
    private String name;
    private String description;
    private int img;
    public static final Drink[] drinks={                        //array of 3 Drink objects
            new Drink("Latte","Latte coffee",R.drawable.latte),
            new Drink("Filter","Filter coffee",R.drawable.filter),
            new Drink("Cappuccino","Cappuccino coffee",R.drawable.cappuccino)
    };
    private Drink(String name,String description,int img){
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
    public String toString(){        // to give name to the list items to display from array items
        return this.name;
    }
}
