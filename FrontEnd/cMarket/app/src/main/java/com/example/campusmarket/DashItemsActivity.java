package com.example.campusmarket;

public class DashItemsActivity {

    private String name;
    private String price;
    private String condition;
    private String category;

    // we will be calling this DashItemsActivity object --
    // in the main activity to get all of the necessary fields of an item

    /**
     * This object is called in the main activity to get all of the necessary fields of an item
     * @param name
     * @param price
     * @param condition
     * @param category
     */
    public DashItemsActivity(String name, String price, String condition, String category) {
        this.name = name;
        this.price = price;
        this.condition = condition;
        this.category = category;
    }

    /**
     * Will return the name of the item
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Can update the name of the item
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Will return the price of the item
     * @return price
     */
    public String getPrice(){
        return price;
    }

    /**
     * Can update the name of the item
     * @param price
     */
    public void setPrice(String price){
        this.price = price;
    }

    /**
     * Will return the condition of the item
     * @return condition
     */
    public String getCondition(){
        return condition;
    }

    /**
     * Can update the condition of the item
     * @param condition
     */
    public void setCondition(String condition){
        this.condition = condition;
    }

    /**
     * Will return the category of the item
     * @return category
     */
    public String getCategory(){
        return category;
    }

    /**
     * Can update the category of the item
     * @param category
     */
    public void setCategory(String category){
        this.category = category;
    }

}
