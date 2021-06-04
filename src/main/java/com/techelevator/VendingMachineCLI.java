package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.IOException;
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
	private static final String PURCHASE_MAIN_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MAIN_OPTION_PURCHASE = "Select Product";
	private static final String PURCHASE_MAIN_OPTION_FINISH = "Finish Transaction";
	private static final String [] PURCHASE_MENU_OPTION = {PURCHASE_MAIN_OPTION_FEED_MONEY,PURCHASE_MAIN_OPTION_PURCHASE,PURCHASE_MAIN_OPTION_FINISH};


	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

	getVendingMachineStocked();


		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				getInventory();
				// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
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
		double currentBalance = 0.00;
		String  x = "Y";
		while(x != "N") {
			System.out.println("How much are you entering");
			amount = scan.nextInt();
			if (amount == 1) {
				currentBalance += amount;
			} else if (amount == 2) {
				currentBalance += amount;
			} else if (amount == 5) {
				currentBalance += amount;
			} else if (amount == 10) {
				currentBalance += amount;
			}
			System.out.println("Do you want to enter another amount (Y/N)");
			x = scan.nextLine();
		}
		System.out.println("Current Money Provided: $"+currentBalance);
	}
	//Purchase
	public void SelecProduct(){
	}

	public void getInventory() {
		for(String key : inventory.keySet()) {
			System.out.println(inventory.get(key).toString());
		}
	}

}
