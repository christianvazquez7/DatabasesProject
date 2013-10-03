package com.basket.fragments;

import com.example.basket.R;
import com.example.basket.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HarvestFragment extends Fragment
{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		    View view = inflater.inflate(R.layout.harvest_layout,
		        container, false);
		    return view;
		  }
}
