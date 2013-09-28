package com.basket.containers;

import java.util.ArrayList;
import java.util.HashMap;

import com.basket.general.Address;

public class AddressContainer {
	
	public static Address shippingSelection;
	private static AddressContainer sessionAddressContainer;
	public static ArrayList<Address> userAddresses;
	private AddressContainer(){
		userAddresses=new ArrayList<Address>();
		userAddresses.add(new Address("123 cat avenue"));
		shippingSelection = null;
	}
	public static AddressContainer getAddressContainer(){
		if (sessionAddressContainer==null){
			sessionAddressContainer=new AddressContainer();
			
		}
		
		return sessionAddressContainer;
	}
}
