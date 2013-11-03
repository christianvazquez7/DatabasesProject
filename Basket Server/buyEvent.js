/**
 * New node file
 */
module.exports ={
		BuyEvent: function(product,price,day,year,month,hour,minute,finalized,features,description,reviews,id,ammount)
		{
			this.product=product;
			this.price= price;
			this.day= day;
			this.year= year;
			this.month= month;
			this.hour= hour;
			this.minute= minute;
			this.finalized=finalized;
			this.features=features;
			this.description=description;
			this.reviews=reviews;
			this.id=id;
			this.ammount = ammount;
		}
};