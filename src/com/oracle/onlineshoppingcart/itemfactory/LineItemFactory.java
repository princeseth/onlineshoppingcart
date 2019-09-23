package com.oracle.onlineshoppingcart.itemfactory;

import com.oracle.onlineshoppingcart.LineItem;
import com.oracle.onlineshoppingcart.impl.LineItemImpl;

/*
 * Factory class for LineItem`s instance  
 */
public class LineItemFactory {

	private int QUANTITY = 10;

	/*
	 * @param: name
	 */
	public LineItem getLineItem(String name) {

		if (name == null) {
			return null;
		}
		return new LineItemImpl(name, QUANTITY);
	}
	
	public void updateQuantity(LineItemImpl lineItem, int quantity) {
		lineItem.setQuantity(quantity);
	}
}
