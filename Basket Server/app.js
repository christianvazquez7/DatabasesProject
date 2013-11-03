/**
 * Module dependencies.
 */
var express = require('express');

var app = express();
var roduct = require("./product.js");
var product = roduct.product;

var orderJS= require("./order.js");
var Order =  orderJS.Order;

var userJS = require("./user.js");
var User= userJS.User;

var reviewJS = require("./review.js");
var Review= reviewJS.Review;

var creditCardJS = require("./creditCard.js");
var CreditCard=creditCardJS.CreditCard;

var adressJS = require ("./adress.js");
var Adress = adressJS.Adress;

var BasketJS = require("./basket.js");
var Basket = BasketJS.Basket;

var buyEventJS= require("./buyEvent.js");
var BuyEvent= buyEventJS.BuyEvent;

var bidEventJS = require ("./bidEvent.js");
var BidEvent = bidEventJS.BidEvent;

var bidJS = require("./bid.js");
var Bid = bidJS.Bid;

var reportJS= require("./report.js");
var Report = reportJS.Report;

var Q = require('Q');
var bb = require('bytebuffer');

var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'lukesionkira',
  password : 'qwerty',
  database: "myFirstSql"
});

connection.connect();


 
	
//	function getUserInfo (callback) {
//		var userquery= 'select Address.* from Address where userId=1 and addressId not in (select Address.id from'
//		connection.query(userquery, function(err, response) {
//			  if (err) throw err;
//			  callback(err, response);
//			});
//	};
//	
//	console.log('The solution is: ');
//	getUserInfo(function(err, result){
//		console.log(result);
//
//	});






var creditCardList = new Array(
		new CreditCard (0123,124567,05,2030,011,"Christian",new Adress("Urb. Catalana","casa #7","Barceloneta PR",00617)),
		new CreditCard (0456,123457,07,1992,044,"Juan",new Adress("Urb. Catalana","casa #7","Barceloneta PR",00617))
);
var shippingList = new Array(
		new Adress ("Urb. Catalana","casa #7","Barceloneta PR",00617),
		new Adress ("Urb. Montes","casa #35","San Juan PR",00687)
);
var billingList = new Array(
		new Adress("P.O. Box 41","","Barceloneta PR",00617)
);

var exproduct = new product("Alienware M17x", 1204054932,"Dell Inc.",20,15,40);
var exbproduct = new product("Macbook Pro", 1204054932,"Dell Inc.",20,15,40);

var buyEvents = new Array(
		new BuyEvent(exproduct,1700.50,1,2010,05,8,36,false),
		new BuyEvent(exproduct,1700.50,1,2010,05,8,36,false),
		new BuyEvent(exproduct,1700.50,1,2010,05,8,36,false)
);

var users = {};
var buylist = new Array();
var bids = new Array();
var bidlist = new Array();
var bidEvents = new Array();
var orderList= new Array();
var withbid= new Array();

var baskets= new Array(
		new Basket("basket example",buyEvents,buyEvents),
		new Basket("basket example 2",buyEvents,buyEvents)
);
var bidEvents2=new Array();
var userList = new Array(
		new User ("lukesionkira@hotmail.com","chris","qwerty",billingList,shippingList,baskets,buyEvents,bidEvents2,buyEvents, creditCardList,orderList),
		new User ("pedro.colon4@upr.edu","blabla","potatoes",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList),
		new User ("Wu@hotmail.com","Wuuu","Wuuuuuu",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList)
);
users["admin"]=new User ("pedro.colon4@upr.edu","blabla","potatoes",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList),

users["lukesionkira@hotmail.com"]= new User ("lukesionkira@hotmail.com","chris","qwerty",billingList,shippingList,baskets,withbid,bidEvents,buyEvents, creditCardList,orderList);
var  randId=1000;
var reviews = new Array();
var event= new BuyEvent(exproduct,1700.50,1,2010,05,8,36,false,"- Intel Core i7 3610QM \
		- 6 GB DDR3 \
		- 17.3-Inch Screen \
		- 1600x900","The all-new Alienware 14 is compact, powerful and designed for more intense gaming anywhere you set up. The magnesium alloy frame and anodized aluminum shell protect your LCD and its components, while the copper heat sinks keep your system cool on the inside so you can game for hours on end."
		,reviews,0);

buylist.push(event);

var bid = new Bid(users["lukesionkira@hotmail.com"], 2,5,2011,12,50, 300);
 

var biddy = new BidEvent(exbproduct,300,1,2011,5,9,35,21,2011,6,5,20,"- Intel Core i7 3610QM \
		- 6 GB DDR3 \
		- 17.3-Inch Screen \
		- 1600x900","The all-new Alienware 14 is compact, powerful and designed for more intense gaming anywhere you set up. The magnesium alloy frame and anodized aluminum shell protect your LCD and its components, while the copper heat sinks keep your system cool on the inside so you can game for hours on end.",
		reviews,bids,bid,userList[1],30,1);
