/**
 * New node file

 */
module.exports =  { 
	User : function (email,username, password,billingAdress,shippingAdress,baskets,currentlyBiddingOn,currentlySellingOnBid,currentlySellingOnBuy, creditCards)
	{
		

		
		this.billingAdress=billingAdress;
		this.shippingAdress=shippingAdress;
		this.baskets= baskets;
		this.currentlyBiddingOn=currentlyBiddingOn;
		this.currentlySellingOnBid=currentlySellingOnBid;
		this.currentlySellingOnBuy= currentlySellingOnBuy;
		this.email=email;
		this.username=username;
		this.password=password;
		this.creditCards=creditCards;
		
	}
}