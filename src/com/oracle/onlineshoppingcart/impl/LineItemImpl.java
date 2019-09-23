package com.oracle.onlineshoppingcart.impl;

import com.oracle.onlineshoppingcart.LineItem;

public class LineItemImpl implements LineItem{
	
	private String name;
	private int quantity;
	
	public LineItemImpl(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}	
	
	/*
	 *  {@inheritDoc}
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/*
	 *  {@inheritDoc}
	 */
	@Override
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		
	}	

}
