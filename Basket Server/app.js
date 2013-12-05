/**
 * Module dependencies.
 */
var express = require('express');
var nodemailer = require("nodemailer");

var app = express();
var roduct = require("./product.js");
var product = roduct.product;

var orderJS= require("./order.js");
var Order =  orderJS.Order;

var userJS = require("./user.js");
var User= userJS.User;

var dealJS= require("./deal.js");
var Deal =  dealJS.Deal;

var categoryJS= require("./category.js");
var Category =  categoryJS.Category;

var reviewJS = require("./review.js");
var Review= reviewJS.Review;

var user_reviewJS = require("./user_review.js");
var User_Review= user_reviewJS.User_Review;

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

var Q = require('q');
var bb = require('bytebuffer');

var mysql      = require('mysql');
var connection = mysql.createConnection({
	host     : 'localhost',
	user     : 'root',
	password : '',
	database: "myFirstSql"
});

connection.connect();



function getForgottenUserAccount(umail, callback) 
{
	console.log("In get user info uname is:"+umail);
	connection.query('SELECT * FROM users WHERE email=\''+umail+'\'', function(err, response) {
		if (err) 
			throw err;
		callback(err, response);
	});
};

////create reusable transport method (opens pool of SMTP connections)



function getUserInfo(uname, callback) {
	console.log("In get user info uname is:"+uname);
	if(uname==""){
		console.log("Empty");
		connection.query('SELECT * FROM Users', function(err, response) {
			if (err) throw err;
			callback(err, response);
		});
	}
	else{
		console.log("Not empty");
		console.log(uname);
		connection.query('SELECT username, password, email FROM Users WHERE username like \'%'+uname+'%\' union '+'SELECT username, password, email FROM admins WHERE username like \'%'+uname+'%\';', function(err, response) {
			if (err) throw err;
			callback(err, response);
		});
	}
};





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
		new BuyEvent(exproduct,1700.50,1,2010,05,8,36,false,"- Intel Core i7 3610QM \
				- 6 GB DDR3 \
				- 17.3-Inch Screen \
				- 1600x900","The all-new Alienware 14 is compact, powerful and designed for more intense gaming anywhere you set up. The magnesium alloy frame and anodized aluminum shell protect your LCD and its components, while the copper heat sinks keep your system cool on the inside so you can game for hours on end."
				,reviews,0,5)
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
		,reviews,0,1);

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
//{

//var response = {"Product" : ProductList[0]};
//res.json(response);		

//});

//Search for users
app.get('/Basket.js/AdminSearch/',function(req,res)
		{
	console.log("Empty argument adminsearch");
	var namequery = "";
	getUserInfo(namequery, function(err, result){
		console.log(err || JSON.stringify(result));
		console.log('Im out!');
		var response =
		{
				"users": result
		};
		res.json(response);	
	});

		});

app.get('/Basket.js/AdminSearch/:search',function(req,res)
		{
	console.log("Not empty log");
	var namequery = req.params.search;
	console.log(req.params.search);
	getUserInfo(namequery, function(err, result){
		console.log(err || JSON.stringify(result));
		console.log('Im out!');
		var response =
		{
				"users": result
		};
		res.json(response);	
	});

		});
function getrecommendations(username, callback) {
	console.log("Loading recommendations for "+username);
	var query = 'select * from buy_events natural join products natural join manufacturers natural join Users where soldBy = userId and buycategory in (select buycategory as boughtcategories from users natural join orders, baskets natural join buy_events natural join in_buy_basket where basketId = withbasketId and username = \''+username+'\') and buyEventId not in (select buyEventId as boughtIds from users natural join orders, baskets natural join buy_events natural join in_buy_basket where basketId = withbasketId and username = \''+username+'\')';
	console.log(query);
	connection.query(query  , function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
app.get('/Basket.js/GetRecommendations/:userName',function(req,res)
		{
	console.log("Getting recommendations");

	getrecommendations(req.params.userName, function(err, result){

		console.log(err || JSON.stringify(result));
		console.log('Im out!');
		var results = new Array();
		for(var i =0;i<result.length;i++){
			results.push(new BuyEvent(new product(result[i].pname,result[i].productId,result[i].mname,result[i].width,result[i].height,result[i].depth,result[i].dimensions),result[i].price,result[i].sellingTime,false,result[i].features,result[i].description,result[i].buyEventId,result[i].username,result[i].rating,result[i].btitle,result[i].pic,1));
		}
		var response =
		{
				"buyEvents": results
		};
		res.json(response);	
	});

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
		var userquery='select * from Product_Reviews natural join reviews_bid_event   join Bid_Events on productReviewedId=Bid_Events.bidEventId join Users on Product_Reviews.userId=Users.userId where reviews_bid_event.bidEventId='+connection.escape(req.params.id);
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


app.get('/Basket.js/GetDeals',function(req,res)
		{
	console.log("Getting deals");
	function getDeals() 
	{
		var defered = Q.defer();
		var userquery='SELECT * FROM deal_of_the_day natural join Buy_Events natural join Products natural join Manufacturers join Users on userId=soldBy where dayOfSale=DATE(NOW())';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getDeals()]).then(function(rest)
			{

		var events= new Array();
		for (var i=0;i<rest[0][0].length;i++)
		{
			events.push(new Deal(rest[0][0][i].title,new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].buyEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].btitle,rest[0][0][i].pic,1))); 
		}
		console.log(events);
		var response =
		{
				"events": events	
		};
		console.log(response);
		res.json(response);
			});
		});

//get uRatings
app.get('/Basket.js/GetRatings/:id',function(req,res)
		{

	function getURatings() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select User_Reviews.rating,c.username from Users join User_Reviews on userReviewedId=Users.userID join Users as c on c.userId=userReviewId where Users.username='+ connection.escape(req.params.id);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getURatings()]).then(function(rest)
			{
		console.log('HERE');
		var t= new Array();
		for (var i=0;i<rest[0][0].length;i++)
		{
			t.push(new User_Review(rest[0][0][i].rating,rest[0][0][i].username));
		}
		var response =
		{
				"r": t
		};
		console.log(response);
		res.json(response);
			});		
		});
