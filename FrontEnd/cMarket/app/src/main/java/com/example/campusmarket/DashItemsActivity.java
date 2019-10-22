package com.example.campusmarket;

public class DashItemsActivity {

    private String name;
    private String price;
    private String condition;
    private String category;

    // we will be calling this DashItemsActivity object --
    // in the main activity to get all of the necessary fields of an item
    public DashItemsActivity(String name, String price, String condition, String category) {
        this.name = name;
        this.price = price;
        this.condition = condition;
        this.category = category;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getCondition(){
        return condition;
    }

    public void setCondition(String condition){
        this.condition = condition;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

}
