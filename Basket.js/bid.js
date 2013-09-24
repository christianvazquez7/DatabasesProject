/**
 * New node file
 */
module.exports =  { 
	bid : function (bidder, day,month,year,hour,minute, ammount)
	{
		this.id = "";
		this.bidder=bidder;
		this.day=day;
		this.month=month;
		this.year=year;
		this.hour=hour;
		this.minute=minute;
		this.ammount=ammount;
	}
};