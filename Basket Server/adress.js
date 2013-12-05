/**
 * New node file
 */
module.exports = {
		Adress : function(line1,line2,country,zipCode,city,state,AddressId)
		{
			this.line1=line1;
			this.line2=line2;
			this.country=country;
			this.zipCode=zipCode;
			this.city=city;
			this.state=state;
			this.aid=AddressId;
		}
};
