package com.basket.general;

public class CreditCard {
	private String  nameOnCard, billingAddress;
	public int getSecNum() {
		return secNum;
	}
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}
	private int expYear, expMonth, secNum ;
	private long cardNumber;
	public CreditCard(String nameOnCard, String billingAddress, long cardNumber,
			int expYear, int expDate) {
		this.nameOnCard = nameOnCard;
		this.billingAddress = billingAddress;
		this.cardNumber = cardNumber;
		this.expYear = expYear;
		this.expMonth = expDate;
	}
	public String getNameOnCard() {
		return nameOnCard;
	}
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getExpYear() {
		return expYear;
	}
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	public int getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(int expDate) {
		this.expMonth = expDate;
	}

}