bidlist.push(biddy);




var allowCrossDomain = function(req, res, next) 
{
	res.header('Access-Control-Allow-Origin', '*');
	res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS');
	res.header('Access-Control-Allow-Headers', 'Content-Type, Authorization, Content-Length, X-Requested-With');

	// intercept OPTIONS method
	if ('OPTIONS' == req.method) {
		res.send(200);
	}
	else {
		next();
	}
};
app.configure(function () {
	app.use(allowCrossDomain);
});


//all environments
app.set('port', process.env.PORT || 3000);
app.use(express.bodyParser());


//development only
if ('development' == app.get('env')) {
	app.use(express.errorHandler());
}

app.listen(process.env.PORT || 3412);
console.log("server listening");


//app.get('/Basket.js/buyEvent/:id', function(req, res) 
//		{
//
//	var response = {"Product" : ProductList[0]};
//	res.json(response);		
//
//		});

//Search for users
app.get('/Basket.js/AdminSearch/:searchQuery',function(req,res)
{
	var response =
	{
		"users": userList
	};
	res.json(response);
});

app.get('/Basket.js/GetBuyReviews/:id',function(req,res)
		{
	
	function getBuyReviews() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select * from Product_Reviews natural join reviews_buy_event natural join Buy_Events join Users on Product_Reviews.userId=Users.userId where buyEventId='+connection.escape(req.params.id);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getBuyReviews()]).then(function(rest)
		{
		console.log(rest[0][0]);	
		var reviewList= new Array();
		for (var i=0;i<rest[0][0].length;i++)
		{
			console.log(rest[0][0][i].title);
			console.log(rest[0][0][i].content);
//			console.log(review[0][0][i].username
			
		 reviewList.push(new Review(rest[0][0][i].title,rest[0][0][i].content,rest[0][0][i].username, rest[0][0][i].rrating));
		}
		console.log(reviewList);
		var response =
		{
			"reviews": reviewList
		};
		res.json(response);
			});
	
		
		});


app.get('/Basket.js/GetBidReviews/:id',function(req,res)
		{
	
	function getBuyReviews() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select * from Product_Reviews natural join reviews_bid_event natural join Bid_Events join Users on Product_Reviews.userId=Users.userId where bidEventId='+connection.escape(req.params.id);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getBuyReviews()]).then(function(rest)
		{
		console.log(rest[0][0]);	
		var reviewList= new Array();
		for (var i=0;i<rest[0][0].length;i++)
		{
			console.log(rest[0][0][i].title);
			console.log(rest[0][0][i].content);
//			console.log(review[0][0][i].username
			
		 reviewList.push(new Review(rest[0][0][i].title,rest[0][0][i].content,rest[0][0][i].username, rest[0][0][i].rrating));
		}
		console.log(reviewList);
		var response =
		{
			"reviews": reviewList
		};
		res.json(response);
			});
	
		
		});



app.get('/Basket.js/GetBids/:id',function(req,res)
		{
	
	function getBids() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select * from Bids natural join Users where bidEventId='+ connection.escape(req.params.id) + ' order by amount desc';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getBids()]).then(function(rest)
		{
				
		var bidList= new Array();
		for (var i=0;i<rest[0][0].length;i++)
		{
			bidList.push(new Bid(rest[0][0][i].username,rest[0][0][i].bidTime,rest[0][0][i].amount));
		}
		var response =
		{
			"bids": bidList
		};
		res.json(response);
			});
	
		
		});
