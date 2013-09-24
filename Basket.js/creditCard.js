/**
 * New node file
 */

module.exports = {
		CreditCard: function(cardId,cardNo,expDay,expYear,secCode,name,billing)
		{
			this.cardId=cardId;
			this.cardNo=cardNo;
			this.expDay=expDay;
			this.expYear=expYear;
			this.secCode=secCode;
			this.name=name;
			this.billing=billing;
		}
};
