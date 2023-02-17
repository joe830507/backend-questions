package com.order;

public class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7192470797353336349L;

	public ProductNotFoundException(String message) {
		super(message);
	}

}