//Edit a user
app.put('/Basket.js/UserEdit/:id/:usr/:pass/:email',function(req,res){
	console.log("Editing user");
	var buffUser =userList[req.params.id];
	buffUser.username=req.params.usr;
	buffUser.password=req.params.pass;
	buffUser.email=req.params.email;
	res.json(true);
});
//Register Device
app.put('/Basket.js/RegisterDevice/:id',function(req,res){
	console.log("Registering device");
	console.log(req.params.id);
	var found = false;
	for (var i=0; i<registrationIds.length; i++) {
		console.log(registrationIds[i].localeCompare(req.params.id));
		if(registrationIds[i].localeCompare(req.params.id)){
			console.log("Found");
			found = true;
		}
	}
	if(!found){
		registrationIds.push(req.params.id);
	}
	res.json(true);
});
//Update user shipping adresses
app.put('/Basket.js/UpdateUser/',function(req,res){
	console.log("Updating user");
	var u =users["lukesionkira@hotmail.com"];
	u.shippingAdress=req.body.shippingAdress;
	u.creditCards=req.body.creditCards;
	console.log(req.body.shippingAdress);
	res.json(true);
});
//Update a basket
app.put('/Basket.js/UpdateBasket/:pos',function(req,res){
	console.log("Updating basket");
	var u =users["lukesionkira@hotmail.com"];
	u.baskets[req.params.pos].buyEvents=req.body.buyEvents;
	console.log(req.body.buyEvents);
	res.json(true);
});
//Add a bid
app.put('/Basket.js/addBid/:id',function(req,res){
	console.log("here");
	var u =userList[1];
	var target;
	   for(var i=0; i<bidlist.length; i++) {
	        if (bidlist[i].id == req.params.id) target=bidlist[i];
	    }
	   console.log(req.body);
	   console.log(target);
	   target.bids.push(req.body);
	   
	  // u.currentlyBiddingOn.push(target);
	
	res.json(true);
});
//Search for something
app.get('/Basket.js/search/:searchQuery',function(req,res)
{
	console.log(req.params.searchQuery);
	function getBuyEvents () 
	{
		var defered = Q.defer();
		var userquery='select * from Buy_Events natural join Products natural join Manufacturers join Users on userId=soldBy where btitle like "%'+req.params.searchQuery+'%"';
		console.log(userquery);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getBidEvents () 
	{
		var defered = Q.defer();
		var userquery='select Bid_Events.*,Products.*,Manufacturers.*,Users.*,bidTime as time,w.username as wusername,amount from Bid_Events natural join Products natural join Manufacturers join Users on userId=soldBy left outer join Bids on bidId=winningBid left outer join Users as w on w.userId=Bids.userId where bidTitle like "%'+req.params.searchQuery+'%"';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getBuyEvents(),getBidEvents()]).then(function(rest)
	{
		console.log('yeeah');
		var eventList = new Array();
		var bidList=new Array();
		 for (var i=0;i<rest[0][0].length;i++)
		 {
				
				 eventList.push(new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].buyEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].btitle)); 
		 }
			
		 for (var i=0;i<rest[1][0].length;i++)
		 {
			 	console.log(rest[1][0][i].amount);
				 bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,new Bid(rest[1][0][i].wusername,rest[1][0][i].time,rest[1][0][i].amount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		 }
		 console.log('sali');
		 var response =
			{
				"buyEvents":eventList,
				"bidEvents":bidList
			};
		 
		 console.log(bidList.length);
		 console.log(bidList);
			res.json(response);
	});
});

//Delete bidevent
app.del('/Basket.js/remBid/:id', function(req,res)
{
	console.log("Removing item");
	var target = req.params.id;
	users["lukesionkira@hotmail.com"].currentlySellingOnBid.splice(target,1);
	res.json(true);
});

//Delete user
app.del('/Basket.js/UserDelete/:id', function(req,res)
		{
			var target = req.params.id;
			userList.splice(target,1);
			res.json(true);
		});
//Create a user
app.post('/Basket.js/create/:id', function(req,res)
{
	console.log("Creating user");
	userList.push(req.body);
	res.json(true);
});
//Place an order
app.post('/Basket.js/PlaceOrder/:username/:pos', function(req,res)
		{
	console.log("hind");
	var us = users["lukesionkira@hotmail.com"];
	us.userOrders.push(req.body);
	
	us.baskets.splice(req.params.pos,1);

	res.json(true);
		});
