package com.basket.general;


public class CreditCard 
{

	private int cardId,expMonth,expYear,secCode;
	private long cardNum;
	private String name;
	private Adress billing;
//	private String bankName;
	
	public CreditCard(){};

	public CreditCard(String nameOnCard, Adress billingAddress, long cardNumber, int expYear, int expDate) {
		this.name = nameOnCard;
		this.billing = billingAddress;
		this.cardNum = cardNumber;
		this.expYear = expYear;
		this.expMonth = expDate;
	}

	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	public long getCardNum() {
		return cardNum;
	}
	public void setCardNum(long cardNum) {
		this.cardNum = cardNum;
	}
	public int getExpMonth() {
		return expMonth;
	}
	public void setExpDay(int expMonth) {
		this.expMonth = expMonth;
	}
	public int getExpYear() {
		return expYear;
	}
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	public int getSecCode() {
		return secCode;
	}
	public void setSecCode(int secCode) {
		this.secCode = secCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Adress getBilling() {
		return billing;
	}
	public void setBilling(Adress billing) {
		this.billing = billing;
	}

}
