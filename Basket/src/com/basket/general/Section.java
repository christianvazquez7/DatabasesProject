package com.basket.general;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private String title;
    private boolean isBold;
    private ArrayList<Category> subsub;
    
	public ArrayList<Category> getSubsub() {
		return subsub;
	}

	public void setSubsub(ArrayList<Category> subsub) {
		this.subsub = subsub;
	}

	private List<SectionItem> sectionItems = new ArrayList<SectionItem>();

    public Section(String title) {
        this.title = title;
        this.isBold = false;
    }

    public void addSectionItem(long id, String title, String icon) {
        this.sectionItems.add( new SectionItem(id, title, icon));
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<SectionItem> getSectionItems() {
        return sectionItems;
    }
    
    public void setSectionItems(List<SectionItem> sectionItems) {
        this.sectionItems = sectionItems;
    }
    
    public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	public void addSectionItem(int i, String name, String string,
			ArrayList<Category> child) {
		SectionItem temp = new SectionItem(i, name, string);
		temp.setSubsub(child);
		this.sectionItems.add( temp);
		
	}

}