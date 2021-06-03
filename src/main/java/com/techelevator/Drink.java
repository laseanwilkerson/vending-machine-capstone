
package com.techelevator;

    public class Drink extends Item {

        private final String message = "Glug Glug, Yum!";


        public Drink (String slotNumber, String name, double price) {
            super(slotNumber, name, price);


        }

        public String getMessage() { return message; }
    }

