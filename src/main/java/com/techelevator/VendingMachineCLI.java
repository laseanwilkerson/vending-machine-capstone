package com.techelevator;

import com.sun.source.util.SourcePositions;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private Map<String, Object> inventory = new HashMap<String, Object>();

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "EXIT";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	//Purchase menu functions
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH = "Finish Transaction";
	private static final String [] PURCHASE_MENU_OPTION = { PURCHASE_MENU_OPTION_FEED_MONEY,PURCHASE_MENU_OPTION_SELECT_PRODUCT,PURCHASE_MENU_OPTION_FINISH };


	private Menu menu;
	private double currentBalance = 0.00;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;

	}

	public void run() {

	getVendingMachineStocked();


		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				getInventory();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				while(true) {
					String choicePurchase = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTION);

					if (choicePurchase.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						feedMe();

					} else if (choicePurchase.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {

						//SELECT PRODUCT

					} else if (choicePurchase.equals(PURCHASE_MENU_OPTION_FINISH)) {
						System.out.println("Thank you! Come back soon!");
						System.exit(1);
					}

				}

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Thank you! Come back soon!");
				System.exit(1);
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	private void getVendingMachineStocked() {
		String path = "vendingmachine.csv";
		File inputFile = new File(path);
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
	}

	public void feedMe(){
		Scanner scan = new Scanner(System.in);
		int amount;
		int amountProvided = 0;
		String  x = "Y";

		while(!x.equals("0")) {
			System.out.println("Current Balance: $" + currentBalance);
			System.out.println("How much are you entering");
			amount = scan.nextInt();
			if (amount == 1) {
				currentBalance = getCurrentBalance() + amount;
			} else if (amount == 2) {
				currentBalance = getCurrentBalance() + amount;
			} else if (amount == 5) {
				currentBalance = getCurrentBalance() + amount;
			} else if (amount == 10) {
				currentBalance = getCurrentBalance() + amount;
			} else {
				System.out.println("You have provided unacceptable amount! We only accept $1, $2, $5 or $10!");
			}

			Scanner answer = new Scanner(System.in);
			System.out.println("Press 0 to return to menu or any key to add more money");
			x = answer.nextLine();
			amountProvided += amount;
		}
		System.out.println("Current Money Provided: $" + amountProvided);
		System.out.println("Current Balance: $" + currentBalance);
	}
	//Purchase
	public void SelectProduct(){

	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void getInventory() {
		for(String key : inventory.keySet()) {
			System.out.println(inventory.get(key).toString());
		}



	}

}
