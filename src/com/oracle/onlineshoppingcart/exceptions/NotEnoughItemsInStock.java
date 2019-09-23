package com.oracle.onlineshoppingcart.exceptions;

public class NotEnoughItemsInStock extends Exception{

	public NotEnoughItemsInStock(String message) {
		super(message);
	}
}
