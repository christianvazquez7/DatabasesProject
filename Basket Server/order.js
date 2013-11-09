
module.exports =  
{ 
	Order : function (sDate, creditCard,account,buyEvents,shipAdress,bidEvent)
	{
		
		this.creditCard=creditCard;
		this.account=account;
		this.buyEvents=buyEvents;
		this.shipAdress=shipAdress;
		this.sDate=sDate;
		this.bidEvent=bidEvent;
	}
};
