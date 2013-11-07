package com.basket.general;

import java.util.ArrayList;

public class SectionItem {

    private long id;
    private String title;
    private String icon;
    private ArrayList<Category> subsub;
    public ArrayList<Category> getSubsub() {
		return subsub;
	}

	public void setSubsub(ArrayList<Category> subsub) {
		this.subsub = subsub;
	}

	public SectionItem(long id, String title, String icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
