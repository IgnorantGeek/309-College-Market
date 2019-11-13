package com.example.campusmarket;

public class CartItemsActivity {


    private String name;
    private String price;
//    private String condition;
//    private String category;
    private String user;
    private String refnum;

    // we will be calling this DashItemsActivity object --
    // in the main activity to get all of the necessary fields of an item

    /**
     * This object is called in the main activity to get all of the necessary fields of an item
     * @param name name of item
     * @param price price of item
//     * @param condition condition of item
//     * @param category category of item
     * @param user
     */
    public CartItemsActivity(String name, String price, String user, String refnum) {
        this.name = name;
        this.price = price;
//        this.condition = condition;
//        this.category = category;
        this.user = user;
        this.refnum = refnum;
    }

    /**
     * Will return the name of the item
     * @return name of item
     */
    public String getName(){
        return name;
    }

    /**
     * Can update the name of the item
     * @param name name of item
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Will return the price of the item
     * @return price of item
     */
    public String getPrice(){
        return price;
    }

    /**
     * Can update the price of the item
     * @param price price of item
     */
    public void setPrice(String price){
        this.price = price;
    }

//    /**
//     * Will return the condition of the item
//     * @return condition of item
//     */
//    public String getCondition(){
//        return condition;
//    }
//
//    /**
//     * Can update the condition of the item
//     * @param condition condition of item
//     */
//    public void setCondition(String condition){
//        this.condition = condition;
//    }
//
//    /**
//     * Will return the category of the item
//     * @return category of item
//     */
//    public String getCategory(){
//        return category;
//    }
//
//    /**
//     * Can update the category of the item
//     * @param category category of item
//     */
//    public void setCategory(String category){
//        this.category = category;
//    }

    /**
     * Will return the seller of the item
     * @return seller of item
     */
    public String getUser(){
        return user;
    }

    /**
     * Can update the seller of the item
     * @param user seller of the item
     */
    public void setUser(String user){
        this.user = user;
    }

    public String getRefnum(){
        return refnum;
    }

    /**
     * Can update the refnum of the item
     * @param refnum of the item
     */
    public void setRefnum(String refnum){
        this.refnum = refnum;
    }

}


