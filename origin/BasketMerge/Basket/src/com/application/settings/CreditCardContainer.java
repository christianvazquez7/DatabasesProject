package com.application.settings;

import java.util.ArrayList;
import java.util.HashMap;

public class CreditCardContainer {
	
	public static CreditCard paymentSelection;
	private static CreditCardContainer sessionCreditCardContainer;
	public static ArrayList<CreditCard> userCreditCards;
	private CreditCardContainer(){
		userCreditCards=new ArrayList<CreditCard>();
		userCreditCards.add(new CreditCard("Bob", "123 Cat Avenue", 4334450005928377L, 2016, 05));
		paymentSelection = null;
	}
	public static CreditCardContainer getCreditCardContainer(){
		if (sessionCreditCardContainer==null){
			sessionCreditCardContainer=new CreditCardContainer();
			
		}
		
		return sessionCreditCardContainer;
	}
}
