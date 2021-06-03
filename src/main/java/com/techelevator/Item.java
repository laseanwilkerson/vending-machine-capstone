package com.techelevator;


public abstract class Item {

    private String slotNum;
    private String name;
    private double price;
    private int itemCount;


    public Item (String slotNum, String name, double price) {
        this.slotNum = slotNum;
        this.itemCount = 5;
    }



//    public String toString() {
//        return getName() + getSlotNum();
//    }


    public String getSlotNum() { return slotNum; }

    public String getName() { return name; }

    public double getPrice() { return price; }

    public int getItemCount() { return itemCount; }

    public abstract String getMessage();

}

