package com.example.basket;

import java.util.ArrayList;

import Adapters.AdminProductAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class AdminProductList extends ProductListFragment
{
	private ArrayList<Product> foundProducts;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		foundProducts= new ArrayList<Product>();
		super.onCreate(savedInstanceState);
		getActivity().setTitle("Product List");
		
		AdminProductAdapter adapter = new AdminProductAdapter(this.getActivity(),foundProducts);
		AdminSession.setProducts(foundProducts);
		this.setListAdapter(adapter);
	}
	public void onListItemClick(ListView l, View v, int pos ,  long id )
	{
		
		Intent productSales = new Intent(this.getActivity(),ProductSales.class);
		productSales.putExtra("selected", pos);
		this.startActivity(productSales);
	}
	public void clear()
	{
		foundProducts.clear();
	}
	
	public void addProduct(Product product)
	{
		foundProducts.add(product);
	}
}
