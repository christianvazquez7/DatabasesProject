package com.basket.general;

public class Address {
	private String shippingAddress;

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String ShippingAddress) {
		this.shippingAddress = ShippingAddress;
	}

	public Address(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
}