//Edit a user
function editUser (userId, req , callback) 
{
	console.log(req);
	var userquery='UPDATE `myfirstsql`.`users` SET `email` = \''+req.params.email+'\', password = \''+req.params.pass+'\', username = \''+req.params.usr+'\' WHERE `users`.`userId` = \''+userId+'\';';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function getUserId (req , callback) 
{
	// var defered = Q.defer();
	var userquery='select userId from users where username= \''+req.body.username+'\' and email = \''+req.body.email+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};	
app.put('/Basket.js/UserEdit/:id/:usr/:pass/:email',function(req,res){
	console.log("Editing user");
	getUserId(req,function(err, result){

		var userId = result[0].userId;
		editUser(userId,req,function(err,result){
			console.log("Success");
			res.json(true);

		});

	});
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
	console.log(u.baskets[req.params.pos]);
	if(u.baskets[req.params.pos]==undefined){
		res.json(false);
	}
	else{
		u.baskets[req.params.pos].buyEvents=req.body.buyEvents;
		console.log(req.body.buyEvents);
		res.json(true);
	}

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

			eventList.push(new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].buyEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].btitle,rest[0][0][i].pic,1)); 
		}

		for (var i=0;i<rest[1][0].length;i++)
		{
			console.log(rest[1][0][i].amount);
			if(rest[1][0][i].wusername!=null)
				bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,new Bid(rest[1][0][i].wusername,rest[1][0][i].time,rest[1][0][i].amount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			else
				bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,null)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

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
app.get('/Basket.js/search/:searchQuery/:cat',function(req,res)
		{
	console.log(req.params.searchQuery);
	function getBuyEvents () 
	{
		var defered = Q.defer();
		var userquery='select * from Buy_Events natural join Products natural join Manufacturers join Users on userId=soldBy join Categories on Categories.categoryId=buycategory where btitle like "%'+req.params.searchQuery+'%" and name='+connection.escape(req.params.cat);
		console.log(userquery);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getBidEvents () 
	{
		var defered = Q.defer();
		var userquery='select Categories.*,Bid_Events.*,Products.*,Manufacturers.*,Users.*,bidTime as time,w.username as wusername,amount from Bid_Events natural join Products natural join Manufacturers join Users on userId=soldBy left outer join Bids on bidId=winningBid left outer join Users as w on w.userId=Bids.userId join Categories on Categories.categoryId=bidcategory where bidTitle like "%'+req.params.searchQuery+'%" and name='+connection.escape(req.params.cat);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	}
	Q.all([getBuyEvents(),getBidEvents()]).then(function(rest){
		console.log('yeeah');
		var eventList = new Array();
		var bidList=new Array();
		for (var i=0;i<rest[0][0].length;i++)
		{

			eventList.push(new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].buyEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].btitle,rest[0][0][i].pic,1)); 
		}

		for (var i=0;i<rest[1][0].length;i++)
		{
			console.log(rest[1][0][i].amount);
			if(rest[1][0][i].wusername!=null)
				bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,new Bid(rest[1][0][i].wusername,rest[1][0][i].time,rest[1][0][i].amount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			else
				bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,null)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

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

function insertUser (req , callback) 
{
	console.log(req);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`users` (`userId`, `username`, `firstName`, `lastName`, `password`, `email`, `age`, `birthday`, `rating`) \
		VALUES (NULL, \''+req.body.username+'\', \''+req.body.firstName+'\', \''+req.body.lastName+'\', \''+req.body.password+'\', \''+req.body.email+'\', \''+req.body.age+'\', \''+req.body.bdYear+'-'+req.body.bdMonth+'-'+req.body.bdDay+'\', \'0\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function getInsertedUserId (req , callback) 
{
	// var defered = Q.defer();
	var userquery='select userId from users where username= \''+req.body.username+'\' and firstName = \''+req.body.firstName+'\' and lastName = \''+req.body.lastName+'\' and email = \''+req.body.email+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};	
function insertShipAddress (userId, req  ,callback) 
{
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
		VALUES (NULL,\''+req.body.shippingAdress[0].line1+'\',\''+req.body.shippingAdress[0].line2+'\',\''+req.body.shippingAdress[0].city+'\',\''+req.body.shippingAdress[0].country+'\',\''+req.body.shippingAdress[0].zipCode+'\',\''+userId+'\',\''+req.body.shippingAdress[0].state+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};

function getInsertedShipAddress (req,callback) 
{
	// var defered = Q.defer();
	var userquery='select AddressId from address where userId= \''+userId+'\' and line1 = \''+req.body.shippingAdress[0].line1+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};	
function insertBillAddress (userId, req,callback) 
{
	// var defered = Q.defer();
	console.log(req);
	var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
		VALUES (NULL,\''+req.body.billingAdress[0].line1+'\',\''+req.body.billingAdress[0].line2+'\',\''+req.body.billingAdress[0].city+'\',\''+req.body.billingAdress[0].country+'\',\''+req.body.billingAdress[0].zipCode+'\',\''+userId+'\',\''+req.body.billingAdress[0].state+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function insertAddress (userId, req,callback) 
{
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
		VALUES (NULL,\''+req.body.billing.line1+'\',\''+req.body.billing.line2+'\',\''+req.body.billing.city+'\',\''+req.body.billing.country+'\',\''+req.body.billing.zipCode+'\',\''+userId+'\',\''+req.body.billing.state+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function insertShippingAddress (userId, req,callback) 
{
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
		VALUES (NULL,\''+req.body.line1+'\',\''+req.body.line2+'\',\''+req.body.city+'\',\''+req.body.country+'\',\''+req.body.zipCode+'\',\''+userId+'\',\''+req.body.state+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function getInsertedBillAddress (userId, req,callback) 
{
	// var defered = Q.defer();
	var userquery='select AddressId from address where userId= \''+userId+'\' and line1 = \''+req.body.billingAdress[0].line1+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};	

function getInsertedAddress (userId, req,callback) 
{
	console.log(req);

	// var defered = Q.defer();
	var userquery='select AddressId from address where userId= \''+userId+'\' and line1 = \''+req.body.billing.line1+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};	
function insertCreditCards (userId, billId, req,callback) 
{
	// var defered = Q.defer();
	console.log(req);
	var userquery='INSERT INTO `myfirstsql`.`credit_cards` (`cardId`, `cardNum`, `secCode`, `expMonth`, `expYear`, `name`, `userId`, `bankAccountId`, `BillingId`) \
		VALUES (NULL,\''+req.body.creditCards[0].cardNum+'\',\''+req.body.creditCards[0].secCode+'\',\''+req.body.creditCards[0].expMonth+'\',\''+req.body.creditCards[0].expYear+'\',\''+req.body.creditCards[0].name+'\',\''+userId+'\',\'1'+'\',\''+billId+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};

app.post('/Basket.js/create/:id', function(req,res){

	var userId =-1;
	var insertedBill ="";
	console.log(req.body.creditCards);
	res.json(false);
	console.log(req.body);
	insertUser(req, function(err, result){
		console.log(req.body);
		getInsertedUserId(req, function(err, result){
			console.log(result);
			userId = result[0].userId;
			console.log(userId);
			insertShipAddress(userId, req, function(err, result){
				insertBillAddress(userId,req, function(err, result){
					getInsertedBillAddress(userId, req, function(err, result){
						insertedBill = result[0].AddressId;
						console.log(insertedBill);
						insertCreditCards(userId,insertedBill,req,function(err, result){
							console.log("Success");
							res.json(true);
						});
					});
				});
			});
		});
	});

});
function insertAdmin (req , callback) 
{
	console.log(req);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`admins` (`adminId`, `username`, `firstName`, `lastName`, `password`, `email`, `age`) \
		VALUES (NULL, \''+req.body.username+'\', \''+req.body.firstName+'\', \''+req.body.lastName+'\', \''+req.body.password+'\', \''+req.body.email+'\', \''+req.body.age+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
app.post('/Basket.js/createAdmin/:id', function(req,res)
		{
	insertAdmin(req,function(err,result){
		console.log("Success inserting admin");
		res.json("true");
	});
		});

function updateCreditCard (cardId, req , callback) 
{
	console.log(req);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='UPDATE `myfirstsql`.`credit_cards` SET `cardNum` = \''+req.cardNum+'\', secCode = \''+req.secCode+'\', expMonth = \''+req.expMonth+'\', expYear = \''+req.expYear+'\', name = \''+req.name+'\' WHERE `credit_cards`.`cardId` = \''+cardId+'\';';

	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function updateBilling (billId, req , callback) 
{
	console.log(req);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='UPDATE `myfirstsql`.`address` SET `line1` = \''+req.line1+'\', line2 = \''+req.line2+'\', city = \''+req.city+'\', country = \''+req.country+'\', zipCode = \''+req.zipCode+'\', state = \''+req.state+'\' WHERE `address`.`AddressId` = \''+billId+'\';';

	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function getCreditCardIdandBillId (uid,card, callback) 
{
	console.log(card);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='SELECT cardId, BillingId from credit_cards where cardNum = \"'+card.cardNum+'\" and secCode = \"'+card.secCode+'\" and expMonth = \"'+card.expMonth+'\" and expYear=\"'+card.expYear+'\" and userId=\"'+uid+'\"';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
app.post('/Basket.js/updateCreditCard/:email/:uname', function(req,res){
	var bod = 
	{
			"username":req.params.uname,
			"email":req.params.email
	};
	var user = {
			"body": bod
	};			
	console.log("User");
	console.log(user);
	console.log("Body");
	console.log(req.body);
	getUserId(user,function(err,result){
		console.log(result);
		var userId = result[0].userId;
		console.log(userId);

		getCreditCardIdandBillId(userId, req.body[1],function(err,response){
			console.log(response);
			var billId = response[0].BillingId;
			updateCreditCard(response[0].cardId,req.body[0],function(err,respnse){
				console.log("Updating card");
				updateBilling(billId,req.body[1].billing,function(err,respon){
					console.log("Updated succesfully");
					res.json(true);
				});
			});
		});
	});	
});
function insertCreditCard (userId, billId, req,callback) 
{
	// var defered = Q.defer();
	console.log(req);
	var userquery='INSERT INTO `myfirstsql`.`credit_cards` (`cardId`, `cardNum`, `secCode`, `expMonth`, `expYear`, `name`, `userId`, `bankAccountId`, `BillingId`) \
		VALUES (NULL,\''+req.body.cardNum+'\',\''+req.body.secCode+'\',\''+req.body.expMonth+'\',\''+req.body.expYear+'\',\''+req.body.name+'\',\''+userId+'\',\'1'+'\',\''+billId+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};

app.post('/Basket.js/insertCreditCard/:email/:uname', function(req,res){
	var bod = 
	{
			"username":req.params.uname,
			"email":req.params.email
	};
	var user = {
			"body": bod
	};			
	console.log("User");
	console.log(user);
	console.log("Body");
	console.log(req.body);
	getUserId(user,function(err,result){
		console.log(result);
		var userId = result[0].userId;
		console.log(userId);
		console.log(req.body);
		insertAddress(userId,req, function(err, result){
			getInsertedAddress(userId, req, function(err, result){
				console.log(result);
				insertedBill = result[0].AddressId;
				console.log(insertedBill);
				
				insertCreditCard(userId,insertedBill,req,function(err, result){
					console.log("Success");
					res.json(true);
				});
			});
		});
	});	
});
app.post('/Basket.js/updateAddress/:email/:uname', function(req,res){
	var bod = 
	{
			"username":req.params.uname,
			"email":req.params.email
	};
	var user = {
			"body": bod
	};			
	console.log("User");
	console.log(user);
	console.log("Body");
	console.log(req.body);
	getUserId(user,function(err,result){
		console.log(result);
		var userId = result[0].userId;
		console.log(userId);

		getInsertedAddress(userId, req.body[1],function(err,response){
			console.log(response);
			var billId = response[0].AddressId;

			updateBilling(billId,req.body[0],function(err,respon){
				console.log("Updated succesfully");
				res.json(true);
			});

		});
	});	
});
app.post('/Basket.js/insertShippingAddress/:email/:uname', function(req,res){
	var bod = 
	{
			"username":req.params.uname,
			"email":req.params.email
	};
	var user = {
			"body": bod
	};			
	console.log("User");
	console.log(user);
	console.log("Body");
	console.log(req.body);
	getUserId(user,function(err,result){
		console.log(result);
		var userId = result[0].userId;
		console.log(userId);
		insertShippingAddress(userId, req, function(err,response){
			console.log("Updated succesfully");
			res.json(true);
		});

		
	});	
});
function updateAddress (billId, newAddress , callback) 
{
	console.log(billId);
	console.log(newAddress);
	// var userquery='INSERT INTO `myfirstsql`.`admins` (`adminId`, `username`, `firstName`, `lastName`, `password`, `email`, `age`) \
	// VALUES (NULL, \''+req.body.username+'\', \''+req.body.firstName+'\', \''+req.body.lastName+'\', \''+req.body.password+'\', \''+req.body.email+'\', \''+req.body.age+'\');';
	// console.log(userquery);
	// connection.query(userquery, function(err, response) {
	//  		if (err) throw err;
	//  		callback(err, response);
	// });
};

app.post('/Basket.js/updateAddress/:id', function(req,res)
		{
	insertAdmin(req,function(err,result){
		console.log("Success inserting admin");
		res.json("true");
	});
		});
//app.post('/Basket.js/create/:id', function(req,res)
//{
//console.log(req.body);
//function insertUser () 
//{
//var defered = Q.defer();
//var userquery='INSERT INTO `myfirstsql`.`users` (`userId`, `username`, `firstName`, `lastName`, `password`, `email`, `age`, `birthday`, `rating`) \
//VALUES (NULL, \''+req.body.username+'\', \''+req.body.firstName+'\', \''+req.body.lastName+'\', \''+req.body.password+'\', \''+req.body.email+'\', \''+req.body.age+'\', \''+req.body.bdYear+'-'+req.body.bdMonth+'-'+req.body.bdDay+'\', \'0\');';
//console.log(userquery);
//connection.query(userquery, defered.makeNodeResolver());
//return defered.promise;
//};
//function getInsertedUserId () 
//{
//var defered = Q.defer();
//var userquery='select userId from users where username= \''+req.body.username+'\' and firstName = \''+req.body.firstName+'\' and lastName = \''+req.body.lastName+'\' and email = \''+req.body.email+'\'';
//console.log(userquery);
//connection.query(userquery, defered.makeNodeResolver());
//return defered.promise;
//};

//function insertShipAddress () 
//{
//var defered = Q.defer();
//var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
//VALUES (NULL,\''+req.body.shippingAdress[0].line1+'\',\''+req.body.shippingAdress[0].line2+'\',\''+req.body.shippingAdress[0].city+'\',\''+req.body.shippingAdress[0].country+'\',\''+req.body.shippingAdress[0].zipCode+'\',(select max(userId) from users),\''+req.body.shippingAdress[0].state+'\');';
//console.log(userquery);
//connection.query(userquery, defered.makeNodeResolver());
//return defered.promise;
//};
//function insertBillAddress () 
//{
//var defered = Q.defer();
//var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
//VALUES (NULL,\''+req.body.billingAdress[0].line1+'\',\''+req.body.billingAdress[0].line2+'\',\''+req.body.billingAdress[0].city+'\',\''+req.body.billingAdress[0].country+'\',\''+req.body.billingAdress[0].zipCode+'\',(select max(userId) from users),\''+req.body.billingAdress[0].state+'\');';
//console.log(userquery);
//connection.query(userquery, defered.makeNodeResolver());
//return defered.promise;
//};

//function insertCreditCards () 
//{
//var defered = Q.defer();
//var userquery='INSERT INTO `myfirstsql`.`credit_cards` (`cardId`, `cardNum`, `secCode`, `expMonth`, `expYear`, `name`, `userId`, `bankAccountId`, `BillingId`) \
//VALUES (NULL,\''+req.body.creditCards[0].cardNum+'\',\''+req.body.creditCards[0].secCode+'\',\''+req.body.creditCards[0].expMonth+'\',\''+req.body.creditCards[0].expYear+'\',\''+req.body.creditCards[0].name+'\',(select max(userId) from users),'+"1,(select max(AddressId) from address));";
//console.log(userquery);
//connection.query(userquery, defered.makeNodeResolver());
//return defered.promise;
//};
//Q.all([insertUser(),insertShipAddress(),insertBillAddress(),insertCreditCards()]).then(function(rest)
//{
//console.log("Success!");
//res.json(true);
//});

//});


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
				evetList.push(new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,1,1,1,rest[0][0][i].dimensions),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].basketId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].pic,1)); 
			if(i<rest[1][0].length)
				evetList.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,1,1,1,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		}
		var response =
		{
				"products":eventList
		};
		res.json(response);
			});

		});

app.get('/Basket.js/UpdateBidSeller', function(req,res)
		{
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
		res.json(response);
			});

		});



///////////////////////////////////////////
function getadminproducts(query, callback) {
	console.log("Loading admin products "+query);
	connection.query('select * from products natural join manufacturers where pname like \'%'+query+'%\''  , function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
app.get('/Basket.js/AdminProductSearch/:query', function(req,res)
		{
	console.log("Getting admin products");
	getadminproducts(req.params.query, function(err, result){
		console.log(err || JSON.stringify(result));
		console.log('Im out!');
		var productlist = new Array();
		for(var i=0;i<result.length;i++){
			console.log(result[i]);
			productlist.push(new product(result[i].pname, result[i].productId,result[i].mname,result[i].width,result[i].height,result[i].depth));
		}
		var response =
		{
				"products": productlist
		};
		res.json(response);	
	});
		});
function getproductsales(weekStart,type, reqdate, pid, callback) {
	console.log(2);
	var month = weekStart.getMonth();
	month+=1;
	console.log("Getting total sales"+reqdate+"Type:"+type);
	var datearray = reqdate.split("-");
	var queryadd="";
	if(type=='Day'){
		console.log("1");
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';
	}
	else if(type == 'Year'){
		queryadd = '\'%'+datearray[0]+'-%\'';
		console.log(datearray[0]);
		console.log("2");
	}
	else if(type=='Month'){
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-%\'';
		console.log("3");
	}

	var query = 'select sum(price*item_quantity) as result from in_buy_basket natural join buy_events natural join baskets, orders where basketId = withbasketId and productId = ' +pid+' and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
	console.log(query);
	connection.query(query, function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function getproductsalescount(weekStart,type, reqdate, pid, callback) {
	console.log(1);
	var month = weekStart.getMonth();
	month+=1;
	console.log("Getting total sales"+reqdate+"Type:"+type);
	var datearray = reqdate.split("-");
	var queryadd="";
	if(type=='Day'){
		console.log("1");
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';
	}
	else if(type == 'Year'){
		queryadd = '\'%'+datearray[0]+'-%\'';
		console.log(datearray[0]);
		console.log("2");
	}
	else if(type=='Month'){
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-%\'';
		console.log("3");
	}
	var query = 'select sum(item_quantity) as result from in_buy_basket natural join buy_events natural join baskets, orders where basketId = withbasketId and productId = ' +pid+' and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
	console.log(query);

	connection.query(query  , function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function gettotalsales(weekStart, type, reqdate, callback) {
	console.log(1);
	var month = weekStart.getMonth();
	month+=1;
	console.log(weekStart);
	console.log("Getting total sales"+reqdate+"Type:"+type);
	var datearray = reqdate.split("-");
	var queryadd="";
	if(type=='Day'){
		console.log("1");
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';
	}
	else if(type == 'Year'){
		queryadd = '\'%'+datearray[0]+'-%\'';
		console.log(datearray[0]);
		console.log("2");
	}
	else if(type=='Month'){
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-%\'';
		console.log("3");
	}
	var query = 'select sum(amount) as result from orders where DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
	console.log(query);
	connection.query(query  , function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};
function gettotalsalescount(weekStart, type, reqdate, callback) {
	console.log(1);
	var month = weekStart.getMonth();
	month+=1;
	console.log(weekStart);

	console.log("Getting total sales count"+reqdate+"Type:"+type);
	var datearray = reqdate.split("-");
	var queryadd="";
	if(type=='Day'){
		console.log("1");
		// queryadd = '\'%-'+datearray[2]+'%\'';
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';

	}
	else if(type == 'Year'){
		queryadd = '\'%'+datearray[0]+'-%\'';
		console.log(datearray[0]);
		console.log("2");
	}
	else if(type=='Month'){
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-%\'';

		// queryadd = '\'%-'+datearray[1]+'-%\'';
		console.log("3");
	}
	var query = 'select sum(item_quantity) as result from baskets natural join in_buy_basket natural join buy_events, orders where basketId = withbasketId and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
	console.log(query);
	connection.query(query  , function(err, response) {
		if (err) throw err;
		callback(err, response);
	});
};

app.get('/Basket.js/ProductReport/:day/:month/:year/:type/:pid', function(req,res)
		{
	// var datereq = req.params.year+"-"+req.params.month+"-"+req.params.day;
	// var now = new Date();
	// var startDay = 1; //0=sunday, 1=monday etc.
	// var d = now.getDay(); //get the current day
	// var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
	// var weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day
	// console.log(weekStart);
	// console.log(weekEnd);
	// console.log(req.params.pid);

	// getproductsales(req.params.type, datereq, req.params.pid, function(err, result){
	// console.log(err || JSON.stringify(result));
	// console.log('Im out!');
	// console.log(result[0].result);
	// if(result[0].result == null){
	// console.log("In if");
	// var response =
	// {
	// "totalSales": 0,
	// "totalGross": 0

	// };
	// res.json(response);
	// }
	// else{
	// getproductsalescount(req.params.type, datereq, req.params.pid, function(err, result2){
	// console.log(err || JSON.stringify(result2));
	// console.log('Im out!');
	// console.log(result2);
	// var response =
	// {
	// "totalSales": result2[0].result,
	// "totalGross": result[0].result

	// };
	// console.log(response);
	// res.json(response);
	// });
	// }

	// });


	var month = req.params.month;

	var datereq = req.params.year+"-"+month+"-"+req.params.day;

	console.log(req.params.type);
	console.log(datereq);

	// if(req.params.type == "Week"){
	// var now = new Date();
	// now.setFullYear( req.params.year);
	// now.setDate(req.params.day);
	// now.setMonth(req.params.month-1);
	// var startDay = 1; //0=sunday, 1=monday etc.
	// var d = now.getDay(); //get the current day
	// var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
	// var weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day
	// var totalSales = 0;
	// var totalGross = 0;
	// for(var i =0; i<7;i++){
	// var month = weekStart.getMonth()+1;
	// var datereq = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();
	// console.log(datereq);
	// gettotalsales("Day", datereq, function(err, result){
	// console.log(err || JSON.stringify(result));
	// console.log('Resultado de totalSales');
	// console.log(result[0].result);
	// if(result[0].result!=null)
	// totalSales+=result[0].result;
	// });
	// var val = weekStart.valueOf();
	// weekStart = new Date(val+86400000);
	// }
	// weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
	// weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day

	// for(var i =0; i<7;i++){
	// month = weekStart.getMonth()+1;
	// datereq = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

	// gettotalsalescount("Day", datereq, function(err, result){
	// console.log(err || JSON.stringify(result));
	// console.log('Resultado de totalSalescount');
	// console.log(result);

	// if(result[0].result!=null)
	// totalGross+=result[0].result;
	// });

	// var val = weekStart.valueOf();
	// weekStart = new Date(val+86400000);
	// }
	// var response =
	// {
	// "totalSales": totalSales,
	// "totalGross": totalGross
	// };
	// console.log(response);
	// res.json(response);

	// }
	function gettotsales(weekStart) {	
		var defered = Q.defer();
		var month = weekStart.getMonth();
		month+=1;
		var reqdate = weekStart.getFullYear()+"-%"+month+"-"+weekStart.getDate();

		console.log("Getting total sales"+reqdate+"Type:");
		var datearray = reqdate.split("-");
		var queryadd="";
		console.log("1");
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';


		// var query = 'select sum(amount) as result from orders where DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
		var query = 'select sum(price*item_quantity) as result from in_buy_basket natural join buy_events natural join baskets, orders where basketId = withbasketId and productId = ' +req.params.pid+' and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;

		console.log(query);
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;

	};
	function gettotsalescount(weekStart) {
		var defered = Q.defer();
		var month = weekStart.getMonth();
		month+=1;
		var reqdate = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

		console.log("Getting total sales count"+reqdate+"Type:");
		var datearray = reqdate.split("-");
		var queryadd="";

		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';

		// var query = 'select sum(item_quantity) as result from baskets natural join in_buy_basket natural join buy_events, orders where basketId = withbasketId and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
		var query = 'select sum(item_quantity) as result from in_buy_basket natural join buy_events natural join baskets, orders where basketId = withbasketId and productId = ' +req.params.pid+' and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
		console.log(query);
		connection.query(query  , defered.makeNodeResolver());
		return defered.promise;
	};
	if(req.params.type == "Week"){
		var now = new Date();
		now.setFullYear( req.params.year);
		now.setDate(req.params.day);
		now.setMonth(req.params.month-1);
		var startDay = 0; //0=sunday, 1=monday etc.
		var d = now.getDay(); //get the current day
		var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
		var val = weekStart.valueOf();
		Q.all([gettotsalescount(weekStart), gettotsalescount(new Date(val+1*86400000)), gettotsalescount(new Date(val+2*86400000)),gettotsalescount(new Date(val+3*86400000)),gettotsalescount(new Date(val+4*86400000)),gettotsalescount(new Date(val+5*86400000)),gettotsalescount(new Date(val+6*86400000)),gettotsales(weekStart), gettotsales(new Date(val+1*86400000)), gettotsales(new Date(val+2*86400000)),gettotsales(new Date(val+3*86400000)),gettotsales(new Date(val+4*86400000)),gettotsales(new Date(val+5*86400000)),gettotsales(new Date(val+6*86400000))]).then(function(rest){


			// weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
			// weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day

			// for(var i =0; i<7;i++){
			// month = weekStart.getMonth()+1;
			// datereq = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

			// gettotalsalescount("Day", datereq, function(err, result){
			// console.log(err || JSON.stringify(result));
			// console.log('Resultado de totalSalescount');
			// console.log(result);

			// if(result[0].result!=null)
			// totalGross+=result[0].result;
			// });

			// var val = weekStart.valueOf();
			// weekStart = new Date(val+86400000);
			var totalSales = 0;
			var totalGross = 0;
			for (var i=0;i<rest.length;i++)
			{
				for(var j =0; j<rest[0][0].length;j++ ){
					console.log(rest[i][0][j].result);

					if(rest[i][0][j].result!= null){
						console.log(rest.length);
						if(i<7){
							console.log("Adding sale "+rest[i][0][j].result);
							totalSales+= rest[i][0][j].result;
						}
						else{
							console.log("Adding sale "+rest[i][0][j].result);
							totalGross+=rest[i][0][j].result;
						}
					}
				}
			}
			var response =
			{
					"totalSales": totalSales,
					"totalGross": totalGross
			};
			console.log(response);
			res.json(response);
		});


	}
	else{
		var now = new Date();
		console.log(now);
		now.setFullYear( req.params.year);
		now.setDate(req.params.day);
		now.setMonth(req.params.month-1);
		console.log(now);
		var startDay = 0; //0=sunday, 1=monday etc.
		var d = now.getDay();
		var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
		console.log("In else value of weekStart"+weekStart);
		getproductsales(weekStart,req.params.type, datereq, req.params.pid, function(err, result){
			console.log(err || JSON.stringify(result));
			console.log('Im out!');
			console.log(result[0].result);
			// var response =
			// {
			// "users": result
			// };
			// res.json(response);	

			getproductsalescount(weekStart, req.params.type, datereq, req.params.pid,function(err, result2){
				console.log(err || JSON.stringify(result2));
				console.log('Im out!');
				console.log(result2);
				var response =
				{
						"totalSales": result2[0].result,
						"totalGross": result[0].result

				};
				console.log(response);
				res.json(response);
			});
		});
	}
		});


app.get('/Basket.js/SalesReport/:day/:month/:year/:type', function(req,res)
		{

	var month = req.params.month;

	var datereq = req.params.year+"-"+month+"-"+req.params.day;

	console.log(req.params.type);
	console.log(datereq);

	// if(req.params.type == "Week"){
	// var now = new Date();
	// now.setFullYear( req.params.year);
	// now.setDate(req.params.day);
	// now.setMonth(req.params.month-1);
	// var startDay = 1; //0=sunday, 1=monday etc.
	// var d = now.getDay(); //get the current day
	// var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
	// var weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day
	// var totalSales = 0;
	// var totalGross = 0;
	// for(var i =0; i<7;i++){
	// var month = weekStart.getMonth()+1;
	// var datereq = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();
	// console.log(datereq);
	// gettotalsales("Day", datereq, function(err, result){
	// console.log(err || JSON.stringify(result));
	// console.log('Resultado de totalSales');
	// console.log(result[0].result);
	// if(result[0].result!=null)
	// totalSales+=result[0].result;
	// });
	// var val = weekStart.valueOf();
	// weekStart = new Date(val+86400000);
	// }
	// weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
	// weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day

	// for(var i =0; i<7;i++){
	// month = weekStart.getMonth()+1;
	// datereq = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

	// gettotalsalescount("Day", datereq, function(err, result){
	// console.log(err || JSON.stringify(result));
	// console.log('Resultado de totalSalescount');
	// console.log(result);

	// if(result[0].result!=null)
	// totalGross+=result[0].result;
	// });

	// var val = weekStart.valueOf();
	// weekStart = new Date(val+86400000);
	// }
	// var response =
	// {
	// "totalSales": totalSales,
	// "totalGross": totalGross
	// };
	// console.log(response);
	// res.json(response);

	// }
	function gettotsales(weekStart) {	
		var defered = Q.defer();
		var month = weekStart.getMonth();
		month+=1;
		var reqdate = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

		console.log("Getting total sales"+reqdate+"Type:");
		var datearray = reqdate.split("-");
		var queryadd="";
		console.log("1");
		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';


		var query = 'select sum(amount) as result from orders where DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
		console.log(query);
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;

	};
	function gettotsalescount(weekStart) {
		var defered = Q.defer();
		var month = weekStart.getMonth();
		month+=1;
		var reqdate = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

		console.log("Getting total sales count"+reqdate+"Type:");
		var datearray = reqdate.split("-");
		var queryadd="";

		queryadd = '\'%'+weekStart.getFullYear()+'-%'+month+'-'+datearray[2]+'\'';

		var query = 'select sum(item_quantity) as result from baskets natural join in_buy_basket natural join buy_events, orders where basketId = withbasketId and DATE_FORMAT(orderTime, \'%Y-%m-%d\') like '+ queryadd;
		console.log(query);
		connection.query(query  , defered.makeNodeResolver());
		return defered.promise;
	};
	if(req.params.type == "Week"){
		var now = new Date();
		now.setFullYear( req.params.year);
		now.setDate(req.params.day);
		now.setMonth(req.params.month-1);
		var startDay = 0; //0=sunday, 1=monday etc.
		var d = now.getDay(); //get the current day
		var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
		var val = weekStart.valueOf();
		Q.all([gettotsalescount(weekStart), gettotsalescount(new Date(val+1*86400000)), gettotsalescount(new Date(val+2*86400000)),gettotsalescount(new Date(val+3*86400000)),gettotsalescount(new Date(val+4*86400000)),gettotsalescount(new Date(val+5*86400000)),gettotsalescount(new Date(val+6*86400000)),gettotsales(weekStart), gettotsales(new Date(val+1*86400000)), gettotsales(new Date(val+2*86400000)),gettotsales(new Date(val+3*86400000)),gettotsales(new Date(val+4*86400000)),gettotsales(new Date(val+5*86400000)),gettotsales(new Date(val+6*86400000))]).then(function(rest){


			// weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
			// weekEnd = new Date(weekStart.valueOf() + 6*86400000); //add 6 days to get last day

			// for(var i =0; i<7;i++){
			// month = weekStart.getMonth()+1;
			// datereq = weekStart.getFullYear()+"-"+month+"-"+weekStart.getDate();

			// gettotalsalescount("Day", datereq, function(err, result){
			// console.log(err || JSON.stringify(result));
			// console.log('Resultado de totalSalescount');
			// console.log(result);

			// if(result[0].result!=null)
			// totalGross+=result[0].result;
			// });

			// var val = weekStart.valueOf();
			// weekStart = new Date(val+86400000);
			var totalSales = 0;
			var totalGross = 0;
			for (var i=0;i<rest.length;i++)
			{
				for(var j =0; j<rest[0][0].length;j++ ){

					if(rest[i][0][j].result!= null){
						console.log(rest[i][0][j].result);
						console.log(rest.length);
						if(i<7){
							console.log("Adding sale "+rest[i][0][j].result);
							totalSales+= rest[i][0][j].result;
						}
						else{
							console.log("Adding sale "+rest[i][0][j].result);
							totalGross+=rest[i][0][j].result;
						}
					}
				}
			}
			var response =
			{
					"totalSales": totalSales,
					"totalGross": totalGross
			};
			console.log(response);
			res.json(response);
		});


	}
	else{
		var now = new Date();
		console.log(now);
		now.setFullYear( req.params.year);
		now.setDate(req.params.day);
		now.setMonth(req.params.month-1);
		console.log(now);
		var startDay = 0; //0=sunday, 1=monday etc.
		var d = now.getDay();
		var weekStart = new Date(now.valueOf() - (d<=0 ? 7-startDay:d-startDay)*86400000); //rewind to start day
		console.log("In else value of weekStart"+weekStart);
		gettotalsales(weekStart,req.params.type, datereq, function(err, result){
			console.log(err || JSON.stringify(result));
			console.log('Im out!');
			console.log(result[0].result);
			// var response =
			// {
			// "users": result
			// };
			// res.json(response);	

			gettotalsalescount(weekStart, req.params.type, datereq, function(err, result2){
				console.log(err || JSON.stringify(result2));
				console.log('Im out!');
				console.log(result2);
				var response =
				{
						"totalSales": result2[0].result,
						"totalGross": result[0].result

				};
				console.log(response);
				res.json(response);
			});
		});
	}
		});

app.get('/Basket.js/WinBid/:id', function(req,res)
		{
	function getFinishedBidEvents () 
	{
		var defered = Q.defer();
		var query='select Bid_Events.bidEventId from Bids natural join Users join Bid_Events on Bid_Events.bidEventId=Bids.bidEventId where NOW()>= endingTime and accepted=true and username='+connection.escape(req.params.id);
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
		var response=
		{
				"toFinish":finished
		};

		console.log(finished);
		res.json(response);
			});

		});

function getAdminList (uname, upass, callback) {
	var userquery= 'SELECT * FROM admins where username='+connection.escape(uname)+' and password='+connection.escape(upass);
	connection.query(userquery, function(err, response) {
		if (err) 
			throw err;
		callback(err, response);
	});
};
app.get('/Basket.js/Admin/:id/:password', function(req, res) {
	console.log("In admin search");
	getAdminList(req.params.id, req.params.password, function(err, result2){
		console.log(err || JSON.stringify(result2));
		console.log('Im out!');
		console.log(result2.length);
		if (result2.length ==0) {
			res.statusCode=404;
			res.send("Wrong");
			return;
		}		
		res.json(true);
	});
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
		var userquery= 'select wuser.rating as sellerRating, cc.*,Manufacturers.*,Address.*, bAddress.line1 as bline1, bAddress.line2 as bline2 , bAddress.country as bcountry, bAddress.zipCode as bzipCode, bAddress.city as bcity, bAddress.state as bstate,shipTo.line1 as sline1, shipTo.line2 as sline2 , shipTo.country as scountry, shipTo.zipCode as szipCode, shipTo.city as scity, shipTo.state as sstate, Orders.*,Bank_Accounts.*,Buy_Events.*,Products.*,b.*,in_buy_basket.item_quantity from Users as b natural join Orders join Bank_Accounts on Bank_Accounts.bankAccountId=Orders.bankAccountId join Baskets on withbasketId=basketId natural join in_buy_basket natural join Buy_Events natural join Products join Address on Address.userId=b.userId join Credit_Cards on b.userId=Credit_Cards.userId join Credit_Cards as cc on cc.cardId=Orders.cardId join Address as bAddress on bAddress.AddressId=Credit_Cards.billingId natural join Manufacturers join Users as wuser on wuser.userId=Buy_Events.soldBy join Address as shipTo on shipTo.AddressId=Orders.shipTo where b.userId='+connection.escape(id)+' and type="buy" order by orderId';
		var defered= Q.defer();
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};

	function getOrdersBid (id) {
		var userquery= '	select shipTo.line1 as sline1, shipTo.line2 as sline2 , shipTo.country as scountry, shipTo.zipCode as szipCode, shipTo.city as scity, shipTo.state as sstate, wuser.rating as sellerRating,cc.*,Address.*,Manufacturers.*, bAddress.line1 as bline1, bAddress.line2 as bline2 , bAddress.country as bcountry, bAddress.zipCode as bzipCode, bAddress.city as bcity, bAddress.state as bstate, Orders.*,Bank_Accounts.*,Bid_Events.*,Products.*,b.*, w.bidTime as time,w.amount as wamount, wu.username as wusername, c.username as seller , c.rating as sellerRating from Users as b natural join Orders join Bank_Accounts on Bank_Accounts.bankAccountId=Orders.bankAccountId join Baskets on withbasketId=basketId natural join in_bid_basket natural join Bid_Events natural join Products join Address on Address.userId=b.userId join Credit_Cards on b.userId=Credit_Cards.userId join Credit_Cards as cc on cc.cardId=Orders.cardId join Address as bAddress on bAddress.AddressId=Credit_Cards.billingId left outer join Bids as w on Bid_Events.winningBid=w.bidId left outer join Users as wu on wu.userId=w.userId join Users as c on c.userId=Bid_Events.soldBy natural join Manufacturers join Users as wuser on wuser.userId=Bid_Events.soldBy join Address as shipTo on shipTo.AddressId=Orders.shipTo where Address.userId='+connection.escape(id)+' and type="bid" order by orderId';
		var defered= Q.defer();
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};

	getUserInfo(function(err, result){
		if (result.length ==0) {
			res.statusCode=404;
			res.send("Wrong");
			return;}
		var dd = result[0].userId;



		Q.all([getShipping(dd),getBilling(dd),getOrders(dd),getCurrentlyBiddingOn(dd),getCreditCards(dd),getSoldByBid(dd),getSoldBy(dd),getUserBaskets(dd),getOrdersBid(dd)]).then(function(rest){
			//console.log(rest[0][0][0]);

			//get the shipping Address in shipping
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
			if(rest[2][0].length>0)
				var curroId=rest[2][0][0].orderId;

			for(var i=0;i<rest[2][0].length;i++)
			{
				console.log("here on"+curroId);


				if(curroId != rest[2][0][i].orderId || i==rest[2][0].length-1) //include last
				{
					console.log("here on"+curroId);

					if (i==rest[2][0].length-1)
					{
						console.log("here on"+i);
						if (curroId != rest[2][0][i].orderId)
						{
							console.log("here");
							OrderList.push(new Order(rest[2][0][i-1].sellingTime,new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate)),rest[2][0][i-1].accountNum,oEvents,new Adress(rest[2][0][i-1].sline1,rest[2][0][i-1].line2,rest[2][0][i-1].scountry,rest[2][0][i-1].szipCode,rest[2][0][i-1].scity,rest[2][0][i-1].sstate),null));
							oEvents= new Array();
						}
						oEvents.push(new BuyEvent(new product(rest[2][0][i].pname,rest[2][0][i].sellerPId,rest[2][0][i].mname,1,1,1,rest[2][0][i].dimensions),rest[2][0][i].price,rest[2][0][i].sellingTime,false,rest[2][0][i].features,rest[2][0][i].description,rest[2][0][i].basketId,rest[2][0][i].username,rest[2][0][i].sellerRating,rest[2][0][i].btitle,rest[2][0][i].pic,rest[2][0][i].item_quantity)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
						OrderList.push(new Order(rest[2][0][i].sellingTime,new CreditCard(rest[2][0][i].cardId,rest[2][0][i].cardNum,rest[2][0][i].expMonth,rest[2][0][i].expYear,rest[2][0][i].secCode,rest[2][0][i].name,new Adress(rest[2][0][i].bline1,rest[2][0][i].bline2,rest[2][0][i].bcountry,rest[2][0][i].bzipCode,rest[2][0][i].bcity,rest[2][0][i].bstate)),rest[2][0][i].accountNum,oEvents,new Adress(rest[2][0][i].sline1,rest[2][0][i].sline2,rest[2][0][i].scountry,rest[2][0][i].szipCode,rest[2][0][i].scity,rest[2][0][i].sstate),null));
					}
					else
					{
						OrderList.push(new Order(rest[2][0][i-1].sellingTime,new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate)),rest[2][0][i-1].accountNum,oEvents,new Adress(rest[2][0][i-1].sline1,rest[2][0][i-1].sline2,rest[2][0][i-1].scountry,rest[2][0][i-1].szipCode,rest[2][0][i-1].scity,rest[2][0][i-1].sstate),null));
					}

					oEvents= new Array();
				}
				oEvents.push(new BuyEvent(new product(rest[2][0][i].pname,rest[2][0][i].sellerPId,rest[2][0][i].mname,1,1,1,rest[2][0][i].dimensions),rest[2][0][i].price,rest[2][0][i].sellingTime,false,rest[2][0][i].features,rest[2][0][i].description,rest[2][0][i].basketId,rest[2][0][i].username,rest[2][0][i].sellerRating,rest[2][0][i].btitle,rest[2][0][i].pic,rest[2][0][i].item_quantity)); 
				curroId=rest[2][0][i].orderId;
				console.log(rest[2][0][i].orderId+'at'+i);



			}


			var o=rest[8][0];
			//get a list of bid orders 
			var  empty = new Array();

			for (var i =0;i<o.length;i++)
			{
				if(rest[8][0][i].wusername!=null)
					OrderList.push(new Order(o[i].endingTime,new CreditCard(o[i].cardId,o[i].cardNum,o[i].expMonth,o[i].expYear,o[i].secCode,o[i].name,new Adress(o[i].bline1,o[i].bline2,o[i].bcountry,o[i].bzipCode,o[i].bcity,o[i].bstate)),o[i].accountNum,empty,new Adress(o[i].sline1,o[i].sline2,o[i].scountry,o[i].szipCode,o[i].scity,o[i].sstate),new BidEvent(new product(rest[8][0][i].pname,rest[8][0][i].sellerPId,rest[8][0][i].mname,rest[8][0][i].width,rest[8][0][i].height,rest[8][0][i].depth,rest[8][0][i].dimensions),rest[8][0][i].startingBid,rest[8][0][i].startingTime,rest[8][0][i].endingTime,rest[8][0][i].features,rest[8][0][i].description,rest[8][0][i].minBid,rest[8][0][i].bidEventId,rest[8][0][i].seller, rest[8][0][i].sellerRating,rest[8][0][i].bidTitle,rest[8][0][i].picture,new Bid(rest[8][0][i].wusername,rest[8][0][i].time,rest[8][0][i].wamount))));	
				else
					OrderList.push(new Order(o[i].endingTime,new CreditCard(o[i].cardId,o[i].cardNum,o[i].expMonth,o[i].expYear,o[i].secCode,o[i].name,new Adress(o[i].bline1,o[i].bline2,o[i].bcountry,o[i].bzipCode,o[i].bcity,o[i].bstate)),o[i].accountNum,empty,new Adress(o[i].sline1,o[i].sline2,o[i].scountry,o[i].szipCode,o[i].scity,o[i].sstate),new BidEvent(new product(rest[8][0][i].pname,rest[8][0][i].sellerPId,rest[8][0][i].mname,rest[8][0][i].width,rest[8][0][i].height,rest[8][0][i].depth,rest[8][0][i].dimensions),rest[8][0][i].startingBid,rest[8][0][i].startingTime,rest[8][0][i].endingTime,rest[8][0][i].features,rest[8][0][i].description,rest[8][0][i].minBid,rest[8][0][i].bidEventId,rest[8][0][i].seller, rest[8][0][i].sellerRating,rest[8][0][i].bidTitle,rest[8][0][i].picture,null)));		  

			}

			console.log(OrderList);


			//bidding on in BidEvents
			var BidEvents= new Array();
			for (var i=0;i<rest[3][0].length;i++)
			{
				if(rest[3][0][i].wusername!=null)
					BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,new Bid(rest[3][0][i].wusername,rest[3][0][i].time,rest[3][0][i].wamount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
				else
					BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,null)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

			}

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
				}
				if(rest[5][0][i].winnerName!=null)
					sBidEvents.push(new BidEvent(new product(rest[5][0][i].pname,rest[5][0][i].productPId,rest[5][0][i].mname,rest[5][0][i].width,rest[5][0][i].height,rest[5][0][i].depth,rest[5][0][i].dimensions),rest[5][0][i].startingBid,rest[5][0][i].startingTime,rest[5][0][i].endingTime,rest[5][0][i].features,rest[5][0][i].description,rest[5][0][i].minBid,rest[5][0][i].bidEventId,rest[5][0][i].username,rest[5][0][i].rating,rest[5][0][i].bidTitle,rest[5][0][i].picture,new Bid(rest[5][0][i].winnerName,rest[5][0][i].bidTime,rest[5][0][i].amount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
				else
					sBidEvents.push(new BidEvent(new product(rest[5][0][i].pname,rest[5][0][i].productPId,rest[5][0][i].mname,rest[5][0][i].width,rest[5][0][i].height,rest[5][0][i].depth,rest[5][0][i].dimensions),rest[5][0][i].startingBid,rest[5][0][i].startingTime,rest[5][0][i].endingTime,rest[5][0][i].features,rest[5][0][i].description,rest[5][0][i].minBid,rest[5][0][i].bidEventId,rest[5][0][i].username,rest[5][0][i].rating,rest[5][0][i].bidTitle,rest[5][0][i].picture,null)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

			}
			//get sold by

			var sBuyEvents= new Array();
			for (var i=0;i<rest[6][0].length;i++)
			{

				sBuyEvents.push(new BuyEvent(new product(rest[6][0][i].pname,rest[6][0][i].sellerPId,rest[6][0][i].mname,1,1,1,rest[6][0][i].dimensions),rest[6][0][i].price,rest[6][0][i].sellingTime,false,rest[6][0][i].features,rest[6][0][i].description,rest[6][0][i].basketId,rest[6][0][i].username,rest[6][0][i].rating,rest[6][0][i].btitle,rest[6][0][i].pic,1)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			}

			//get user Baskets!!
			var BasketList = new Array();
			var Events= new Array();
			var EventsPerBasket={};
			if(rest[7][0].length>0)	//no baskets at all
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
						Events.push(new BuyEvent(new product(rest[7][0][i].pname,rest[7][0][i].sellerPId,rest[7][0][i].mname,1,1,1,rest[7][0][i].dimensions),rest[7][0][i].price,rest[7][0][i].sellingTime,false,rest[7][0][i].features,rest[7][0][i].description,rest[7][0][i].basketId,rest[7][0][i].username,rest[7][0][i].rating,rest[7][0][i].btitle,rest[7][0][i].pic,rest[7][0][i].item_quantity)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
						EventsPerBasket[rest[7][0][i].bname]=Events; 
					}
					else
					{
						EventsPerBasket[rest[7][0][i-1].bname]=Events; //must address multiple name existance?
					}

					Events= new Array();
				}
				Events.push(new BuyEvent(new product(rest[7][0][i].pname,rest[7][0][i].sellerPId,rest[7][0][i].mname,1,1,1,rest[7][0][i].dimensions),rest[7][0][i].price,rest[7][0][i].sellingTime,false,rest[7][0][i].features,rest[7][0][i].description,rest[7][0][i].basketId,rest[7][0][i].username,rest[7][0][i].rating,rest[7][0][i].btitle,rest[7][0][i].pic,rest[7][0][i].item_quantity)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
				currId=rest[7][0][i].basketId;
			}


//			var keys = Object.keys(EventsPerBasket);
			for (var key in EventsPerBasket)
			{
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







			res.json(response);
		});

//		getShipping(result[0].userId,function(err, result)
//		{
//		//console.log(err || JSON.stringify(result));
//		var shipping= new Array();
//		for (var i=0;i<result.length;i++)
//		{
//		shipping.push(new Adress(result[i].line1,result[i].line2,result[i].country,result[i].zipCode));
//		}
//		//console.log(shipping);
//		});
//		getBilling(result[0].userId,function(err, result)
//		{
//		//console.log(err || JSON.stringify(result));
//		var shipping= new Array();
//		for (var i=0;i<result.length;i++)
//		{
//		shipping.push(new Adress(result[i].line1,result[i].line2,result[i].country,result[i].zipCode));
//		}
//		console.log(shipping);
//		});
//		getSoldBy(result[0].userId,function(err, result)
//		{
//		//console.log(err || JSON.stringify(result));
//		var sBuyEvents= new Array();
//		for (var i=0;i<result.length;i++)
//		{
//		sBuyEvents.push(new BuyEvent(new product(result[i].pname,result[i].sellerPId,result[i].mname,1,1,1),result[i].price,1,1,1,1,1,false,result[i].features,result[i].description,null,result[i].basketId)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
//		}
		//console.log(sBuyEvents);
	});
		});

//var email = req.params.id;
//var userAccount = users[email];
//var password= req.params.password;
//if (email==""||password=="")
//{
//res.statusCode = 400;
//res.send("Fields missing");
//return;
//}
//if (userAccount==null)
//{
//res.statusCode = 404;
//res.send("User not found.");
//}
//else {
//if (userAccount.password == password)
//{
//console.log("true");
//var response = {"password": userAccount.password,
//"username": userAccount.username,
//"email": userAccount.email,
//"billingAdress":userAccount.billingAdress,
//"shippingAdress":userAccount.shippingAdress,
//"baskets": userAccount.baskets,
//"creditCards":userAccount.creditCards,
//"currentlyBiddingOn":userAccount.currentlyBiddingOn,
//"currentlySellingOnBid":userAccount.currentlySellingOnBid,
//"currentlySellingOnBuy":userAccount.currentlySellingOnBuy,
//"userOrders":userAccount.userOrders};
//res.json(response);		
//}
//else {
//res.statusCode=404;
//res.send("User or Password mismatch");
//}	



//Push things

var gcm = require('node-gcm');
//or with object values
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
//At least one required
/**
 * Parameters: message-literal, registrationIds-array, No. of retries, callback-function
 */
var myVar=setInterval(function(){myTimer()},30000);
//check for completed bidEvents

var myVar2=setInterval(function(){myBidEventTimer()},60000);

function myBidEventTimer()
{
	console.log('Checking for completed Bid Events');
	function getFinishedBidEvents () 
	{
		var defered = Q.defer();
		var query='select * from Bid_Events where NOW()>= endingTime';
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};


};

function myTimer()
{
	console.log("Bam");
	sender.send(message, registrationIds, 4, function (err, result) {console.log(result);});
};


app.get('/Basket.js/LoadCategory',function(req,res)
		{
	function getCategories() 
	{
		var defered = Q.defer();
		var userquery='select * from categories';
		console.log(userquery);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getCategories()]).then(function(rest)
			{
		var CategoryTree = new Array();
		var temCats={};
		var categories={};
		var r= rest[0][0];
		//load category map
		console.log('here first');
		for(var i=0 ; i<r.length;i++)
		{
			categories[r[i].categoryId]=r[i];
			temCats[r[i].categoryId]= new Category(r[i].name,r[i].parentCategoryId,new Array());
		}


		for(var i=0 ; i<r.length;i++)
		{
			var cat=r[i];
			if(cat.parentCategoryId==null)
			{
				CategoryTree.push(temCats[cat.categoryId]);
			}
			else
			{
				temCats[cat.categoryId].parent=temCats[cat.parentCategoryId];
				CategoryTree.push(temCats[cat.categoryId]);

			}
		}
		console.log("HAHAHAHA");
		var empty =new Array();
		var response =
		{
				"categories": CategoryTree
		};	
		console.log(CategoryTree.length);
		res.json(response);
			});
		});
//UNTESTED
var smtpTransport = nodemailer.createTransport("SMTP",
		{
	service: "Gmail",
	auth: {
		user: "basketservices@gmail.com",
		pass: "tito12@@"
	}
		});
app.get('/Basket.js/ForgetCreds/:email',function(req,res)
		{
	getForgottenUserAccount(req.params.email, function(err, result)
			{
		console.log(err || JSON.stringify(result));
		// var smtpTransport = nodemailer.createTransport("SMTP",
		// {
		//     service: "Gmail",
		//     auth: {
		//         user: "basketservices@gmail.com",
		//         pass: "tito12@@"
		//     }
		// });
		var mailOptions = {
				from: "Basket Services <basketservices@gmail.com>", // sender address
				to: req.params.email, // list of receivers
				subject: "Your basket account ", // Subject line
				text: JSON.stringify(result)//, // plaintext body
				// html: "<b>Hello world ✔</b>" // html body
		}

		// send mail with defined transport object
		smtpTransport.sendMail(mailOptions, function(error, response)
				{
			if(error)
			{
				console.log(error);
			}
			else{
				console.log("Message sent: " + response.message);
			}

			// if you don't want to use this transport object anymore, uncomment following line
			smtpTransport.close(); // shut down the connection pool, no more messages
				});

		console.log('Sent email!!');

		res.json(true);	
			});

		});