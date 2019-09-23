package com.oracle.onlineshoppingcart.impl;

import com.oracle.onlineshoppingcart.LineItem;

public class StoreObject {

	private LineItem lineItem;
	private double price;
	
	public LineItem getLineItem() {
		return lineItem;
	}
	public void setLineItem(LineItem lineItem) {
		this.lineItem = lineItem;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double itemPrice) {
		this.price = itemPrice;
	}
	public StoreObject(LineItem lineItem, double price) {		
		this.lineItem = lineItem;
		this.price = price;
	}
		
}
