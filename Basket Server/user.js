/**
 * New node file

 */
module.exports =  { 
	User : function (email,username, password,billingAdress,shippingAdress,baskets,currentlyBiddingOn,currentlySellingOnBid,currentlySellingOnBuy, creditCards,userOrders,rating,name,lastName,userId)
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
		this.userOrders=userOrders;
		this.rating=rating;
		this.name=name;
		this.lastName=lastName;
		this.userId=userId;
	}
}