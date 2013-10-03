package com.basket.containers;

import java.util.ArrayList;
import java.util.HashMap;

import com.basket.general.Adress;
import com.basket.general.CreditCard;

public class CreditCardContainer {
	
	public static CreditCard paymentSelection;
	private static CreditCardContainer sessionCreditCardContainer;
	public static ArrayList<CreditCard> userCreditCards;
	private CreditCardContainer(){
		userCreditCards=new ArrayList<CreditCard>();
		userCreditCards.add(new CreditCard("Bob", new Adress("Hello Street", "World Sector", "This City", "this STate", 85263, "USA"), 4334450005928377L, 2016, 05));
		paymentSelection = null;
	}
	public static CreditCardContainer getCreditCardContainer(){
		if (sessionCreditCardContainer==null){
			sessionCreditCardContainer=new CreditCardContainer();
			
		}
		
		return sessionCreditCardContainer;
	}
}
