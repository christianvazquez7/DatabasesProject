package com.basket.containers;

import java.util.ArrayList;

import com.basket.general.Adress;

public class AddressContainer {
	
	public static Adress shippingSelection;
	private static AddressContainer sessionAddressContainer;
	public static ArrayList<Adress> userAddresses;
	private AddressContainer(){
		userAddresses=new ArrayList<Adress>();
		Adress tmp = new Adress();
		tmp.setCountry("PR");
		tmp.setLine1("123 cat avenue");
		tmp.setLine2("Barceloneta");
		tmp.setZipCode(00617);
		userAddresses.add(tmp);
		shippingSelection = null;
	}
	public static AddressContainer getAddressContainer(){
		if (sessionAddressContainer==null){
			sessionAddressContainer=new AddressContainer();
			
		}
		
		return sessionAddressContainer;
	}
}
