package com.product;

public class ProductNotEnoughException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5697463637566547667L;

	public ProductNotEnoughException(String message) {
		super(message);
	}
}
