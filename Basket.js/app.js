
/**
 * Module dependencies.
 */

var express = require('express');

var app = express();
var roduct = require("./Product.js");
var product = roduct.product;

var orderJS= require("./order.js");
var Order =  orderJS.Order;

var userJS = require("./user.js");
var User= userJS.User;

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
users["lukesionkira@hotmail.com"]= new User ("lukesionkira@hotmail.com","chris","qwerty",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList);

var reviews = new Array();
var event= new BuyEvent(exproduct,1700.50,1,2010,05,8,36,false,"- Intel Core i7 3610QM \
		- 6 GB DDR3 \
		- 17.3-Inch Screen \
		- 1600x900","The all-new Alienware 14 is compact, powerful and designed for more intense gaming anywhere you set up. The magnesium alloy frame and anodized aluminum shell protect your LCD and its components, while the copper heat sinks keep your system cool on the inside so you can game for hours on end."
		,reviews);
var buylist = new Array();
buylist.push(event);
var bid = new Bid(users["lukesionkira@hotmail.com"], 2,5,2011,12,50, 300);
 var bids = new Array();
var bidlist = new Array();

var biddy = new BidEvent(exbproduct,300,1,2011,5,9,35,21,2011,6,5,20,"- Intel Core i7 3610QM \
		- 6 GB DDR3 \
		- 17.3-Inch Screen \
		- 1600x900","The all-new Alienware 14 is compact, powerful and designed for more intense gaming anywhere you set up. The magnesium alloy frame and anodized aluminum shell protect your LCD and its components, while the copper heat sinks keep your system cool on the inside so you can game for hours on end.",
		reviews,bids,bid,users["lukesionkira@hotmail.com"],30);
bidlist.push(biddy);

var bidEvents = new Array();

var baskets= new Array(
		new Basket("basket example",buyEvents,buyEvents),
		new Basket("basket example 2",buyEvents,buyEvents)
);


var orderList= new Array();


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
var userList = new Array(
		new User ("lukesionkira@hotmail.com","chris","qwerty",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList),
		new User ("pedro.colon4@upr.edu","blabla","potatoes",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList),
		new User ("Wu@hotmail.com","Wuuu","Wuuuuuu",billingList,shippingList,baskets,buyEvents,bidEvents,buyEvents, creditCardList,orderList)
		);
app.get('/Basket.js/AdminSearch/:searchQuery',function(req,res)
{

	var response =
		{
			"users": userList
		};
	res.json(response);
	
});
app.put('/Basket.js/UserEdit/:id/:usr/:pass/:email',function(req,res){
	console.log("here");
	var buffUser =userList[req.params.id];
	buffUser.username=req.params.usr;
	buffUser.password=req.params.pass;
	buffUser.email=req.params.email;
	res.json(true);
});
app.put('/Basket.js/UpdateBasket/:pos',function(req,res){
	console.log("here");
	var u =users["lukesionkira@hotmail.com"];
	u.baskets[req.params.pos].buyEvents=req.body.buyEvents;
	console.log(req.body.buyEvents);
	res.json(true);
});
app.get('/Basket.js/search/:searchQuery',function(req,res)
{
	var response =
	{
			"buyEvents":buylist,
			"bidEvents":bidlist
	};
	res.json(response);
});
app.del('/Basket.js/UserDelete/:id', function(req,res)
		{
			var target = req.params.id;
			userList.splice(target,1);
			res.json(true);
		});
app.post('/Basket.js/create/:id', function(req,res)
		{
	console.log("hind");
	userList.push(req.body);
	res.json(true);
		});
app.post('/Basket.js/PlaceOrder/:username', function(req,res)
		{
	console.log("hind");
	var us = users["lukesionkira@hotmail.com"];
	us.userOrders.push(req.body);
	res.json(true);
		});
app.post('/Basket.js/NewBasket', function(req,res)
		{
	var u =users["lukesionkira@hotmail.com"];
	u.baskets.push(req.body);
	res.json(true);
		});
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

app.get('/Basket.js/Product/:searchQuery', function(req,res)
{
	var response =
	{
			"products":products
	};
	res.json(response);
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
app.get('/Basket.js/User/:id/:password', function(req, res) 
		{
	var email = req.params.id;
	var userAccount = users[email];
	var password= req.params.password;
	if (email==""||password=="")
	{
		res.statusCode = 400;
		res.send("Fields missing");
		return;
	}
	console.log(email);
	console.log(password);
	console.log(userAccount.password);
	console.log(userAccount.email);

	if (userAccount==null)
	{
		res.statusCode = 404;
		res.send("User not found.");
	}
	else {
		if (userAccount.password == password)
		{
			console.log("true");
			var response = {"password": userAccount.password,
							"username": userAccount.username,
							"email": userAccount.email,
							"billingAdress":userAccount.billingAdress,
							"shippingAdress":userAccount.shippingAdress,
							"baskets": userAccount.baskets,
							"ceditCards":userAccount.creditCards,
							"currentlyBiddingOn":userAccount.currentlyBiddingOn,
							"currentlySellingOnBid":userAccount.currentlySellingOnBid,
							"currentlySellingOnBuy":userAccount.currentlySellingOnBuy,
							"userOrders":userAccount.userOrders};
			res.json(response);		
		}
		else {
			res.statusCode=404;
			res.send("User or Password mismatch");
		}	
	}

		});
