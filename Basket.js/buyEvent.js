/**
 * New node file
 */
module.exports ={
		BuyEvent: function(product,price,day,year,month,hour,minute,finalized)
		{
			this.product=product;
			this.price= price;
			this.day= day;
			this.year= year;
			this.month= month;
			this.hour= hour;
			this.minute= minute;
			this.finalized=finalized;
		}
};