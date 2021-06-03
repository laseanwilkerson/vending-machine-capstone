package com.techelevator;

public class Chip extends Item {

    private final String message = "Crunch Crunch, Yum!";


    public Chip (String slotNumber, String name, double price) {
        super(slotNumber, name, price);


    }

    public String getMessage() { return message; }
}
