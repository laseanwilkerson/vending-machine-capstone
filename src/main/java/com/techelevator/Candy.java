package com.techelevator;

public class Candy extends Item {

    private final String message = "Munch Munch, Yum!";


    public Candy (String slotNumber, String name, double price) {
        super(slotNumber, name, price);


    }

    public String getMessage() { return message; }
}

