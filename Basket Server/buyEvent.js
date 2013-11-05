/**
 * New node file
 */
module.exports ={
		BuyEvent: function(product,price,sdate,finalized,features,description,id,creator,rating,btitle,pic)
		{
			this.product=product;
			this.price= price;
			this.sdate=sdate;
			this.finalized=finalized;
			this.features=features;
			this.description=description;
			this.id=id;
			this.creator=creator;
			this.rating=rating;
			this.btitle=btitle;
			this.pic=pic;
		}
};