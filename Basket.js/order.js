
module.exports =  
{ 
	Order : function (day , month, year, creditCard,account,buyEvents,shipAdress)
	{
		this.day=day;
		this.month=month;
		this.year=year;
		this.creditCard=creditCard;
		this.account=account;
		this.buyEvents=buyEvents;
		this.shipAdress=shipAdress;
	}
};
