/**
 * New node file
 */
module.exports =
{
		BidEvent: function(product,startingBid,sday,syear,smonth,shour,sminute,fday,fyear,fmonth,fhour,fminute,features,description,reviews,bids,winning,creator,minBid)
		{
			this.product=product;
			this.startingBid= startingBid;
			
			this.sday= sday;
			this.syear= syear;
			this.smonth= smonth;
			this.shour= shour;
			this.sminute= sminute;
			
			this.fday= fday;
			this.fyear= fyear;
			this.fmonth= fmonth;
			this.fhour= fhour;
			this.fminute= fminute;
			
			this.creator=creator;
			
			this.minBid=minBid;
			this.bids=bids;
			this.winning = winning;
			
			this.features=features;
			this.description=description;
			this.reviews=reviews;

		}
};