//Create a basket
app.post('/Basket.js/NewBasket', function(req,res)
{
	var u =users["lukesionkira@hotmail.com"];
	u.baskets.push(req.body);
	res.json(true);
});
//Remove a basket
app.post('/Basket.js/RemoveBasket', function(req,res)
{
	console.log("Here");
	var u =users["lukesionkira@hotmail.com"];
	var index = u.baskets.indexOf(req.body);
	u.baskets.splice(index, 1);
	res.json(true);
});
//Create a sell bid event
app.post('/Basket.js/NewBidSell', function(req,res)
{
	console.log("Created buy event");
	var u =users["lukesionkira@hotmail.com"];
	u.currentlySellingOnBid.push(req.body);
	res.json(true);
});
//Create a sell buy event
app.post('/Basket.js/NewBuySell', function(req,res)
{
	console.log("Created bid event");
	var u =users["lukesionkira@hotmail.com"];
	u.currentlySellingOnBuy.push(req.body);
	res.json(true);
});
//Example report
var exReport = new Report(2,05,1992,"sales", 150,25000);
app.get('/Basket.js/Report/:day/:month/:year/:type',function(req,res)
	{
	var response = 
	{
		"day": exReport.day ,
		"month": exReport.month,
		"year" : exReport.year,
		"type": exReport.type,
		"totalSales": exReport.totalSales,
		"totalGross": exReport.totalGross
	};
	res.json(response);
});
var products = new Array(
		 new product("Alienware M17x", 1204054932,"Dell Inc.",20,15,40),
		 new product("Macbook", 1203323,"Apple Inc.",20,15,40)
);
//Search for product
app.get('/Basket.js/Product/:searchQuery', function(req,res)
{
	
	function getBuyEvents () 
	{
		var defered = Q.defer();
		var userquery='select * from Buy_Events natural join Products natural join Manufacturers join Users on userId=soldBy where btitle like \'%'+connection.escape(req.params.searchQuery)+'%\'';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getBidEvents () 
	{
		var defered = Q.defer();
		var userquery='select * from Bid_Events natural join Products natural join Manufacturers join Users on userId=soldBy where bidTitle like \'%'+connection.escape(req.params.searchQuery)+'%\'';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getBuyEvents(),getBidEvents()]).then(function(rest)
	{
		var eventList = new Array();
		var max = rest[0][0].length;
		if(rest[1][0].length>max) max= rest[1][0].length;
		
		 for (var i=0;i<max;i++)
		 {
			 if(i<rest[0][0].length)
				 evetList.push(new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,1,1,1),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].basketId,rest[0][0][i].username,rest[0][0][i].rating)); 
			 if(i<rest[1][0].length)
				 evetList.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,1,1,1),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		 }
		 var response =
			{
				"products":eventList
			};
		 console.log('products');
			res.json(response);
	});
	
});

app.get('/Basket.js/UpdateBidSeller', function(req,res)
		{
	console.log('Checking for completed Bid Events');
	function getFinishedBidEvents () 
	{
		var defered = Q.defer();
		var query='select * from Bid_Events where NOW()>= endingTime';
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};
	
	Q.all([getFinishedBidEvents()]).then(function(rest)
			{
			var finished = new Array();
			for (var i = 0; i<rest[0][0].length;i++)
			{
				finished.push(rest[0][0][i].bidEventId);
			}
		var response={
				"toFinish":finished
			};
		console.log(response);
			res.json(response);
			});
	
		});
app.get('/Basket.js/ProductReport/:day/:month/:year/:type', function(req,res)
	{
		var response =
		{
				"totalSales": 3200000,
				"totalGross":12300030
				
		};
		res.json(response);
});

