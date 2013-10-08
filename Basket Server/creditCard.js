/**
 * New node file
 */

module.exports = {
		CreditCard: function(cardId,cardNum,expMonth,expYear,secCode,name,billing)
		{
			this.cardId=cardId;
			this.cardNum=cardNum;
			this.expMonth=expMonth;
			this.expYear=expYear;
			this.secCode=secCode;
			this.name=name;
			this.billing=billing;
		}
};
