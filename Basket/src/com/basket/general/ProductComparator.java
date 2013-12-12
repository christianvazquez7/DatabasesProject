package com.basket.general;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product>
{

	private int compareBy;
	private int increasing;
	public int compare(Product arg0, Product arg1) {
		if(increasing==1)
		{
			if(compareBy==2)
			{
				return arg1.getManufacturer().compareTo(arg0.getManufacturer());
			}
			else
			{
				return arg1.getName().compareTo(arg0.getName());
			}
		}
		else	
		{
			if(compareBy==2)
			{
				return arg0.getManufacturer().compareTo(arg1.getManufacturer());
			}
			else
			{
				return arg0.getName().compareTo(arg1.getName());
			}
		}
	}
	//1:name 2:manufacturer
	public void setComparationMode(int mode)
	{
		compareBy=mode;
	}
	public void enableIncrease(int mode)
	{
		increasing=mode;
	}

}
