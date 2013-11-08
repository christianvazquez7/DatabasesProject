package com.basket.containers;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.basket.general.BuyEvent;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {
	private String title;
	private BuyEvent eve;
	
	public BuyEvent getEve() {
		return eve;
	}
	public void setEve(BuyEvent eve) {
		this.eve = eve;
	}
	public String getTitle() {
		return title;
	}
	@Override
	public String toString() {
		return "Deal [title=" + title + ", eve=" + eve + "]";
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
