/**
 * New node file
 */
module.exports =
{
		BidEvent: function(product,startingBid,startingTime,endingTime,features,description,minBid,id,creator,rating,bTitle,picture,winningBid,finished,accepted)
		{
			this.id=id;
			this.product=product;
			this.startingBid= startingBid;
			
			this.endingTime=endingTime;
			this.startingTime=startingTime;
			this.creator = creator;
			this.rating=rating;
			
			
			this.minBid=minBid;
			
			
			this.features=features;
			this.description=description;
			this.bidTitle=bTitle;
			
			
			this.picture=picture;
			this.winningBid=winningBid;
			this.finished=finished;
			this.accepted=accepted;
		}
};