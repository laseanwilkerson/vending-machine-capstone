package com.techelevator;

import com.techelevator.view.Menu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class VendingMachineCLI {

	//Creating the map
	private Map<String, Item> inventory = new HashMap<>();
	private Menu menu;
	private double currentBalance = 0.00;
	// Report file path
	String destinationFile = "src/test/resources/Log.txt";
	// Create File for scanner
	File destination = new File(destinationFile);
	// Time format for report
	String currentDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").format(new Date());

	//Main menu options
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "EXIT";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	//Purchase menu functions
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH = "Finish Transaction";
	private static final String [] PURCHASE_MENU_OPTION = { PURCHASE_MENU_OPTION_FEED_MONEY,PURCHASE_MENU_OPTION_SELECT_PRODUCT,PURCHASE_MENU_OPTION_FINISH };
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}


	public void run() {
		// Calling method to stock up Vending Machine
		getVendingMachineStocked();
		// Main menu and Purchase menu interact
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				while(true) {
					String choicePurchase = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTION);
					if (choicePurchase.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						feedMe();
					} else if (choicePurchase.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						selectProduct();
						//SELECT PRODUCT
					} else if (choicePurchase.equals(PURCHASE_MENU_OPTION_FINISH)) {
						finishTransaction();
						break;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("                      Thank you! Come back soon!");
				System.out.println("-----------------------------------------------------------------------------");
				System.exit(1);
			}
		}
	}

	//Welcome sign
	public static void main(String[] args) {
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$                     VENDO-MATIC 800                         $$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("                                                                                 ");
		System.out.println("                      WE ARE HERE TO SAVE YOUR HUNGRY SOUL!                      ");
		System.out.println("                            P.S.BUT IT AIN'T FREE                                ");
		System.out.println("                                                            Umbrella Corp        ");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
// Method to stock up Vending Machine
	private void getVendingMachineStocked() {
		// Path to the file with products list
		String path = "vendingmachine.csv";
		File inputFile = new File(path);
		try (Scanner inputScanner = new Scanner(inputFile)) {
			// Scanning the file
			while (inputScanner.hasNextLine()) {
				String line = inputScanner.nextLine();
				// Splitting the line from file to assign the product details and fill the Array
				if (line.contains("|")) {
				String[] product = line.split("\\|");
				String productSlot = product[0];
				String productName = product[1];
				// Converting String from the array that contains the price info
				double productPrice = Double.parseDouble(product[2]);
				String productCategory = product[3];
				// Creating new Object based on the product category
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
				// In case file doesn't contain splitter "|" - we will stop and return with the message
				} else {
					System.out.println("-----------------------------------------------------------------------------");
					System.out.println("              This file doesn't contain required parameters!");
					System.out.println("-----------------------------------------------------------------------------");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void feedMe(){
		Scanner scan = new Scanner(System.in);
		// amountInput is a String for purpose to check if customer provide correct value that can be converted into double
		String amountInput;
		//amountProvided is for report purpose
		int amountProvided = 0;
		String  x = "";
		double reportBalance = currentBalance;
		while(!x.equals("0")) {
			try {
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("                 Current Balance: $" + currentBalance);
				System.out.println("                 Please provide $1, $2, $5 or $10 bill");
				System.out.println("                 that you want to add to the balance!");
				System.out.println("-----------------------------------------------------------------------------");
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
					System.out.println("-----------------------------------------------------------------------------");
					System.out.println("   You have provided unacceptable amount! We only accept $1, $2, $5 or $10!");
					System.out.println("-----------------------------------------------------------------------------");
				}
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("                  Current Balance is: $ " + currentBalance);
				System.out.println("-----------------------------------------------------------------------------");
				Scanner answer = new Scanner(System.in);
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("              Press 0 to return to menu or any key to add more money");
				System.out.println("-----------------------------------------------------------------------------");
				x = answer.nextLine();
			} catch (NumberFormatException ex) {
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("   You have provided unacceptable amount! We only accept $1, $2, $5 or $10!");
				System.out.println("-----------------------------------------------------------------------------");
			}
		}
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("                 Current Money Provided: $" + amountProvided);
		System.out.println("                 Current Balance: $" + currentBalance);
		System.out.println("-----------------------------------------------------------------------------");
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(destination, true));
			writer.println(currentDateTime + " FEED MONEY: $" + reportBalance + " $" + currentBalance);
			writer.flush();
		} catch(IOException e) {
			System.out.println("There is an error: " + e);
		}
	}

	//Purchasing
	public void selectProduct(){
		if (currentBalance > 0) {
			System.out.println("-----------------------------------------------------------------------------");
			displayInventory();
			Scanner scan = new Scanner(System.in);
			String userInput = "";
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("                    Please enter a slot number");
			System.out.println("-----------------------------------------------------------------------------");
			userInput = scan.nextLine();
			if (!inventory.containsKey(userInput)) {
				System.out.println("-----------------------------------------------------------------------------");
				System.out.println("                     Invalid slot number");
				System.out.println("-----------------------------------------------------------------------------");
			} else {
				//Grabs the items
				Item item = inventory.get(userInput);
				//Check to see if balance is greater than 0
				if (item.getItemCount() > 0) {
					//Check to see if the current price is enough to make the purchase
					if (getCurrentBalance() >= item.getPrice()) {
						currentBalance -= item.getPrice();
						System.out.println("-----------------------------------------------------------------------------");
						System.out.println("                   " + item.getMessage());
						System.out.println("-----------------------------------------------------------------------------");
						System.out.print(item.getName() + " | " + item.getPrice() + " | " + "Your remaining balance is: $");
						System.out.printf(" %.2f", currentBalance);
						System.out.println(" ");
						System.out.println("-----------------------------------------------------------------------------");
						try {
							PrintWriter writer = new PrintWriter(new FileWriter(destination, true));
							writer.println(">" + currentDateTime + " " + item.getName() + " " + item.getSlotNum() + " $" + item.getPrice());
							writer.flush();
						} catch(IOException e) {
							System.out.println("There is an error: " + e);
						}
					} else {
						System.out.println("-----------------------------------------------------------------------------");
						System.out.println("                           Not enough money");
						System.out.println("-----------------------------------------------------------------------------");
					}
				} else {
					System.out.println("-----------------------------------------------------------------------------");
					System.out.println("                     This product is sold out");
					System.out.println("-----------------------------------------------------------------------------");
				}
			}
		}else {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("                Your balance is $0. Please add money first!");
			System.out.println("-----------------------------------------------------------------------------");
		}
	}

	//Finish Transaction Method
	public void finishTransaction(){

		double remainingBalance = currentBalance;
		//converts double in 2 decimal places
		DecimalFormat df2 = new DecimalFormat("#.##");
		//Returns the customer balancer back in coins
		int quarters = (int)(currentBalance/.25);
		currentBalance %= .25;
		int dimes = (int)(currentBalance/.10);
		currentBalance %= .1;
		int nickles = (int)(currentBalance/.05);

		//Prints the customers change back in coins
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("                      Quarters: " + quarters);
		System.out.println("                      Dimes: " + dimes);
		System.out.println("                      Nickles: " + nickles);
		System.out.println("-----------------------------------------------------------------------------");

		currentBalance = 0.00;//Return the current balance to 0.00
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("                  Current Balance: $"+currentBalance);
		System.out.println("-----------------------------------------------------------------------------");

		try {
			PrintWriter writer = new PrintWriter(new FileWriter(destination, true));
			writer.println(">" + currentDateTime + " " + "GIVE CHANGE: $" + df2.format(remainingBalance) + " $" + currentBalance);
			writer.flush();
		} catch(IOException e) {
			System.out.println("There is an error: " + e);
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














