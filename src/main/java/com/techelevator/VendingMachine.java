package com.techelevator;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

    private Map<String, Item> inventory = new HashMap<>();
    private double currentBalance = 0.00;
    private String currentDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").format(new Date());





    public Map getVendingMachineStocked(String inventoryFilePath) {
        File inputFile = new File(inventoryFilePath);
        try (Scanner inputScanner = new Scanner(inputFile)) {
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                if (line.contains("|")) {
                    String[] product = line.split("\\|");
                    String productSlot = product[0];
                    String productName = product[1];
                    double productPrice = Double.parseDouble(product[2]);
                    String productCategory = product[3];
                    if (productCategory.equals("Chip")) {
                        Chip item = new Chip(productSlot, productName, productPrice);
                        inventory.put(productSlot, item);
                    } else if (productCategory.equals("Candy")) {
                        Candy item = new Candy(productSlot, productName, productPrice);
                        inventory.put(productSlot, item);
                    } else if (productCategory.equals("Drink")) {
                        Drink item = new Drink(productSlot, productName, productPrice);
                        inventory.put(productSlot, item);
                    } else {
                        Gum item = new Gum(productSlot, productName, productPrice);
                        inventory.put(productSlot, item);
                    }
                } else {
                    System.out.println("This file doesn't contain products required to list!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }


    public double feedMe(String amountInput){
            try {
                Double amount = Double.valueOf(amountInput);
                if (amount == 1) {
                    currentBalance = getCurrentBalance() + amount;
                } else if (amount == 2) {
                    currentBalance = getCurrentBalance() + amount;
                } else if (amount == 5) {
                    currentBalance = getCurrentBalance() + amount;
                } else if (amount == 10) {
                    currentBalance = getCurrentBalance() + amount;
                } else {
                    currentBalance = 0.0;
                }
            }catch (Exception e) {

            }
        return currentBalance;

    }

    public void selectProduct(){
        if (currentBalance > 0) {
            displayInventory();
            Scanner scan = new Scanner(System.in);
            String userInput = "";
            System.out.println("Please enter a slot number");
            userInput = scan.nextLine();
            if (!inventory.containsKey(userInput)) {
                System.out.println("Invalid slot number");
            } else {
                //Grabs the items
                Item item = inventory.get(userInput);
                //Check to see if balance is greater than 0
                if (item.getItemCount() > 0) {
                    //Check to see if the current price is enough to make the purchase
                    if (getCurrentBalance() >= item.getPrice()) {
                        currentBalance -= item.getPrice();
                        System.out.println(item.getMessage());
                        System.out.print(item.getName() + " | " + item.getPrice() + " | " + "Your remaining balance is: $");
                        System.out.printf(" %.2f", currentBalance);
                        System.out.println(" ");

                    } else {
                        System.out.println("Not enough money");
                    }
                } else {
                    System.out.println("This product is sold out");
                }
            }
        }else {
            System.out.println("Your balance is $0. Please add money first!");
        }
    }


    public double getCurrentBalance() {
        return currentBalance;
    }


    public void displayInventory() {
        for(String key : inventory.keySet()) {
            System.out.println(inventory.get(key).toString());
        }
    }


}
