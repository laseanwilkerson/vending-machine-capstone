package com.techelevator;

    public class Gum extends Item {

        private final String message = "Chew Chew, Yum!";

        public Gum (String slotNumber, String name, double price) {
            super(slotNumber, name, price);

        }

        public String getMessage() { return message; }
    }
