package com.basket.general;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product 
{
	int productPId;
	

	public int getProductPId() {
		return productPId;
	}
	public void setProductPId(int productPId) {
		this.productPId = productPId;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getName() {
		return pname;
	}
	public void setName(String name) {
		this.pname = name;
	}
	String dimensions;
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	int width;
	int height;
	@Override
	public String toString() {
		return "Product [ISBN=" + productPId + ", width=" + width + ", height="
				+ height + ", depth=" + depth + ", pname=" + pname
				+ ", manufacturer=" + manufacturer + "]";
	}

	int depth;
	String pname;
	String manufacturer;

	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
}