//Get a user	
app.get('/Basket.js/User/:id/:password', function(req, res) 
		
{
	
	  
	
	function getUserInfo (callback) {
		var userquery= 'SELECT * FROM Users where username='+connection.escape(req.params.id)+' and password='+connection.escape(req.params.password);
		connection.query(userquery, function(err, response) {
			  if (err) throw err;
			  callback(err, response);
			});
	};
	function getUserBaskets (id) 
	{
		
		var defered = Q.defer();
		var userquery= 'SELECT * FROM Baskets natural join Users natural join in_buy_basket natural join Buy_Events natural join Products natural join Manufacturers join Users as b on b.userId=soldBy where Users.userId='+connection.escape(id)+' order by basketId';
		connection.query(userquery, defered.makeNodeResolver());
		 return defered.promise;
	};
	function getSoldBy (id)
	{
		var defered = Q.defer();
		var userquery= 'SELECT * FROM  Users join Buy_Events on soldBy=userId natural join Products natural join Manufacturers where userId='+connection.escape(id);
		connection.query(userquery, defered.makeNodeResolver());
		 return defered.promise;
	};
	function getSoldByBid (id) {
		var defered=Q.defer();
		var userquery= 'SELECT Users.*,Bid_Events.*,Products.*,Manufacturers.*,Bids.*,b.username as winnerName FROM  Users join Bid_Events on soldBy=userId natural join Products natural join Manufacturers left outer join Bids on bidId=winningBid left outer join Users as b on b.userId=Bids.userId where Users.userId='+connection.escape(id);
		connection.query(userquery, defered.makeNodeResolver());
		 return defered.promise;
	};
	function getCreditCards (id) {
		var defered = Q.defer();
		var userquery= 'select * from Users as a natural join Credit_Cards join Address on AddressId=BillingId where a.UserId='+connection.escape(id);
		connection.query(userquery, defered.makeNodeResolver());
		 return defered.promise;
	};
	function getCurrentlyBiddingOn (id) {
		var defered=Q.defer();
		var userquery= 'select distinct a.*,Bids.*,Bid_Events.*,Products.*,Manufacturers.*,b.*,w.bidTime as time,w.amount as wamount, wu.username as wusername from Users as a natural join Bids natural join Bid_events natural join Products natural join Manufacturers join Users as b on b.userId=soldBy left outer join Bids as w on Bid_Events.winningBid=w.bidId left outer join Users as wu on wu.userId=w.userId   where a.userId='+connection.escape(id);
		connection.query(userquery,defered.makeNodeResolver());
		 return defered.promise;
	};
	
	function getShipping (id) {
		var userquery= 'select Address.* from Address where userId='+connection.escape(id)+' and addressId not in (select Address.addressId from Users as a natural join Credit_Cards join Address on AddressId=BillingId where a.userId='+connection.escape(id)+')';
		var defered= Q.defer();
		connection.query(userquery,defered.makeNodeResolver());
		 return defered.promise;
	};
	function getBilling (id) {
		var userquery= 'select Address.* from Users as a natural join Credit_Cards join Address on AddressId=BillingId where a.userId='+connection.escape(id);
		var defered= Q.defer();
		connection.query(userquery, defered.makeNodeResolver());
		 return defered.promise;
	};
	//fix user
	function getOrders (id) {
		var userquery= 'select Credit_Cards.*,Address.*, bAddress.line1 as bline1, bAddress.line2 as bline2 , bAddress.country as bcountry, bAddress.zipCode as bzipCode, bAddress.city as bcity, bAddress.state as bstate, Orders.*,Bank_Accounts.*,Buy_Events.*,Products.*,b.* from Users as b natural join Orders natural join Bank_Accounts join Baskets on withbasketId=basketId natural join in_buy_basket natural join Buy_Events natural join Products join Address on Address.userId=b.userId join Credit_Cards on b.userId=Credit_Cards.userId join Address as bAddress on bAddress.AddressId=Credit_Cards.billingId where Address.userId='+connection.escape(id)+' order by orderId';
		var defered= Q.defer();
		connection.query(userquery, defered.makeNodeResolver());
		 return defered.promise;
	};
	
	
	
	
	
	
	getUserInfo(function(err, result){
	    console.log(err || JSON.stringify(result));
	    if (result.length ==0) {
	    	res.statusCode=404;
	    	res.send("Wrong");
	    	return;}
		var dd = result[0].userId;
		
		
		
		Q.all([getShipping(dd),getBilling(dd),getOrders(dd),getCurrentlyBiddingOn(dd),getCreditCards(dd),getSoldByBid(dd),getSoldBy(dd),getUserBaskets(dd)]).then(function(rest){
	     //console.log(rest[0][0][0]);
	     
	     //get the shipping Address in shipping
		console.log(rest[1][0].length);
	     var shipping= new Array();
		   for (var i=0;i<rest[0][0].length;i++)
		   {
			   shipping.push(new Adress(rest[0][0][i].line1,rest[0][0][i].line2,rest[0][0][i].country,rest[0][0][i].zipCode,rest[0][0][i].city,rest[0][0][i].state));
		   }
		   //console.log(shipping);
		   
		 //get the billing address in billing
		   var billing= new Array();
		   for (var i=0;i<rest[1][0].length;i++)
		   {
			   billing.push(new Adress(rest[1][0][i].line1,rest[1][0][i].line2,rest[1][0][i].country,rest[1][0][i].zipCode,rest[1][0][i].city,rest[1][0][i].state));
		   }
		   //console.log(billing);

		   var OrderList = new Array();
		    var oEvents= new Array();
		    var curroId=rest[2][0][0].orderId;

		    for(var i=0;i<rest[2][0].length;i++)
		    {
		    	if(curroId != rest[2][0][i].orderId || i==rest[2][0].length-1) //include last
		    	{
		    		if (i==rest[2][0].length-1)
		    		{
		    			if (curroId != rest[2][0][i].orderId)
		    			{
		    			console.log(new Order(rest[2][0][i-1].sellingTime,
		    					new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,
		    							new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate))
		    			,rest[2][0][i-1].accountNum,oEvents,
		    			new Adress (rest[2][0][i-1].line1,rest[2][0][i-1].line2,rest[2][0][i-1].country,rest[2][0][i-1].zipCode,rest[2][0][i-1].city,rest[2][0][i-1].state)));
		    			OrderList.push(new Order(rest[2][0][i-1].sellingTime,new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate)),rest[2][0][i-1].accountNum,oEvents,new Adress(rest[2][0][i-1].line1,rest[2][0][i-1].line2,rest[2][0][i-1].country,rest[2][0][i-1].zipCode,rest[2][0][i-1].city,rest[2][0][i-1].state)));
				    	oEvents= new Array();
		    			}
				    	oEvents.push(new BuyEvent(new product(rest[2][0][i].pname,rest[2][0][i].sellerPId,rest[2][0][i].mname,1,1,1),rest[2][0][i].price,rest[2][0][i].sellingTime,false,rest[2][0][i].features,rest[2][0][i].description,rest[2][0][i].basketId,rest[2][0][i].username,rest[2][0][i].rating,rest[2][0][i].btitle)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
				    	console.log(rest[2][0][i].sellingTime);
				    	OrderList.push(new Order(rest[2][0][i].sellingTime,new CreditCard(rest[2][0][i].cardId,rest[2][0][i].cardNum,rest[2][0][i].expMonth,rest[2][0][i].expYear,rest[2][0][i].secCode,rest[2][0][i].name,new Adress(rest[2][0][i].bline1,rest[2][0][i].bline2,rest[2][0][i].bcountry,rest[2][0][i].bzipCode,rest[2][0][i].bcity,rest[2][0][i].bstate)),rest[2][0][i].accountNum,oEvents,new Adress(rest[2][0][i].line1,rest[2][0][i].line2,rest[2][0][i].country,rest[2][0][i].zipCode,rest[2][0][i].city,rest[2][0][i].state)));
		    		}
		    		else
		    		{
	    			console.log(new Adress(rest[2][0][i-1].line1,rest[2][0][i-1].line2,rest[2][0][i-1].country,rest[2][0][i-1].zipCode,rest[2][0][i-1].city,rest[2][0][i-1].state));
		    		OrderList.push(new Order(rest[2][0][i-1].sellingTime,new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate)),rest[2][0][i-1].accountNum,oEvents,new Adress(rest[2][0][i-1].line1,rest[2][0][i-1].line2,rest[2][0][i-1].country,rest[2][0][i-1].zipCode,rest[2][0][i-1].city,rest[2][0][i-1].state)));
		    		}
		    		
		    		oEvents= new Array();
		    	}
		    	oEvents.push(new BuyEvent(new product(rest[2][0][i].pname,rest[2][0][i].sellerPId,rest[2][0][i].mname,1,1,1),rest[2][0][i].price,rest[2][0][i].sellingTime,false,rest[2][0][i].features,rest[2][0][i].description,rest[2][0][i].basketId,rest[2][0][i].username,rest[2][0][i].rating,rest[2][0][i].btitle)); 
		    	curroId=rest[2][0][i].orderId;
				console.log(oEvents);

//must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		    }
		    console.log(OrderList);
		   
		   
		   //bidding on in BidEvents
		   var BidEvents= new Array();
		   for (var i=0;i<rest[3][0].length;i++)
		   {

		    	BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,new Bid(rest[3][0][i].wusername,rest[3][0][i].time,rest[3][0][i].wamount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		   }
		   console.log(BidEvents);

		   console.log('jajajaja');
		   //console.log(BidEvents);
		   
		   //get the credit cards
		   
		   var CreditCards= new Array();
		   for (var i=0;i<rest[4][0].length;i++)
		   {
			   CreditCards.push(new CreditCard(rest[4][0][i].cardId,rest[4][0][i].cardNum,rest[4][0][i].expMonth,rest[4][0][i].expYear,rest[4][0][i].secCode,rest[4][0][i].name,new Adress(rest[4][0][i].line1,rest[4][0][i].line2,rest[4][0][i].country,rest[4][0][i].zipCode,rest[4][0][i].city,rest[4][0][i].state)));
		   }
		   //console.log(CreditCards);
		   
		   //get sold by Bid
		   var sBidEvents= new Array();
		   for (var i=0;i<rest[5][0].length;i++)
		   {
			   var arrayBuffer = rest[5][0][i]; 
			   if (arrayBuffer) {
			     var byteArray = new Uint8Array(arrayBuffer);
			     console.log(byteArray);
			   }
			   console.log(rest[5][0][i].bidTime);

			   sBidEvents.push(new BidEvent(new product(rest[5][0][i].pname,rest[5][0][i].productPId,rest[5][0][i].mname,rest[5][0][i].width,rest[5][0][i].height,rest[5][0][i].depth),rest[5][0][i].startingBid,rest[5][0][i].startingTime,rest[5][0][i].endingTime,rest[5][0][i].features,rest[5][0][i].description,rest[5][0][i].minBid,rest[5][0][i].bidEventId,rest[5][0][i].username,rest[5][0][i].rating,rest[5][0][i].bidTitle,rest[5][0][i].picture,new Bid(rest[5][0][i].winnerName,rest[5][0][i].bidTime,rest[5][0][i].amount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		   }
		   console.log(sBidEvents);
		   //get sold by
		   
		   var sBuyEvents= new Array();
		   for (var i=0;i<rest[6][0].length;i++)
		   {
			   
		    	sBuyEvents.push(new BuyEvent(new product(rest[6][0][i].pname,rest[6][0][i].sellerPId,rest[6][0][i].mname,1,1,1),rest[6][0][i].price,rest[6][0][i].sellingTime,false,rest[6][0][i].features,rest[6][0][i].description,rest[6][0][i].basketId,rest[6][0][i].username,rest[6][0][i].rating,rest[6][0][i].btitle)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		   }
		   
		   //get user Baskets!!
		   var BasketList = new Array();
		    var Events= new Array();
		    var EventsPerBasket={};
		    var currId=rest[7][0][0].basketId;
		    for(var i=0;i<rest[7][0].length;i++)
		    {
		    	if(currId != rest[7][0][i].basketId || i==rest[7][0].length-1) //include last
		    	{
		    		if (i==rest[7][0].length-1)
		    		{
		    			if (currId != rest[7][0][i].basketId)
		    			{
				    	EventsPerBasket[rest[7][0][i-1].bname]=Events; //must address multiple name existance?
				    	Events= new Array();
		    			}
				    	Events.push(new BuyEvent(new product(rest[7][0][i].pname,rest[7][0][i].sellerPId,rest[7][0][i].mname,1,1,1),rest[7][0][i].price,rest[7][0][i].sellingTime,false,rest[7][0][i].features,rest[7][0][i].description,rest[7][0][i].basketId,rest[7][0][i].username,rest[7][0][i].rating,rest[7][0][i].btitle)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		    			EventsPerBasket[rest[7][0][i].bname]=Events; 
		    		}
		    		else
		    		{
		    		EventsPerBasket[rest[7][0][i-1].bname]=Events; //must address multiple name existance?
		    		}
		    		
		    		Events= new Array();
		    	}
		    	Events.push(new BuyEvent(new product(rest[7][0][i].pname,rest[7][0][i].sellerPId,rest[7][0][i].mname,1,1,1),rest[7][0][i].price,rest[7][0][i].sellingTime,false,rest[7][0][i].features,rest[7][0][i].description,rest[7][0][i].basketId,rest[7][0][i].username,rest[7][0][i].rating,rest[7][0][i].btitle)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		    	currId=rest[7][0][i].basketId;
		    }
		    
		   
//		   var keys = Object.keys(EventsPerBasket);
		   for (var key in EventsPerBasket)
		   {
			   console.log(key);
			   BasketList.push(new Basket(key,EventsPerBasket[key],null));
		   }
		   
		   var response={
					"username": result[0].username,
					"email": result[0].email,
					"password": result[0].password,
					"billingAdress": billing,
					"rating":result[0].rating,
					"shippingAdress":shipping,
					"baskets": BasketList,
					"creditCards":CreditCards,
					"currentlyBiddingOn":BidEvents,
					"currentlySellingOnBid":sBidEvents,
					"currentlySellingOnBuy":sBuyEvents,	
					"userOrders":OrderList
			};
		   console.log(sBidEvents);
		   console.log(BidEvents);
		   
		   
		   
		   
		   
	     
	     res.json(response);
	    });
	
//		getShipping(result[0].userId,function(err, result)
//				{
//				    //console.log(err || JSON.stringify(result));
//				   var shipping= new Array();
//				   for (var i=0;i<result.length;i++)
//				   {
//					   shipping.push(new Adress(result[i].line1,result[i].line2,result[i].country,result[i].zipCode));
//				   }
//				   //console.log(shipping);
//				});
//		getBilling(result[0].userId,function(err, result)
//				{
//				    //console.log(err || JSON.stringify(result));
//				   var shipping= new Array();
//				   for (var i=0;i<result.length;i++)
//				   {
//					   shipping.push(new Adress(result[i].line1,result[i].line2,result[i].country,result[i].zipCode));
//				   }
//				   console.log(shipping);
//				});
//		getSoldBy(result[0].userId,function(err, result)
//		{
//		    //console.log(err || JSON.stringify(result));
//		   var sBuyEvents= new Array();
//		   for (var i=0;i<result.length;i++)
//		   {
//		    	sBuyEvents.push(new BuyEvent(new product(result[i].pname,result[i].sellerPId,result[i].mname,1,1,1),result[i].price,1,1,1,1,1,false,result[i].features,result[i].description,null,result[i].basketId)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
//		   }
		   //console.log(sBuyEvents);
		});
//		
//		getCurrentlyBiddingOn(result[0].userId,function(err, result)
//		{
		    //console.log(err || JSON.stringify(result));
//		   var BidEvents= new Array();
//		   for (var i=0;i<result.length;i++)
//		   {
//		    	BidEvents.push(new BidEvent(new product(result[i].pname,result[i].mname,1,1,1),result[i].startingBid,result[i].startingTime,result[i].endingTime,result[i].features,result[i].description,result[i].minBid,result[i].bidEventId)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
//
//		   }
//		   console.log(BidEvents);
//		});
//		getCreditCards(result[0].userId,function(err, result)
//				{
//				    //console.log(err || JSON.stringify(result));
//				   var CreditCards= new Array();
//				   for (var i=0;i<result.length;i++)
//				   {
//					   CreditCards.push(new CreditCard(result[i].cardId,result[i].cardNum,result[i].expMonth,result[i].expYear,result[i].secCode,result[i].name,new Adress(result[i].line1,result[i].line2,result[i].country,result[i].zipCode)));
//				   }
				   //console.log(CreditCards);
//				});
//		getSoldByBid(result[0].userId,function(err, result)
//				{
//				    //console.log(err || JSON.stringify(result));
//				   var sBidEvents= new Array();
//				   for (var i=0;i<result.length;i++)
//				   {
//				    	sBidEvents.push(new BidEvent(new product(result[i].pname,result[i].mname,1,1,1),result[i].startingBid,result[i].startingTime,result[i].endingTime,result[i].features,result[i].description,result[i].minBid,result[i].bidEventId)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
//				   }
				   //console.log(sBidEvents);
//				});
//		getUserBaskets(result[0].userId,function(err, result){
//		    //console.log(err || JSON.stringify(result));
//		    var BasketList = new Array();
//		    var Events= new Array();
//		    var EventsPerBasket={};
//		    var currId=result[0].basketId;
//		    for(var i=0;i<result.length;i++)
//		    {
//		    	if(currId != result[i].basketId || i==result.length-1) //include last
//		    	{
//		    		currId=result[i].basketId;
//		    		if (i==result.length-1){
//				    	Events.push(new BuyEvent(new product(result[i].pname,result[i].sellerPId,result[i].mname,1,1,1),result[i].price,1,1,1,1,1,false,result[i].features,result[i].description,null,result[i].basketId)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
//		    			EventsPerBasket[result[i].name]=Events; 
//		    		}
//		    		else
//		    		EventsPerBasket[result[i-1].name]=Events; //must address multiple name existance?
//		    		
//		    		Events= new Array();
//		    	}
//		    	
//		    	Events.push(new BuyEvent(new product(result[i].pname,result[i].sellerPId,result[i].mname,1,1,1),result[i].price,1,1,1,1,1,false,result[i].features,result[i].description,null,result[i].basketId)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
//		    }
//		   // console.log(EventsPerBasket);
//		});
//		res.json(result[0]);
	});
	
//	var email = req.params.id;
//	var userAccount = users[email];
//	var password= req.params.password;
//	if (email==""||password=="")
//	{
//		res.statusCode = 400;
//		res.send("Fields missing");
//		return;
//	}
//	if (userAccount==null)
//	{
//		res.statusCode = 404;
//		res.send("User not found.");
//	}
//	else {
//		if (userAccount.password == password)
//		{
//			console.log("true");
//			var response = {"password": userAccount.password,
//							"username": userAccount.username,
//							"email": userAccount.email,
//							"billingAdress":userAccount.billingAdress,
//							"shippingAdress":userAccount.shippingAdress,
//							"baskets": userAccount.baskets,
//							"creditCards":userAccount.creditCards,
//							"currentlyBiddingOn":userAccount.currentlyBiddingOn,
//							"currentlySellingOnBid":userAccount.currentlySellingOnBid,
//							"currentlySellingOnBuy":userAccount.currentlySellingOnBuy,
//							"userOrders":userAccount.userOrders};
//			res.json(response);		
//		}
//		else {
//			res.statusCode=404;
//			res.send("User or Password mismatch");
//		}	
	


//Push things

var gcm = require('node-gcm');
// or with object values
var message = new gcm.Message({
    collapseKey: 'demo',
    delayWhileIdle: true,
    timeToLive: 3,
    data: {
        key1: 'message1',
        key2: 'message2'
    }
});
var sender = new gcm.Sender('AIzaSyCTFn1fBSl-7jcUgWIDb6SE17qiaoFpr6o');
var registrationIds = [];
// At least one required
/**
 * Parameters: message-literal, registrationIds-array, No. of retries, callback-function
 */
var myVar=setInterval(function(){myTimer()},30000);
//check for completed bidEvents

//var myVar2=setInterval(function(){myBidEventTimer()},60000);

//function myBidEventTimer()
//{
//	console.log('Checking for completed Bid Events');
//	function getFinishedBidEvents () 
//	{
//		var defered = Q.defer();
//		var query='select * from Bid_Events where NOW()>= endingTime';
//		connection.query(query, defered.makeNodeResolver());
//		return defered.promise;
//	};
//	
//	
//}

function myTimer()
{
	console.log("Bam");
	sender.send(message, registrationIds, 4, function (err, result) {console.log(result);});
}


