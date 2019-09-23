package com.oracle.onlineshoppingcart;

import java.util.Scanner;

import com.oracle.onlineshoppingcart.impl.NetSuiteStoreImpl;

public class Test {
	/*
	 * Main function to run the online store
	 */
	public static void main(String[] args) {

		NetSuiteStoreImpl obj = new NetSuiteStoreImpl();

		while (true) {
			System.out.println("Enter number from 1 to 4 to select below action");
			System.out
					.println(" 1. Add Item to store \n 2. Add Item to cart \n 3. Get cart total \n 4. Get cart items ");
			Scanner sc = new Scanner(System.in);
			int input = sc.nextInt();

			if (input == 1) {
				System.out.println("Enter Item Name: ");
				String item = sc.next();
				System.out.println("Enter Item Price: ");
				double price = sc.nextDouble();
				obj.addItemToStore(item, price);
				// System.out.println("'" + item + "' has been added to the store \n\n");
			}

			if (input == 2) {
				System.out.println("Enter Item Name: ");
				String item = sc.next();
				System.out.println("Enter Item Quantity: ");
				int quantity = sc.nextInt();
				obj.addItemToCart(item, quantity);
				// System.out.println("'"+item + "' added to the cart \n\n");
			}

			if (input == 3) {
				System.out.println("Cart total:" + obj.getCartTotal());
				System.out.println("\n");
			}

			if (input == 4) {
				System.out.println("Cart items:\n");
				LineItem[] lineItems = obj.getCartItems();

				if (lineItems.length == 0)
					System.out.println("No items available in cart");
				else
					for (LineItem lineItem : lineItems) {
						System.out.println("Item Name: " + lineItem.getName() + "\n" + "Quantity:"
								+ lineItem.getQuantity() + "\n---*******---");
					}

				System.out.println("\n");
			}
		}

	}
}
