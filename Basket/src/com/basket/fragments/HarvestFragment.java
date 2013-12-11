package com.basket.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basket.activities.BidEventPageActivity;
import com.basket.containers.BasketSession;
import com.basket.general.Bid;
import com.basket.general.BidEvent;
import com.basket.general.BooleanContainer;
import com.basket.general.CarJsonSpringAndroidSpiceService;
import com.basket.restrequest.BidRequest;
import com.basket.restrequest.CurrentWinningRequest;
import com.example.basket.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class HarvestFragment extends Fragment
{
	private SpiceManager spiceManager = new SpiceManager(CarJsonSpringAndroidSpiceService.class);
	private View view;
	Bid newBid;
	private BidEvent event;
	private TextView seller;
	private TextView winningBid;
	private TextView nextBid;
	private TextView nextTime;
	private String currentTime;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.harvest_layout,
				container, false);


		event = (BidEvent) BasketSession.getProductSearch().get(this.getActivity().getIntent().getIntExtra("selectedEvent", 0));
		 seller =(TextView)view.findViewById(R.id.Seller);
		seller.setText(event.getCreator());

		 winningBid =(TextView)view.findViewById(R.id.tvCatTitle);
		if(event.getWinningBid()!=null)
		winningBid.setText(Double.toString(event.getWinningBid().getAmmount()));
		else
			winningBid.setText(Double.toString(event.getStartingBid()));


		 nextBid =(TextView)view.findViewById(R.id.TextView02);
		 nextTime =(TextView)view.findViewById(R.id.pricemybasket);
		if(event.getWinningBid()!=null){
		nextBid.setText(Double.toString(event.getWinningBid().getAmmount()+event.getMinBid()));
		
		java.util.Date date = null;
		
		SimpleDateFormat formatter, FORMATTER;
		formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String oldDate = event.getEndingTime();
		 try {
			date = formatter.parse(event.getWinningBid().getBidTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		if(date!=null)
		 nextTime.setText(FORMATTER.format(date));
		else
			nextTime.setText(event.getWinningBid().getBidTime());
		

		}
		else{
			nextBid.setText(Double.toString(event.getStartingBid()+event.getMinBid()));
			nextTime.setText("");

		}
		




		Button harvest = (Button) view.findViewById(R.id.harvest);
		harvest.setOnClickListener(new OnClickListener()
		{


			

			public void onClick(View arg0) 
			{
				try 
				{
					TextView ammountT = (TextView)view.findViewById(R.id.etCategoryPage);
					double ammount =Double.parseDouble(ammountT.getText().toString());
					Calendar c = Calendar.getInstance(); 
					int minute = c.get(Calendar.MINUTE);
					int hour = c.get(Calendar.HOUR);
					int day = c.get(Calendar.DAY_OF_MONTH);
					int month= c.get(Calendar.MONTH);
					int year = c.get(Calendar.YEAR);
					int second= c.get(Calendar.SECOND);
				

					java.util.Date dt = new java.util.Date();

					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
					 currentTime = sdf.format(dt);
					double current;
					if(event.getWinningBid()!=null)
						current=event.getWinningBid().getAmmount();
					else
						current=event.getStartingBid();
					//arreglar aqui!!
					if(ammount<current+event.getMinBid())
					{
						Toast.makeText(HarvestFragment.this.getActivity(), "Bid needs to be higher than minumum or current", Toast.LENGTH_LONG).show();
					}
					else{
						newBid = new Bid(ammount, day, month, year, hour, minute, BasketSession.getUser().getUsername());
						newBid.setDate(currentTime);
						if (!spiceManager.isStarted())
						{
							spiceManager.start(getActivity());
							BidRequest JsonSpringAndroidRequest = new BidRequest(newBid,event);
							spiceManager.execute(JsonSpringAndroidRequest, "", DurationInMillis.ALWAYS_EXPIRED, new BidListener());
						}
					}
				}
				catch( NumberFormatException e)
				{
					Toast.makeText(getActivity(), "Error in Bid", Toast.LENGTH_SHORT).show();
					if (spiceManager.isStarted())
						spiceManager.shouldStop();
				}

			}


		});
		return view;
	}
	private class BidListener implements RequestListener<BooleanContainer>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "Bid Failed", Toast.LENGTH_SHORT).show();
				spiceManager.shouldStop();
			}
			Toast.makeText(getActivity(), "Bid Failed", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		


		}

		@Override
		public void onRequestSuccess(BooleanContainer bool) 
		{
			
			spiceManager.shouldStop();
			if(bool.getState())
			{
				boolean found=false;
				for (BidEvent b: BasketSession.getUser().getCurrentlyBiddingOn()){
					if (b.getId()==event.getId())
					{
						b.setWinningBid(newBid);
						found=true;
					}
				}
				if (!found)
					BasketSession.getUser().getCurrentlyBiddingOn().add(event);
				winningBid.setText(Double.toString(newBid.getAmmount()));
				nextTime.setText(currentTime);
				nextBid.setText(Double.toString(newBid.getAmmount()+event.getMinBid()));
				BidEventPageActivity a =(BidEventPageActivity)getActivity();
				a.updateBid(newBid);
				if(event.getWinningBid()!=null)
				{
				event.getWinningBid().setAmmount(newBid.getAmmount());
				event.getWinningBid().setBidTime(currentTime);
				event.getWinningBid().setDate(currentTime);
				}
				else{
					Bid win = new Bid();
					win.setAmmount(newBid.getAmmount());
					win.setBidTime(currentTime);
					win.setDate(currentTime);
					event.setWinningBid(win);

				}
				
				
				Toast.makeText(getActivity(), "Bid Posted", Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(getActivity(), "Please try again, someone beat that bid from you!", Toast.LENGTH_SHORT).show();
				spiceManager.start(getActivity());
				CurrentWinningRequest update = new CurrentWinningRequest(event.getId());
				spiceManager.execute(update, "", DurationInMillis.ALWAYS_EXPIRED, new BidLis());

			}

		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
	
	private class BidLis implements RequestListener<Bid>, RequestProgressListener {

		@Override
		public void onRequestFailure(SpiceException arg0) {

			Log.d("error",arg0.getMessage());
			if (!(arg0 instanceof RequestCancelledException)) {

				Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
				spiceManager.shouldStop();
			}
			Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
			if(spiceManager.isStarted())
				spiceManager.shouldStop();
		


		}

		@Override
		public void onRequestSuccess(Bid winning) 
		{

			spiceManager.shouldStop();
			boolean found=false;
			for (BidEvent b: BasketSession.getUser().getCurrentlyBiddingOn()){
				if (b.getId()==event.getId())
				{
					b.setWinningBid(winning);
					found=true;
				}
			}
			if (!found)
				BasketSession.getUser().getCurrentlyBiddingOn().add(event);
			winningBid.setText(Double.toString(winning.getAmmount()));
			
			
			SimpleDateFormat formatter, FORMATTER;
			formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			String oldDate = event.getEndingTime();
			java.util.Date date = null;
			 try {
				date = formatter.parse(winning.getBidTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FORMATTER = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			 nextTime.setText(FORMATTER.format(date));
			
			//Verificar si winning es nulo o no
			nextBid.setText(Double.toString(winning.getAmmount()+event.getMinBid()));
			event.getWinningBid().setAmmount(winning.getAmmount());
			event.getWinningBid().setBidTime(currentTime);
			BidEventPageActivity a =(BidEventPageActivity)getActivity();
			a.updateBid(winning);
		}

		@Override
		public void onRequestProgressUpdate(RequestProgress arg0) 
		{

		}
	}
}
