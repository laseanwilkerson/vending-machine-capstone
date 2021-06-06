package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

    private Map<String, Item> inventory = new HashMap<>();
    private double currentBalance = 0.00;
    private String destinationFile = "src/test/resources/Log.txt";
    private File destination = new File(destinationFile);
    private String currentDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").format(new Date());





    private Map getVendingMachineStocked(String inventoryFilePath) {
        File inputFile = new File(inventoryFilePath);
        try (Scanner inputScanner = new Scanner(inputFile)) {
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
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
                    Gum item = new Gum (productSlot, productName, productPrice);
                    inventory.put(productSlot, item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inventory;
    }


    public double feedMe(double currentBalance){
        Scanner scan = new Scanner(System.in);
        String amountInput;
        int amountProvided = 0;
        String  x = "";
        double reportBalance = currentBalance;
        while(!x.equals("0")) {

            try {
                System.out.println("Current Balance: $" + currentBalance);
                System.out.println("How much are you entering");
                amountInput = scan.nextLine();
                Double amount = Double.valueOf(amountInput);
                if (amount == 1) {
                    currentBalance = getCurrentBalance() + amount;
                    amountProvided += amount;
                } else if (amount == 2) {
                    currentBalance = getCurrentBalance() + amount;
                    amountProvided += amount;
                } else if (amount == 5) {
                    currentBalance = getCurrentBalance() + amount;
                    amountProvided += amount;
                } else if (amount == 10) {
                    currentBalance = getCurrentBalance() + amount;
                    amountProvided += amount;
                } else {
                    System.out.println("You have provided unacceptable amount! We only accept $1, $2, $5 or $10!");
                }
                System.out.println("Current Balance is: $ " + currentBalance);
                Scanner answer = new Scanner(System.in);
                System.out.println("Press 0 to return to menu or any key to add more money");
                x = answer.nextLine();
            } catch (NumberFormatException ex) {
                System.out.println("You have provided unacceptable amount! We only accept $1, $2, $5 or $10!");
            }
        }
        System.out.println("Current Money Provided: $" + amountProvided);
        System.out.println("Current Balance: $" + currentBalance);
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(destination, true));
            writer.println(currentDateTime + " FEED MONEY: $" + reportBalance + " $" + currentBalance);
            writer.flush();
        } catch(IOException e) {
            System.out.println("There is an error: " + e);
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
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(destination, true));
                            writer.println(">" + currentDateTime + " " + item.getName() + " " + item.getSlotNum() + " $" + item.getPrice());
                            writer.flush();
                        } catch(IOException e) {
                            System.out.println("There is an error: " + e);
                        }
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
