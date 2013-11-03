package com.basket.general;

import java.util.Comparator;

public class EventComparator implements Comparator<Event>
{

	private int compareBy;
	private int increasing;
	public int compare(Event arg0, Event arg1) {
		if(increasing==1)
		{
			if (compareBy==0)
			{
				if (arg1.getAmount() < arg0.getAmount()) return -1;
		        if (arg1.getAmount() > arg0.getAmount()) return 1;
		        return 0;
			}
			else if(compareBy==2)
			{
				return arg1.getTitle().compareTo(arg0.getTitle());
			}
			else
			{
				return arg1.brand().compareTo(arg0.brand());
			}
		}else	
		{
		if (compareBy==0)
		{
			if (arg0.getAmount() < arg1.getAmount()) return -1;
	        if (arg0.getAmount() > arg1.getAmount()) return 1;
	        return 0;
		}
		else if(compareBy==2)
		{
			return arg0.getTitle().compareTo(arg1.getTitle());
		}
		else
		{
			return arg0.brand().compareTo(arg1.brand());
		}
		}
	}
	//0:amount 1:name 2:brand
	public void setComparationMode(int mode)
	{
		compareBy=mode;
	}
	public void enableIncrease(int mode)
	{
		increasing=mode;
	}

}
