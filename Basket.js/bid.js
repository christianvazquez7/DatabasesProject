/**
 * New node file
 */
module.exports =  { 
	Bid : function (bidder, day,month,year,hour,minute, ammount)
	{
		
		this.bidder=bidder;
		this.day=day;
		this.month=month;
		this.year=year;
		this.hour=hour;
		this.minute=minute;
		this.ammount=ammount;
	}
};