package com.oracle.onlineshoppingcart.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.oracle.onlineshoppingcart.LineItem;
import com.oracle.onlineshoppingcart.Store;
import com.oracle.onlineshoppingcart.exceptions.ItemNotAvaiableInStore;
import com.oracle.onlineshoppingcart.exceptions.NotEnoughItemsInStock;
import com.oracle.onlineshoppingcart.itemfactory.LineItemFactory;

public class NetSuiteStoreImpl implements Store {
	// To maintain the items of store
	Map<String, StoreObject> itemsAvailableInStore = new HashMap<String, StoreObject>();
	// store`s shopping cart.
	Map<String, Integer> shoppingCart = new HashMap<String, Integer>();

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void addItemToCart(String name, int quantity) {
		try {
			// check empty string
			if (name.isEmpty()) {
				throw new IllegalArgumentException("Item Name can't be empty!");
			}

			if (quantity <= 0) {
				throw new IllegalArgumentException("Quantity can't be lesser or equal to 0!");
			}

			Objects.requireNonNull(name, "name can't be null!");

			// Check the item stock in store inventory
			int availableQuantity = getQuantityInStock(name);

			switch (availableQuantity) {
			case -1:
				throw new ItemNotAvaiableInStore("Item not available in store please select another item");

			case 0:
				throw new NotEnoughItemsInStock(
						"Not enough items are available, only " + availableQuantity + "items in stock");

			default:
				if (availableQuantity >= quantity) {
					if (shoppingCart.get(name) != null) {
						shoppingCart.put(name, shoppingCart.get(name) + quantity);
					} else {
						shoppingCart.put(name, quantity);
					}
					updateItemStock(availableQuantity - quantity, itemsAvailableInStore.get(name).getLineItem());
				} else {
					throw new NotEnoughItemsInStock(
							"Not enough items are available, only " + availableQuantity + " items in stock");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/*
	 * @{inheritDoc}
	 */
	@Override
	public void addItemToStore(String name, double price) {
		try {
			// check empty string
			if (name.isEmpty()) {
				throw new IllegalArgumentException("Item Name can't be empty!");
			}
			// check price for 0 or negative value
			if (price <= 0) {
				throw new IllegalArgumentException("Item Price can't be lesser or equal to 0!");
			}
			// check for null
			String itemName = Objects.requireNonNull(name, "Item Name can't be null!");
			double itemPrice = Objects.requireNonNull(price, "Item Price cant' be null!");

			// check item available in store
			StoreObject storeObject = isItemAvailableInStore(itemName);

			if (storeObject == null) {
				itemsAvailableInStore.put(name,
						new StoreObject(new LineItemFactory().getLineItem(itemName), itemPrice));
			} else {
				storeObject.setPrice(itemPrice);
				itemsAvailableInStore.put(name, storeObject);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public LineItem[] getCartItems() {
		LineItemImpl lineItem[] = new LineItemImpl[shoppingCart.size()];
		int counter = 0;
		for (Map.Entry<String, Integer> entry : shoppingCart.entrySet()) {
			lineItem[counter] = (LineItemImpl) itemsAvailableInStore.get(entry.getKey()).getLineItem();
			lineItem[counter].setQuantity(entry.getValue());
			counter++;
		}

		Arrays.sort(lineItem, (item1, item2) -> item1.getName().compareTo(item2.getName()));

		return lineItem;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public double getCartTotal() {
		double totalCartPrice = 0.0;

		for (Map.Entry<String, Integer> entry : shoppingCart.entrySet()) {
			totalCartPrice += itemsAvailableInStore.get(entry.getKey()).getPrice() * entry.getValue();
		}
		return totalCartPrice;
	}

	/*
	 * @param name
	 * 
	 * @return StoreObject: if item present with given "name", otherwise null
	 */
	private StoreObject isItemAvailableInStore(String name) {
		StoreObject storeObject = itemsAvailableInStore.getOrDefault(name, null);
		return storeObject;
	}

	/*
	 * @param name
	 * 
	 * @return quantity: if item present with given "name", otherwise -1
	 */
	private int getQuantityInStock(String name) {
		int quantity;

		StoreObject storeObject = isItemAvailableInStore(name);
		if (storeObject != null)
			quantity = storeObject.getLineItem().getQuantity();
		else
			quantity = -1;
		return quantity;
	}

	/*
	 * @param quantity: items left after adding to cart
	 * 
	 * @param lineItem: LineItem object to update the item quantity in store list
	 */
	private void updateItemStock(int quantity, LineItem lineItem) {
		((LineItemImpl) lineItem).setQuantity(quantity);
	}

	// Default constructor
	public NetSuiteStoreImpl() {

	}

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
