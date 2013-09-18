package com.example.basket;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

public class AdminProductList extends ProductListFragment
{
	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		Intent productSales = new Intent(this.getActivity(),ProductSales.class);
		this.startActivity(productSales);
	}
}
