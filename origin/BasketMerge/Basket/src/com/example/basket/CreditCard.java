package com.example.basket;

import com.basket.general.Adress;

public class CreditCard 
{

	private int cardId,cardNo,expDay,expYear,secCode;
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public int getExpDay() {
		return expDay;
	}
	public void setExpDay(int expDay) {
		this.expDay = expDay;
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
	private String name;
	private Adress billing;
}
