package com.basket.general;

import java.util.ArrayList;

public class Order {
	public CreditCard paymentOption;
	public Adress shipAdress;
	public ArrayList<BuyEvent> productsinorder;
	public Order(){
		productsinorder = new ArrayList<BuyEvent>();
	}
	public CreditCard getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(CreditCard paymentOption) {
		this.paymentOption = paymentOption;
	}
	public Adress getShipAdress() {
		return shipAdress;
	}
	public void setShipAdress(Adress shipAdress) {
		this.shipAdress = shipAdress;
	}
	public ArrayList<BuyEvent> getProductsinorder() {
		return productsinorder;
	}
	public void setProductsinorder(ArrayList<BuyEvent> productsinorder) {
		this.productsinorder = productsinorder;
	}
	
}
