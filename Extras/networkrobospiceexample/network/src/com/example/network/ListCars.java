package com.example.network;

import java.util.List;


public class ListCars {
	
    public List< Car > cars;

    public List< Car > getResults() {
        return cars;
    }

    public void setResults( List< Car > results ) {
        this.cars = results;
    }
}
