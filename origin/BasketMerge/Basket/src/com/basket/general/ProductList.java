package com.basket.general;

import java.util.List;

public class ProductList
{
	public List< Product > products;

	public List< Product > getResults() {
		return products;
	}

	public void setResults( List< Product > results ) {
		this.products = results;
	}


	public String toString() 
	{
		return "ProductList [products=" + products + "]";
	}
}
