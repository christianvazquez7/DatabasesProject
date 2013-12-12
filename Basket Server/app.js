/**
 * Module dependencies.
 */
var express = require('express');
var nodemailer = require("nodemailer");
var queues= require('mysql-queues');
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

process.env.TZ = 'US/Eastern';

queues(connection,true);
connection.connect();



function getForgottenUserAccount(umail, callback) 
{
	console.log("In get user info uname is:"+umail);
	connection.query('SELECT * FROM users WHERE email=\''+umail+'\'', function(err, response) {
		if (err) 
			console.log(err);
		callback(err, response);
	});
};

////create reusable transport method (opens pool of SMTP connections)



function getUserInfo(uname, callback) {
	console.log("In get user info uname is:"+uname);
	if(uname==""){
		console.log("Empty");
		var queryadd  = 'SELECT username, password, email FROM Users where active_user=1 union SELECT username, password, email FROM admins WHERE active_user=1';

		connection.query(queryadd, function(err, response) {
			if (err) console.log(err);
			callback(err, response);
		});
	}
	else{
		console.log("Not empty");
		console.log(uname);
		connection.query('SELECT username, password, email FROM Users WHERE active_user=1 and username like '+connection.escape("%"+uname+"%")+' union SELECT username, password, email FROM admins WHERE active_user=1 and username like '+connection.escape("%"+uname+"%")+';', function(err, response) {
			if (err) console.log(err);
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


//Search for users
app.get('/Basket.js/AdminSearch/',function(req,res){
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

app.get('/Basket.js/AdminSearch/:search',function(req,res){
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
		if (err) console.log(err);
		callback(err, response);
	});
};
app.get('/Basket.js/GetRecommendations/:userName',function(req,res){
	console.log("Getting recommendations");

	getrecommendations(req.params.userName, function(err, result){


		console.log(err);
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

//WORK!!
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
		var userquery='select distinct * from Product_Reviews natural join reviews_bid_event   join Bid_Events on reviews_bid_event.bidEventId=Bid_Events.bidEventId join Users on Product_Reviews.userId=Users.userId where reviews_bid_event.bidEventId='+connection.escape(req.params.id);
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


app.get('/Basket.js/GetBids/:id',function(req,res){			

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
		var response =
		{
				"events": events	
		};
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
		var userquery='select User_Reviews.rating,c.username from Users join User_Reviews on userReviewedId=Users.userID join Users as c on c.userId=User_reviews.userId where Users.username='+ connection.escape(req.params.id);
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
		if (err) console.log(err);
		callback(err, response);
	});
};
function getUserId (req , callback) 
{
	// var defered = Q.defer();
	var userquery='select userId from users where username= \''+req.body.username+'\' and email = \''+req.body.email+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) console.log(err);
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

function regDevice(username,id,callback){
	var userquery = "update users set device_id="+connection.escape(id)+" where username="+connection.escape(username);
	console.log(userquery);
	connection.query(userquery,function(err,result){
		if (err) {
			console.log(err);
		}
		else{
			callback(err,result);
		}
	});
}
app.put('/Basket.js/RegisterDevice/:username/:id',function(req,res){
	console.log("Registering device");
	console.log(req.params.id);
	console.log(req.params.username);
	regDevice(req.params.username, req.params.id,function(err,result){
		res.json(true);
	});
	
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

app.put('/Basket.js/UpdateBasket/:bid/:eid',function(req,res)
		{
	function getRater() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select item_quantity  from in_buy_basket where basketId='+connection.escape(req.params.bid)+' and buyEventId='+connection.escape(req.params.eid);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};

	Q.all([getRater()]).then(function(rest)
			{
		var trans= connection.startTransaction();
		if (rest[0][0].length>0 && rest[0][0][0].item_quantity>0)
		{
			trans.query('update in_buy_basket set item_quantity='+connection.escape(rest[0][0][0].item_quantity +1)+' where basketId='+connection.escape(req.params.bid)+' and buyEventId='+connection.escape(req.params.eid),
					function(err,info)
					{
				if(err)
					trans.rollback();
				else
				{
					trans.commit();
					res.json(true);
				}

					});
		}
		else
		{
			trans.query('insert into in_buy_basket (basketId,buyEventId,item_quantity) values ('+connection.escape(req.params.bid)+
					','+connection.escape(req.params.eid)+',1)', function(err,info){

				if (err)
					trans.rollback();
				else
				{
					trans.commit();
					res.json(true);
				}
			});
		}
		trans.execute();

			});


		});


app.put('/Basket.js/QUpdateBasket/:bid/:q/:eid',function(req,res){
	console.log(req.params.eid);
	console.log(req.params.bid);
	console.log(req.params.q);


	var trans = connection.startTransaction();
	if(req.params.q<=0)
	{
		trans.query('delete from in_buy_basket where buyEventId='+connection.escape(req.params.eid)+' and basketId='+
				connection.escape(req.params.bid),function(err,info){

			if(err)
				trans.rollback();
			else{
				trans.commit();
				res.json(true);
			}

		});
	}else
		trans.query('update in_buy_basket set item_quantity='+req.params.q+ ' where buyEventId='+connection.escape(req.params.eid)+' and basketId='+
				connection.escape(req.params.bid),function(err,info){

			if(err)
				trans.rollback();
			else{
				trans.commit();
				res.json(true);
			}


		});

	trans.execute();
});


app.put('/Basket.js/addReview/:id/:username/:isBid/:pid',function(req,res)
		{

	function getRater() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select userId from users where username='+connection.escape(req.params.username);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getPid() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select productId from products where sellerPId='+connection.escape(req.params.pid);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getRater(),getPid()]).then(function(rest)
			{

		var trans = connection.startTransaction();
		trans.query('insert into product_reviews (title,rrating,content,userId,productReviewedId) values ('+
				connection.escape(req.body.title)+','+connection.escape(req.body.rrating)+','+connection.escape(req.body.content)+','+
				connection.escape(rest[0][0][0].userId)+','+connection.escape(rest[1][0][0].productId)+')',function(err,info){

			if (err)
			{
				trans.rollback();
				console.log('error in insert');
			}
			else{
				trans.query('select productReviewId from product_reviews where userId='+connection.escape(rest[0][0][0].userId)+' and title='+connection.escape(req.body.title),
						function(err,info2){
					if(err)trans.rollback();
					else
					{
						console.log('trying update');
						if (req.params.isBid=="true")
						{
							trans.query('insert into reviews_bid_event (productReviewId,bidEventId) values ('+connection.escape(info2[0].productReviewId)+',' + connection.escape(req.params.id)+')',
									function(err,info){
								if(err){
									console.log('error in update');
									trans.rollback();
								}
								else{
									trans.commit();
									res.json(true);
								}
							});
						}
						else
						{
							trans.query('insert into reviews_buy_event (productReviewId,buyEventId) values ('+connection.escape(info2[0].productReviewId)+',' + connection.escape(req.params.id)+')',
									function(err,info){
								if(err){
									console.log('error in update');
									trans.rollback();
								}
								else{
									trans.commit();
									res.json(true);
								}

							});
						}


					}
				});



			}



		});

		trans.execute();
			});

		});



//Add a bid aquiiii!!

app.post('/Basket.js/addBid/:id',function(req,res){


	console.log('got here');
	function getEventWinning() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select amount from bid_events left outer join bids on winningBid=bidId where bid_events.bidEventId='+connection.escape(req.params.id);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};

	function getBidder() 
	{
		console.log(req.params.id);
		var defered = Q.defer();
		var userquery='select userId from users where username='+connection.escape(req.body.bidder);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getEventWinning(),getBidder()]).then(function(rest){
		console.log('JHAASHDBAMNSDBAJ');
		if (rest[0][0][0].amount>req.body.ammount)
		{
			console.log('falseeee');
			var response ={
					"state":false
			};
			res.json(response);
		}
		else
		{

			var trans = connection.startTransaction();
			console.log('init');
			trans.query('insert into bids (amount,bidTime,userId,bidEventId) values ('+connection.escape(req.body.ammount)+','+connection.escape(req.body.date)+','+
					connection.escape(rest[1][0][0].userId)+','+connection.escape(req.params.id)+')',function(err,info){

				if (err)
				{
					trans.rollback();
					console.log('error in insert');
				}
				else{
					console.log('trying update');
					trans.query('update bid_events S set winningBid='+connection.escape(info.insertId)+' where bidEventId='+connection.escape(req.params.id),
							function(err,info){
						if(err){
							console.log('error in update');
							trans.rollback();
						}
						else{
							var response ={
									"state":true
							};
							trans.commit();

							res.json(response);
						}

					});


				}
			});
			trans.execute();
		}
	});	
});
//Search for something
app.get('/Basket.js/search/:searchQuery',function(req,res)
		{
	console.log(req.params.searchQuery);
	function getBuyEvents () 
	{
		var defered = Q.defer();
		var userquery='select * from Buy_Events natural join Products natural join Manufacturers join Users on userId=soldBy where available>0 and btitle like "%'+req.params.searchQuery+'%"';
		console.log(userquery);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getBidEvents () 
	{
		var defered = Q.defer();
		var userquery='select Bid_Events.*,Products.*,Manufacturers.*,Users.*,bidTime as time,w.username as wusername,amount from Bid_Events natural join Products natural join Manufacturers join Users on userId=soldBy left outer join Bids on bidId=winningBid left outer join Users as w on w.userId=Bids.userId where bidTitle like "%'+req.params.searchQuery+'%" and finished=false';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getBuyEvents(),getBidEvents()]).then(function(rest)
			{
		console.log('yeeah');
		var eventList = new Array();
		var bidList   = new Array();

		for (var i=0;i<rest[0][0].length;i++)
		{

			eventList.push(new BuyEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].price,rest[0][0][i].sellingTime,false,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].buyEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].btitle,rest[0][0][i].pic,1)); 
		}

		for (var i=0;i<rest[1][0].length;i++)
         {
                 console.log(rest[1][0][i].amount);
                 if(rest[1][0][i].wusername!=null)
                 bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,new Bid(rest[1][0][i].wusername,rest[1][0][i].time,rest[1][0][i].amount),rest[1][0][i].finished,rest[1][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
                 else
                     bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,null,rest[1][0][i].finished,rest[1][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

         }
		console.log('sali');
		var response =
		{
				"buyEvents":eventList,
				"bidEvents":bidList
		};

	
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
		var userquery='select * from Buy_Events natural join Products natural join Manufacturers join Users on userId=soldBy join Categories on Categories.categoryId=buycategory where available>0 and btitle like "%'+req.params.searchQuery+'%" and name='+connection.escape(req.params.cat);
		console.log(userquery);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getBidEvents () 
	{
		var defered = Q.defer();
		var userquery='select Categories.*,Bid_Events.*,Products.*,Manufacturers.*,Users.*,bidTime as time,w.username as wusername,amount from Bid_Events natural join Products natural join Manufacturers join Users on userId=soldBy left outer join Bids on bidId=winningBid left outer join Users as w on w.userId=Bids.userId join Categories on Categories.categoryId=bidcategory where finished=false and bidTitle like "%'+req.params.searchQuery+'%" and name='+connection.escape(req.params.cat);
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
				bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,new Bid(rest[1][0][i].wusername,rest[1][0][i].time,rest[1][0][i].amount),rest[1][0][i].finished,rest[1][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			else
				bidList.push(new BidEvent(new product(rest[1][0][i].pname,rest[1][0][i].sellerPId,rest[1][0][i].mname,rest[1][0][i].width,rest[1][0][i].height,rest[1][0][i].depth,rest[1][0][i].dimensions),rest[1][0][i].startingBid,rest[1][0][i].startingTime,rest[1][0][i].endingTime,rest[1][0][i].features,rest[1][0][i].description,rest[1][0][i].minBid,rest[1][0][i].bidEventId,rest[1][0][i].username, rest[1][0][i].rating,rest[1][0][i].bidTitle,rest[1][0][i].picture,null,rest[1][0][i].finished,rest[1][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

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
app.post('/Basket.js/remBid/:id/:type/:winner', function(req,res){
	function getWinner() 
	{
		var defered = Q.defer();
		var userquery='select userId from users where userId='+connection.escape(req.params.winner);
		console.log(userquery);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};

	var trans = connection.startTransaction();
	if (req.params.type==1)
		trans.query('update bid_events set accepted=true where bidEventId='+connection.escape(req.params.id),function(err,info){
			console.log('ACCEPTED');

			if(err)
				trans.rollback();

			else
			{
                trans.query('select * from bid_events natural join products, bids where winningBid = bidId and bid_events.bidEventId='+connection.escape(req.params.id),function(err,bideventInfo){

                    trans.query('select email from users where username ='+connection.escape(req.params.winner),function(err,winneremail){
                        if(err)
                            trans.rollback();
                        else{
                            var smtpTransport = nodemailer.createTransport("SMTP",
                                {
                                    service: "Gmail",
                                    auth: {
                                        user: "basketservices@gmail.com",
                                        pass: "tito12@@"
                                    }
                                });
                            var mailOptions = {
                                from: "Basket Services <basketservices@gmail.com>", // sender address
                                to: req.params.email, // list of receivers
                                subject: "Your basket account ", // Subject line
                                text: 'Hello,\nYou have won the following bid!:\n'+bideventInfo[0].pname+'\nDescription:'+bideventInfo[0].description+'\nFor '+bideventInfo[0].amount+'\nHave a basketful day!'//, // plaintext body
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
                        }
                    });

                });


//				trans.query('insert into baskets (bname,userId) values("won bid",'+connection.escape(rest[0][0][0].userId),function(err,info)
//				{
//				if (err)trans.rollback();
//				else
//				{
//				trans.query('insert into orders (amount,orderTime,userId,bankAccountId,cardId,withbasketId,type,shipTo) values('
//				+connection.escape(),function(err,info)
//				{
//				if(err)trans.rollback();
//				else
//				{

//				}
//				});
//				}

//				});
				trans.commit();
				res.json(true);
			}			
		});
	else
		trans.query('update bid_events set declined=true where bidEventId='+connection.escape(req.params.id),function(err,info){
			console.log('REHECTED');
			if(err)
				trans.rollback();
			else
			{


				trans.commit();
				var smtpTransport = nodemailer.createTransport("SMTP",
                                {
                                    service: "Gmail",
                                    auth: {
                                        user: "basketservices@gmail.com",
                                        pass: "tito12@@"
                                    }
                                });
                            var mailOptions = {
                                from: "Basket Services <basketservices@gmail.com>", // sender address
                                to: req.params.email, // list of receivers
                                subject: "Your basket account ", // Subject line
                                text: 'Hello,\nYou have won the following bid!:\n'+bideventInfo[0].pname+'\nDescription:'+bideventInfo[0].description+'\nFor '+bideventInfo[0].amount+'\nHoweever the seller has declined your offer.  Try with another item!\nHave a basketful day!'//, // plaintext body
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
                                				res.json(true);

                            });

                            console.log('Sent email!!');
			}				
		});
	trans.execute();


});
//Query for deletion

function deleteUser (userId , callback) 
{

	// console.log(callback);
	// var defered = Q.defer();
	var userquery=' UPDATE users set active_user = 0 where userId ='+connection.escape(userId);

	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log("Problem in query");
			});
		}
		else
			callback(err, response);
	});
};
function removeWinningBids (userId , callback) 
{

	// console.log(callback);
	// var defered = Q.defer();
	var userquery=' select bidId,bid_events.bidEventId from bids, bid_events where bidId=winningBid and userId ='+connection.escape(userId);

	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log("Problem in query");
				console.log(err);
			});
		}
		else{
			console.log(err);
			console.log(response);
			if(response.length==0){
				callback(err, response);
			}
			else{
				for(var i=0;i<response.length;i++){
					console.log(response[i]);
					var result1 = response;
					var bidEventId = response[0].bidEventId;
					var bidId = response[0].bidId;
					console.log(response);
					var userquery2 = 'select bidId, amount from bids natural join bid_events where bid_events.bidEventId ='+connection.escape(bidEventId)+' and amount >= (select max(amount) from bids where bidEventId = '+connection.escape(bidEventId)+')'      ;
					console.log(userquery2);
					connection.query(userquery2,function(err,response1){
						console.log(err);
						console.log(response1);
						var userquery3='select bidId, amount \
						from (select * from bids where bidEventId = '+bidEventId+' and amount <> (select max(amount) from bids where bidEventId = '+bidEventId+'))as bds natural join bid_events \
						where bid_events.bidEventId = '+bidEventId+' \
						and amount = \
						(select max(amount) \
						from bids \
						where bidEventId = '+bidEventId+' and amount <> (select max(amount) from bids where bidEventId = '+bidEventId+'))';
						connection.query(userquery3,function(err,response1){
							console.log(err) ;
							console.log(response1);
							if(response1.length==0){
								var userquery4 = 'update  bid_events set winningBid = NULL where bidEventId='+connection.escape(bidEventId);
								console.log(userquery4);
								connection.query(userquery4, function(err,response2){
									if(err){
										console.log(err) ;

									}
									else
										callback(err, response);
								});

							}
							else{
								var nextBidId = response1[0].bidId;
								var userquery4 = 'update  bid_events set winningBid= '+connection.escape(nextBidId)+' where bidEventId='+connection.escape(bidEventId);
								console.log(userquery4);
								connection.query(userquery4, function(err,response2){
									console.log(err) ;
									console.log("Success");
									if(err){

									}
									else
										callback(err, response);
								});
							}
						});


					});
				}
			}

		}

	});
};
function removeEvents (userId , callback) 
{
	var userquery=' UPDATE bid_events set accepted = 0, declined =1, finished=1 where soldBy ='+connection.escape(userId);
	var userquery2=' UPDATE buy_events set available=0 where soldBy ='+connection.escape(userId);

	console.log(userquery);
	console.log(userquery2);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log("Problem in query");
			});
		}
		else{
			connection.query(userquery2, function(err,response){
				if (err){
					connection.rollback(function() {
						console.log("Problem in query");
					});
				}
				else{
					callback(err, response);
				}
			});
		}
	});
};

app.post('/Basket.js/TerminateEvent/:id', function(req,res)
		{
			console.log('heereeeeee');
			console.log(req.params.id);
			var trans = connection.startTransaction();
			trans.query('update bid_events set declined=true where bidEventId='+connection.escape(req.params.id),function(err,info){
				if(err){
					trans.rollback();
				}
				else
				{
					trans.commit();
					res.json(true);
				}
			});
			trans.execute();	
		});

//Delete user
app.post('/Basket.js/UserDelete/', function(req,res){
	console.log(req.body);
	try{
		connection.beginTransaction(function(err) {
			getUserId(req, function(err,response){
				console.log(response);
				var userId = response[0].userId;
				deleteUser(userId, function(err, response){
					removeWinningBids(userId, function(err, response){
						removeEvents(userId, function(err,response){
							console.log("Wiii");
							res.json(true);
						});
					});
				});
			});
		});
	}
	catch(error){
		console.log("Error"+error);
		res.json(false);
	}
});


//Create a user

function insertUser (req , callback) 
{
	console.log(req);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`users` (`userId`, `username`, `firstName`, `lastName`, `password`, `email`, `age`, `birthday`, `rating`) \
		VALUES (NULL, '+connection.escape(req.body.username)+', '+connection.escape(req.body.firstName)+', '+connection.escape(req.body.lastName)+', '+connection.escape(req.body.password)+', '+connection.escape(req.body.email)+', '+connection.escape(req.body.age)+', '+connection.escape(req.body.bdYear)+'-'+connection.escape(req.body.bdMonth)+'-'+connection.escape(req.body.bdDay)+', \'0\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else
			callback(err, response);
	});
};
function getInsertedUserId (req , callback) 
{
	// var defered = Q.defer();
	var userquery='select userId from users where username= '+connection.escape(req.body.username)+' and firstName = '+connection.escape(req.body.firstName)+' and lastName = '+connection.escape(req.body.lastName)+' and email = '+connection.escape(req.body.email);
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) console.log(err);
		callback(err, response);
	});
};	
function insertShipAddress (userId, req  ,callback) 
{
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`address` (`AddressId`, `line1`, `line2`, `city`, `country`, `zipCode`, `userId`, `state`) \
		VALUES (NULL,'+connection.escape(req.body.shippingAdress[0].line1)+','+connection.escape(req.body.shippingAdress[0].line2)+','+connection.escape(req.body.shippingAdress[0].city)+','+connection.escape(req.body.shippingAdress[0].country)+','+connection.escape(req.body.shippingAdress[0].zipCode)+','+connection.escape(userId)+','+connection.escape(req.body.shippingAdress[0].state)+');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

			callback(err, response);
	});
};

function getInsertedShipAddress (req,callback) 
{
	// var defered = Q.defer();
	var userquery='select AddressId from address where userId= \''+userId+'\' and line1 = \''+req.body.shippingAdress[0].line1+'\'';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) console.log(err);
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
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

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
		if (err) {
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

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
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else  {
			callback(err, response);
            console.log(response);
        }
	});
};
function getInsertedBillAddress (userId, req,callback) 
{
	// var defered = Q.defer();
	var userquery='select AddressId from address where userId= \''+userId+'\' and line1 = '+connection.escape(req.body.billingAdress[0].line1);
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) console.log(err);
		callback(err, response);
	});
};	

function getInsertedAddress (userId, req,callback) 
{
	console.log(req);

	// var defered = Q.defer();
	var userquery='select AddressId from address where userId= '+connection.escape(userId)+' and line1 = '+connection.escape(req.body.billing.line1);
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err) console.log(err);
		callback(err, response);
	});
};	
function insertCreditCards (userId, billId, req,callback) 
{
	// var defered = Q.defer();
	console.log(req);
	var userquery='INSERT INTO `myfirstsql`.`credit_cards` (`cardNum`, `secCode`, `expMonth`, `expYear`, `name`, `userId`, `BillingId`) \
		VALUES ('+connection.escape(req.body.creditCards[0].cardNum)+','+connection.escape(req.body.creditCards[0].secCode)+','+connection.escape(req.body.creditCards[0].expMonth)+','+connection.escape(req.body.creditCards[0].expYear)+','+connection.escape(req.body.creditCards[0].name)+','+connection.escape(userId)+','+connection.escape(billId)+');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else
			callback(err, response);
	});
};

app.post('/Basket.js/create/:id', function(req,res){

	var userId =-1;
	var insertedBill ="";
	console.log(req.body.creditCards);
	res.json(false);
	console.log(req.body);
	try{
		connection.beginTransaction(function(err) {
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

									connection.commit(function(err) {
										if (err) { 
											connection.rollback(function() {
												console.log(err);
											});
										}

										console.log("Success");

										res.json(true);
									});
								});
							});
						});
					});
				});
			});
		});
	}
	catch(error){
		console.log("Error"+error)
		res.json(false)
	}

});
function insertAdmin (req , callback) 
{
	console.log(req);
	// console.log(callback);
	// var defered = Q.defer();
	var userquery='INSERT INTO `myfirstsql`.`admins` (`adminId`, `username`, `firstName`, `lastName`, `password`, `email`, `age`) \
		VALUES (NULL, '+connection.escape(req.body.username)+', '+connection.escape(req.body.firstName)+', '+connection.escape(req.body.lastName)+', '+connection.escape(req.body.password)+', '+connection.escape(req.body.email)+', '+connection.escape(req.body.age)+');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

			callback(err, response);
	});
};
app.post('/Basket.js/createAdmin/:id', function(req,res){
	connection.beginTransaction(function(err) {

		insertAdmin(req,function(err,result){
			connection.commit(function(err) {
				if (err) { 
					connection.rollback(function() {
						console.log(err);
					});
				}

				console.log("Success adding admin");

				res.json(true);
			});
		});
	});
});



//Place an order buy
app.post('/Basket.js/PlaceOrder/:uId/:cId/:basket/:sId/:date/:total', function(req,res)
		{
	console.log("Placing an Order!");
	var transaction= connection.startTransaction();
	for(var i = 0; i < req.body.buyEvents.length; i++){
		var quan =req.body.buyEvents[i].item_quantity;
	    transaction.query("select available from buy_events where buyEventId="+ connection.escape(req.body.buyEvents[i].id), quan,function(err,info){
			console.log('checking');
			console.log(info[0]<this.values);
	    	if(err && trans.rollback) {transaction.rollback(); 
	    	var rr={
	    		"state":false	
	    	};
	    	res.json(rr);
	    	}
		    else if(info[0].available<this.values) {console.log(info[0].available); console.log(this.values); transaction.rollback(); var rr={
	    		"state":false	
	    	};
	    	res.json(rr);}
	    });
		}
	
	for(var i = 0; i < req.body.buyEvents.length; i++)
	{
		var quan =req.body.buyEvents[i].item_quantity;
	transaction.query('update buy_events set available=available-'+connection.escape(quan)+' where buyEventId='+connection.escape(req.body.buyEvents[i].id),quan,function(err,info){
		console.log('updating');
		if(err && transaction.rollback) {transaction.rollback(); var rr={
	    		"state":false	
    	};
    	res.json(rr);}	    
	});
	}

		
	transaction.query('insert into orders (amount,orderTime,userId,cardId,withbasketId,type,shipTo) values ('+connection.escape(req.params.total)
			+','+connection.escape(req.params.date)+','+connection.escape(req.params.uId)+','+connection.escape(req.params.cId)+
			','+connection.escape(req.params.basket)+','+connection.escape('buy')+','+connection.escape(req.params.sId)+')',function (err,info){
		if (err){
            console.log(err)
            transaction.rollback();
        }
		else
		{

			console.log('inserted value');
			var email = 'Hello,\n\n' +
                'You have just placed an order!\n' +
                'Your order details:\n' +
                'Shipping address:\n' +
                req.body.shipAdress.line1+
                '\n'+req.body.shipAdress.line2+
                '\n'+req.body.shipAdress.country+
                '\n'+req.body.shipAdress.state+
                '\n'+req.body.shipAdress.city+'\n' +
                'Credit Card number:\n' +
                req.body.creditCard.cardNum+'\n' +
                'And the items:\n';
            var products ='';
            var totalammount = 0;
            for(var i=0;i<req.body.buyEvents.length;i++){
                products+=req.body.buyEvents[i].btitle+' Amount:'+req.body.buyEvents[i].item_quantity+'\n'
                totalammount+=req.body.buyEvents[i].price*req.body.buyEvents[i].item_quantity;
            }
            var total = 'Totaling in: $'+totalammount;
            console.log('inserted value');
            //transaction.commit();
            transaction.query('select * from users where userId ='+connection.escape(req.params.uId),function(err, uinfo){
                console.log(err);
                var smtpTransport = nodemailer.createTransport("SMTP",
                    {
                        service: "Gmail",
                        auth: {
                            user: "basketservices@gmail.com",
                            pass: "tito12@@"
                        }
                    });
                var mailOptions = {
                    from: "Basket Services <basketservices@gmail.com>", // sender address
                    to: uinfo[0].email, // list of receivers
                    subject: "Your basket account ", // Subject line
                    text: email+products+total//, // plaintext body
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
                    var rr={
		    		"state":true	
		    	};
		    	res.json(rr);
                });
            });
			
		}
	});
	transaction.execute();
	transaction.commit();
	
	
	





		});

//place a bid order anadir!!!!
app.post('/Basket.js/BPlaceOrder/:uId/:cId/:basket/:sId/:date/:total', function(req,res)
		{
	console.log("Placing a Bid Order!");

	var transaction = connection.startTransaction();

	transaction.query('insert into baskets (bname,userId) values("won bid",'+connection.escape(req.params.uId)+')',function(err,info)		
			{
		console.log(info.insertId);
		if(err)transaction.rollback();
		else
		{


			transaction.query('insert into in_bid_basket (basketId,bidEventId) values ('+connection.escape(info.insertId)+','+connection.escape(req.params.basket)+')',function(err,inf)
					{
				if(err)transaction.rollback();
				else
				{
					transaction.query('insert into orders (amount,orderTime,userId,cardId,withbasketId,type,shipTo) values ('+connection.escape(req.params.total)
							+','+connection.escape(req.params.date)+','+connection.escape(req.params.uId)+','+connection.escape(req.params.cId)+
							','+connection.escape(info.insertId)+','+connection.escape('bid')+','+connection.escape(req.params.sId)+')',function (err,info)
							{
						if (err)transaction.rollback();
						else
						{
							transaction.query('update bid_events set ordered=true where bidEventId='+connection.escape(req.params.basket),function(err,info)
									{
								if(err)transaction.rollback();
								else
								{
									console.log('inserted value');
									transaction.commit();
									res.json(true);
								}
									});

						}
							});
				}
					});

		}
			});


	transaction.execute();




		});
//Create a basketWW
app.post('/Basket.js/NewBasket/:username', function(req,res)
		{
	console.log("found it!!!!");
	function getUser() 
	{
		var defered = Q.defer();
		var query='select userId from users where username='+connection.escape(req.params.username);
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};

	Q.all([getUser()]).then(function(rest)
			{

		var trans = connection.startTransaction();

		trans.query('insert into baskets (userId,bname) values('+connection.escape(rest[0][0][0].userId)+','+connection.escape(req.body.bname)+')',function(err,info){
			console.log(rest[0][0][0].userId);
			console.log(req.body.bname);
			if (err)
			{
				trans.rollback();
				console.log('error in insert');
			}
			else{
				console.log('successfull!!');
				trans.commit();

				var rp=
				{
						"value":info.insertId
				}
				res.json(rp);
				}
				
		
	});
	trans.execute();	
});
});

//rate user

app.post('/Basket.js/RateUser/:rater/:ratee/:rating', function(req,res)
		{
	function getRatingCount()
	{
		var defered = Q.defer();
		var userquery='select count(*) as total,sum(user_reviews.rating) as result from user_reviews join users on userReviewedId=users.userId where username='
			+connection.escape(req.params.ratee);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getRaterId() 
	{
		var defered = Q.defer();
		var userquery='select userId from users where username='+connection.escape(req.params.rater);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getRateeId() 
	{
		var defered = Q.defer();
		var userquery='select userId from users where username='+connection.escape(req.params.ratee);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	Q.all([getRatingCount(),getRaterId(),getRateeId()]).then(function(rest)	{
		var trans= connection.startTransaction();

		trans.query('insert into user_reviews (rating,userId,userReviewedId) values ('+connection.escape(req.params.rating)+','
				+connection.escape(rest[1][0][0].userId)+','+connection.escape(rest[2][0][0].userId)+')', function (err,info){

			if (err)
				trans.rollback();
			else
			{
				console.log(req.params.rating);
				console.log(rest[0][0][0].result+req.params.rating);
				var num=parseFloat(req.params.rating);
				var newR= parseFloat(rest[0][0][0].result);
				var t= parseFloat(rest[0][0][0].total);
				t=t+1;
				var total = (num+newR)/(t);
				console.log(total);
				console.log(rest[0][0][0].total+1);
				trans.query('update users set rating='+connection.escape(total)+' where userId='+ connection.escape(rest[2][0][0].userId),function(err,info){

					console.log(req.params.rating);
					console.log(rest[0][0][0].result+req.params.rating);
					var num=parseFloat(req.params.rating);
					var newR= parseFloat(rest[0][0][0].result);
					var t= parseFloat(rest[0][0][0].total);
					t=t+1;
					var total = (num+newR)/(t);
					console.log(total);
					console.log(rest[0][0][0].total+1);
					trans.query('update users set rating='+connection.escape(total)+' where userId='+
							connection.escape(rest[2][0][0].userId),function(err,info)
							{
						if(err)
							trans.rollback();
						else
						{
							var response={
									"value":total	
							};
							trans.commit();
							res.json(response);
						}
							});

				});

			}
			trans.execute();
		});


		trans.execute();
	});





		});
//Remove a basket
app.post('/Basket.js/RemoveBasket', function(req,res)
		{
	console.log(req.body);
	var trans = connection.startTransaction();


	function error(err) {
		if(err && trans.rollback) {trans.rollback(); console.log(err);}

	}

	function error2(err) {
		if(err && trans.rollback) {trans.rollback(); console.log(err);}
		else{
			trans.commit();
			res.json(true);
		}
	}

	trans.query("delete from in_buy_basket where basketId="+connection.escape(req.body.id), error);
	trans.query("delete from baskets where basketId="+connection.escape(req.body.id), error2);



	trans.execute();

		});
function insertCreditCard (userId, billId, req,callback) 
{
	// var defered = Q.defer();
	console.log(req);
	var userquery='INSERT INTO `myfirstsql`.`credit_cards` (`cardId`, `cardNum`, `secCode`, `expMonth`, `expYear`, `name`, `userId`, `BillingId`) \
		VALUES (NULL,\''+req.body.cardNum+'\',\''+req.body.secCode+'\',\''+req.body.expMonth+'\',\''+req.body.expYear+'\',\''+req.body.name+'\',\''+userId+'\',\''+billId+'\');';
	console.log(userquery);
	connection.query(userquery, function(err, response) {
		if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

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
	connection.beginTransaction(function(err) {

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
						connection.commit(function(err) {
							if (err) { 
								connection.rollback(function() {
									console.log(err)       ;
								});
							}

							console.log("Success adding admin");

							res.json(result.insertId.toString());
						});
					});
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

function updateCreditCard (cardId, req , callback) 
{
        console.log(req);
        // console.log(callback);
        // var defered = Q.defer();
        var userquery='UPDATE `myfirstsql`.`credit_cards` SET `cardNum` = \''+req.cardNum+'\', secCode = \''+req.secCode+'\', expMonth = \''+req.expMonth+'\', expYear = \''+req.expYear+'\', name = \''+req.name+'\' WHERE `credit_cards`.`cardId` = \''+cardId+'\';';

        console.log(userquery);
        connection.query(userquery, function(err, response) {
                if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

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
                if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

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
               if (err){
			connection.rollback(function() {
				console.log(err);
			});
		}
		else

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
        connection.beginTransaction(function(err) {

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
	                                        connection.commit(function(err) {
	                    if (err) {
	                        connection.rollback(function() {
	                            console.log(err);
	                        });
	                    }

	                    console.log("SUCESS");
	                    console.log(response);
	                    res.json(true);
	                });
	                                });
	                        });
	                });
	        }); 
        }  );     
    
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
    connection.beginTransaction(function(err) {

        getUserId(user,function(err,result){
            console.log(result);
            var userId = result[0].userId;
            console.log(userId);
            insertShippingAddress(userId, req, function(err,response){
                connection.commit(function(err) {
                    if (err) {
                        connection.rollback(function() {
                            console.log(err);
                        });
                    }

                    console.log("SUCESS");
                    console.log(response);
                    res.json(response.insertId.toString());
                });

            });


	    });
    } );

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

app.post('/Basket.js/NewBidSell/:uId/:cat', function(req,res)
{
	var bitmap = new Buffer(req.body.picture,"base64");
	var transaction = connection.startTransaction();

	transaction.query('select categoryId from categories where name='+connection.escape(req.params.cat),function(err,info2){
	
		if(err)trans.rollback();
		else{
		transaction.query('insert into Manufacturers (mname) values ('+connection.escape(req.body.product.manufacturer)+')',function(err,info){
			if(err)transaction.rollback();
			else
			{
				console.log(req.body);
				//insert product
				transaction.query('insert into products (sellerPId,pname,features,dimensions,manufacturerID,categoryId, width,depth,height) values ('+
						connection.escape(req.body.product.productPId)+','+connection.escape(req.body.product.pname)+','+connection.escape(req.body.features)+','+
						connection.escape(req.body.product.dimensions)+','+connection.escape(info.insertId)+','+connection.escape(info2[0].categoryId)+','+connection.escape(req.body.product.width)
						+','+connection.escape(req.body.product.width)+','+connection.escape(req.body.product.height)+')',function(err,info){
					if(err)transaction.rollback();
					else
					{
						//insert buy event
						transaction.query('insert into bid_events (startingBid,endingTime,description,soldBy,productId,minBid,bidTitle,picture,bidcategory) values('+
								connection.escape(req.body.startingBid)+','+connection.escape(req.body.endingTime)+','+connection.escape(req.body.description)+','+connection.escape(req.params.uId)+','
								+connection.escape(info.insertId)+','+connection.escape(req.body.minBid)+','+connection.escape(req.body.bidTitle)+',?,'+connection.escape(info2[0].categoryId)+')',[bitmap],function(err,info)
							{
							if (err)transaction.rollback();
							else
							{
								transaction.commit();
								res.json(true);
							}
						});
					}
				});
			}
		});

		}
		
	});
	transaction.execute();
	
	
});
//Create a sell buy event
String.prototype.getBytes = function () {
	  var bytes = [];
	  for (var i = 0; i < this.length; ++i) {
	    bytes.push(this.charCodeAt(i));
	  }
	  return bytes;
	};
	function createHexString(arr) {
	    var result = "";
	    for (var i = 0; i < arr.length; i++) {
	        var str = arr[i].toString(16);

	        z = 8 - str.length + 1;
	        str = Array(z).join("0") + str;

	        result += str;
	    }

	    return result;
	}
	
app.post('/Basket.js/NewBuySell/:uId/:quantity/:cat', function(req,res)

{
	var bitmap = new Buffer(req.body.pic,"base64");
	console.log(bitmap);
	var transaction = connection.startTransaction();
	transaction.query('select categoryId from categories where name='+connection.escape(req.params.cat),function(err,info2){

		if(err)trans.rollback();
		else{
		transaction.query('insert into Manufacturers (mname) values ('+connection.escape(req.body.product.manufacturer)+')',function(err,info){
			if(err)transaction.rollback();
			else
			{
				//insert product
				transaction.query('insert into products (sellerPId,pname,features,dimensions,manufacturerID,categoryId, width,depth,height) values ('+
						connection.escape(req.body.product.productPId)+','+connection.escape(req.body.product.pname)+','+connection.escape(req.body.features)+','+
						connection.escape(req.body.product.dimensions)+','+connection.escape(info.insertId)+','+connection.escape(info2[0].categoryId)+','+connection.escape(req.body.product.width)
						+','+connection.escape(req.body.product.width)+','+connection.escape(req.body.product.height)+')',function(err,info){
					if(err)transaction.rollback();
					else
					{
						//insert buy event
						transaction.query('insert into buy_events (price,description,soldBy,productId,btitle,pic,available,buycategory) values('+
								connection.escape(req.body.price)+','+connection.escape(req.body.description)+','+connection.escape(req.params.uId)+','
								+connection.escape(info.insertId)+','+connection.escape(req.body.btitle)+',?,'+
								connection.escape(req.params.quantity)+','+connection.escape(info2[0].categoryId)+')',[bitmap],function(err,info)
							{
							if (err)transaction.rollback();
							else
							{
								transaction.commit();
								var resp = {
										"value":info.isertId
								}
								
								res.json(resp);
							}
						});
					}
				});
			}
		});

		}

	});
	transaction.execute();

		});


var products = new Array(
		new product("Alienware M17x", 1204054932,"Dell Inc.",20,15,40),
		new product("Macbook", 1203323,"Apple Inc.",20,15,40)
);
//Search for product
app.get('/Basket.js/Product/:searchQuery', function(req,res){

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
		var userquery='select * from Bid_Events natural join Products natural join Manufacturers join Users on userId=soldBy where finished=false and bidTitle like \'%'+connection.escape(req.params.searchQuery)+'%\'';
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

app.get('/Basket.js/UpdateBidSeller/:uId', function(req,res)
		{
	console.log(req.params.uId);
	function getFinishedBidEvents () 
	{
		var defered = Q.defer();
		var query='SELECT Users.*,Bid_Events.*,Products.*,Manufacturers.*,Bids.bidTime,Bids.amount,b.username as winnerName FROM  Users join Bid_Events on soldBy=userId natural join Products natural join Manufacturers left outer join Bids on bidId=winningBid left outer join Users as b on b.userId=Bids.userId where accepted=false and declined=false and Users.userId='+connection.escape(req.params.uId);
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};

	Q.all([getFinishedBidEvents()]).then(function(rest)
			{
	
		
		 var sBidEvents= new Array();
		   for (var i=0;i<rest[0][0].length;i++)
		   {
			  
			   if(rest[0][0][i].winnerName!=null)
			   sBidEvents.push(new BidEvent(new product(rest[0][0][i].pname,rest[0][0][i].productPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].startingBid,rest[0][0][i].startingTime,rest[0][0][i].endingTime,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].minBid,rest[0][0][i].bidEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].bidTitle,rest[0][0][i].picture,new Bid(rest[0][0][i].winnerName,rest[0][0][i].bidTime,rest[0][0][i].amount),rest[0][0][i].finished,rest[0][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			   else
			   sBidEvents.push(new BidEvent(new product(rest[0][0][i].pname,rest[0][0][i].productPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].startingBid,rest[0][0][i].startingTime,rest[0][0][i].endingTime,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].minBid,rest[0][0][i].bidEventId,rest[0][0][i].username,rest[0][0][i].rating,rest[0][0][i].bidTitle,rest[0][0][i].picture,null,rest[0][0][i].finished,rest[0][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

		   }
		var response={
				"events":sBidEvents
		};
		res.json(response);
			});

});



///////////////////////////////////////////
function getadminproducts(query, callback) {
	console.log("Loading admin products "+query);
	connection.query('select * from products natural join manufacturers where pname like \'%'+query+'%\''  , function(err, response) {
		if (err) console.log(err);
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
		if (err) console.log(err);
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
		if (err) console.log(err);
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
		if (err) console.log(err);
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
		if (err) console.log(err);
		callback(err, response);
	});
};

app.get('/Basket.js/ProductReport/:day/:month/:year/:type/:pid', function(req,res){



	var month = req.params.month;

	var datereq = req.params.year+"-"+month+"-"+req.params.day;

	console.log(req.params.type);
	console.log(datereq);


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

app.get('/Basket.js/SalesReport/:day/:month/:year/:type', function(req,res){

	var month = req.params.month;

	var datereq = req.params.year+"-"+month+"-"+req.params.day;

	console.log(req.params.type);
	console.log(datereq);

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



app.get('/Basket.js/CurrentWinning/:id', function(req,res)
		{
	console.log('here!!!! ERRR');

	function getCurrent() 
	{
		var defered = Q.defer();
		var query='select Bids.* from bid_events join bids on bidId=winningBid where bid_events.bidEventId='+connection.escape(req.params.id);
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};

	Q.all([getCurrent()]).then(function(rest)
			{
		console.log('here!!!! ERRR');
		var response =
		{
				"ammount": rest[0][0][0].amount,
				"bidTime": rest[0][0][0].bidTime,
				"bidder":"dummy"
		};
		res.json(response);
			});

		});


app.get('/Basket.js/WinBid/:id', function(req,res)
		{
	function getFinishedBidEvents ()
	{
		var defered = Q.defer();
		var query='select distinct a.*,Bids.*,Bid_Events.*,Products.*,Manufacturers.*,b.*,max(w.bidTime) as time,max(w.amount) as wamount, wu.username as wusername from Users as a natural join Bids natural join Bid_events natural join Products natural join Manufacturers join Users as b on b.userId=soldBy left outer join Bids as w on Bid_Events.winningBid=w.bidId left outer join Users as wu on wu.userId=w.userId   where ordered=false and declined=false and finished=true and wu.userId='+connection.escape(req.params.id)+' or finished=false and a.userId='+connection.escape(req.params.id)+' group by Bid_Events.bidEventId';
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};

	Q.all([getFinishedBidEvents()]).then(function(rest)
			{
		 var BidEvents= new Array();
		   for (var i=0;i<rest[0][0].length;i++)
		   {
			   if(rest[0][0][i].wusername!=null)
		    	BidEvents.push(new BidEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].startingBid,rest[0][0][i].startingTime,rest[0][0][i].endingTime,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].minBid,rest[0][0][i].bidEventId,rest[0][0][i].username, rest[0][0][i].rating,rest[0][0][i].bidTitle,rest[0][0][i].picture,new Bid(rest[0][0][i].wusername,rest[0][0][i].time,rest[0][0][i].wamount),rest[0][0][i].finished,rest[0][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			   else
			    	BidEvents.push(new BidEvent(new product(rest[0][0][i].pname,rest[0][0][i].sellerPId,rest[0][0][i].mname,rest[0][0][i].width,rest[0][0][i].height,rest[0][0][i].depth,rest[0][0][i].dimensions),rest[0][0][i].startingBid,rest[0][0][i].startingTime,rest[0][0][i].endingTime,rest[0][0][i].features,rest[0][0][i].description,rest[0][0][i].minBid,rest[0][0][i].bidEventId,rest[0][0][i].username, rest[0][0][i].rating,rest[0][0][i].bidTitle,rest[0][0][i].picture,null,rest[0][0][i].finished,rest[0][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

		   }
		var response=
		{
				"events":BidEvents
		};

		res.json(response);
			});

		});

function getAdminList (uname, upass, callback) {
	var userquery= 'SELECT * FROM admins where username='+connection.escape(uname)+' and password='+connection.escape(upass)+' and active_user=1';
	connection.query(userquery, function(err, response) {
		if (err) 
			console.log(err);
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
app.get('/Basket.js/User/:id/:password', function(req, res) {
	function getUserInfo (callback) {
		var userquery= 'SELECT * FROM Users where username='+connection.escape(req.params.id)+' and password='+connection.escape(req.params.password)+' and active_user=1';
		connection.query(userquery, function(err, response) {
			if (err) console.log(err);
			callback(err, response);
		});
	};
	function getUserBaskets (id) 
	{

		var defered = Q.defer();
		var userquery= 'SELECT * FROM Baskets natural join Users natural join in_buy_basket natural join Buy_Events natural join Products natural join Manufacturers join Users as b on b.userId=soldBy where Users.userId='+connection.escape(id)+' and available>0 and basketId not in (select basketId as cc from baskets as dd join orders as bb on dd.basketId=bb.withbasketId) order by basketId';
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getEmptyBaskets (id) 
	{

		var defered = Q.defer();
		var userquery= 'SELECT * FROM Baskets where Baskets.basketId not in (select a.basketId from Baskets as a natural join in_buy_basket natural join Buy_Events) and  Baskets.basketId not in (select b.basketId from Baskets as b natural join in_bid_basket) and userId='+connection.escape(id);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getSoldBy (id)
	{
		var defered = Q.defer();
		var userquery= 'SELECT * FROM  Users join Buy_Events on soldBy=userId natural join Products natural join Manufacturers where available>0 and userId='+connection.escape(id);
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};
	function getSoldByBid (id) {
		var defered=Q.defer();
		var userquery= 'SELECT Users.*,Bid_Events.*,Products.*,Manufacturers.*,Bids.bidTime,Bids.amount,b.username as winnerName FROM  Users join Bid_Events on soldBy=userId natural join Products natural join Manufacturers left outer join Bids on bidId=winningBid left outer join Users as b on b.userId=Bids.userId where accepted=false and declined=false and Users.userId='+connection.escape(id);
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
		var userquery= 'select distinct a.*,Bids.*,Bid_Events.*,Products.*,Manufacturers.*,b.*,max(w.bidTime) as time,max(w.amount) as wamount, wu.username as wusername from Users as a natural join Bids natural join Bid_events natural join Products natural join Manufacturers join Users as b on b.userId=soldBy left outer join Bids as w on Bid_Events.winningBid=w.bidId left outer join Users as wu on wu.userId=w.userId   where ordered=false and declined=false and finished=true and wu.userId='+connection.escape(id)+' or finished=false and a.userId='+connection.escape(id)+' group by Bid_Events.bidEventId ';
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
		var userquery= 'select wuser.rating as sellerRating, cc.*,Manufacturers.*, bAddress.line1 as bline1, bAddress.line2 as bline2 , bAddress.country as bcountry, bAddress.zipCode as bzipCode, bAddress.city as bcity, bAddress.state as bstate,shipTo.line1 as sline1, shipTo.line2 as sline2 , shipTo.country as scountry, shipTo.zipCode as szipCode, shipTo.city as scity, shipTo.state as sstate, Orders.*,Buy_Events.*,Products.*,b.*,in_buy_basket.item_quantity from Users as b natural join Orders join Baskets on withbasketId=basketId natural join in_buy_basket natural join Buy_Events natural join Products join Credit_Cards on b.userId=Credit_Cards.userId join Credit_Cards as cc on cc.cardId=Orders.cardId join Address as bAddress on bAddress.AddressId=Credit_Cards.billingId natural join Manufacturers join Users as wuser on wuser.userId=Buy_Events.soldBy join Address as shipTo on shipTo.AddressId=Orders.shipTo where b.userId='+connection.escape(id)+' and type="buy" order by orderId';
		var defered= Q.defer();
		connection.query(userquery, defered.makeNodeResolver());
		return defered.promise;
	};

	function getOrdersBid (id) {
		var userquery= 'select shipTo.line1 as sline1, shipTo.line2 as sline2 , shipTo.country as scountry, shipTo.zipCode as szipCode, shipTo.city as scity, shipTo.state as sstate, wuser.rating as sellerRating,cc.*,Manufacturers.*, bAddress.line1 as bline1, bAddress.line2 as bline2 , bAddress.country as bcountry, bAddress.zipCode as bzipCode, bAddress.city as bcity, bAddress.state as bstate, Orders.*,Bid_Events.*,Products.*,b.*, w.bidTime as time,w.amount as wamount, wu.username as wusername, c.username as seller , c.rating as sellerRating from Users as b natural join Orders join Baskets on withbasketId=basketId natural join in_bid_basket  join Bid_Events on Bid_Events.bidEventId=in_bid_basket.bidEventId natural join Products join Credit_Cards on b.userId=Credit_Cards.userId join Credit_Cards as cc on cc.cardId=Orders.cardId join Address as bAddress on bAddress.AddressId=Credit_Cards.billingId left outer join Bids as w on Bid_Events.winningBid=w.bidId left outer join Users as wu on wu.userId=w.userId join Users as c on c.userId=Bid_Events.soldBy natural join Manufacturers join Users as wuser on wuser.userId=Bid_Events.soldBy join Address as shipTo on shipTo.AddressId=Orders.shipTo where b.userId='+connection.escape(id)+' and type="bid" order by orderId';
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

		Q.all([getShipping(dd),getBilling(dd),getOrders(dd),getCurrentlyBiddingOn(dd),getCreditCards(dd),getSoldByBid(dd),getSoldBy(dd),getUserBaskets(dd),getOrdersBid(dd),getEmptyBaskets(dd)]).then(function(rest){
			//console.log(rest[0][0][0]);

			//get the shipping Address in shipping
			// var shipping= new Array();
			// for (var i=0;i<rest[0][0].length;i++)
			// {
			// 	shipping.push(new Adress(rest[0][0][i].line1,rest[0][0][i].line2,rest[0][0][i].country,rest[0][0][i].zipCode,rest[0][0][i].city,rest[0][0][i].state,rest[0][0][i].AddressId));
			// }
			// //console.log(shipping);

			// //get the billing address in billing
			// var billing= new Array();
			// for (var i=0;i<rest[1][0].length;i++)
			// {
			// 	billing.push(new Adress(rest[1][0][i].line1,rest[1][0][i].line2,rest[1][0][i].country,rest[1][0][i].zipCode,rest[1][0][i].city,rest[1][0][i].state,rest[1][0][i].AddressId));
			// }
			// //console.log(billing);

			// var OrderList = new Array();
			// var oEvents= new Array();
			// if(rest[2][0].length>0)
			// 	var curroId=rest[2][0][0].orderId;

			//get the shipping Address in shipping
			var shipping= new Array();
			for (var i=0;i<rest[0][0].length;i++)
			{
				shipping.push(new Adress(rest[0][0][i].line1,rest[0][0][i].line2,rest[0][0][i].country,rest[0][0][i].zipCode,rest[0][0][i].city,rest[0][0][i].state,rest[0][0][i].AddressId));
			}
			//console.log(shipping);

			//get the billing address in billing
			var billing= new Array();
			for (var i=0;i<rest[1][0].length;i++)
			{
				billing.push(new Adress(rest[1][0][i].line1,rest[1][0][i].line2,rest[1][0][i].country,rest[1][0][i].zipCode,rest[1][0][i].city,rest[1][0][i].state,rest[0][0][i].AddressId));
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
							OrderList.push(new Order(rest[2][0][i-1].sellingTime,new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate)),0,oEvents,new Adress(rest[2][0][i-1].sline1,rest[2][0][i-1].line2,rest[2][0][i-1].scountry,rest[2][0][i-1].szipCode,rest[2][0][i-1].scity,rest[2][0][i-1].sstate),null));
							oEvents= new Array();
						}
						oEvents.push(new BuyEvent(new product(rest[2][0][i].pname,rest[2][0][i].sellerPId,rest[2][0][i].mname,1,1,1,rest[2][0][i].dimensions),rest[2][0][i].price,rest[2][0][i].sellingTime,false,rest[2][0][i].features,rest[2][0][i].description,rest[2][0][i].basketId,rest[2][0][i].username,rest[2][0][i].sellerRating,rest[2][0][i].btitle,rest[2][0][i].pic,rest[2][0][i].item_quantity)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
						OrderList.push(new Order(rest[2][0][i].sellingTime,new CreditCard(rest[2][0][i].cardId,rest[2][0][i].cardNum,rest[2][0][i].expMonth,rest[2][0][i].expYear,rest[2][0][i].secCode,rest[2][0][i].name,new Adress(rest[2][0][i].bline1,rest[2][0][i].bline2,rest[2][0][i].bcountry,rest[2][0][i].bzipCode,rest[2][0][i].bcity,rest[2][0][i].bstate)),0,oEvents,new Adress(rest[2][0][i].sline1,rest[2][0][i].sline2,rest[2][0][i].scountry,rest[2][0][i].szipCode,rest[2][0][i].scity,rest[2][0][i].sstate),null));
					}
					else
					{
						OrderList.push(new Order(rest[2][0][i-1].sellingTime,new CreditCard(rest[2][0][i-1].cardId,rest[2][0][i-1].cardNum,rest[2][0][i-1].expMonth,rest[2][0][i-1].expYear,rest[2][0][i-1].secCode,rest[2][0][i-1].name,new Adress(rest[2][0][i-1].bline1,rest[2][0][i-1].bline2,rest[2][0][i-1].bcountry,rest[2][0][i-1].bzipCode,rest[2][0][i-1].bcity,rest[2][0][i-1].bstate)),0,oEvents,new Adress(rest[2][0][i-1].sline1,rest[2][0][i-1].sline2,rest[2][0][i-1].scountry,rest[2][0][i-1].szipCode,rest[2][0][i-1].scity,rest[2][0][i-1].sstate),null));
					}

					oEvents= new Array();
				}
				oEvents.push(new BuyEvent(new product(rest[2][0][i].pname,rest[2][0][i].sellerPId,rest[2][0][i].mname,1,1,1,rest[2][0][i].dimensions),rest[2][0][i].price,rest[2][0][i].sellingTime,false,rest[2][0][i].features,rest[2][0][i].description,rest[2][0][i].basketId,rest[2][0][i].username,rest[2][0][i].sellerRating,rest[2][0][i].btitle,rest[2][0][i].pic,rest[2][0][i].item_quantity)); 
				curroId=rest[2][0][i].orderId;
				console.log(rest[2][0][i].orderId+'at'+i);



			}


			// var o=rest[8][0];
			// //get a list of bid orders 
			// var  empty = new Array();

			// for (var i =0;i<o.length;i++)
			// {
			// 	if(rest[8][0][i].wusername!=null)
			// 		OrderList.push(new Order(o[i].endingTime,new CreditCard(o[i].cardId,o[i].cardNum,o[i].expMonth,o[i].expYear,o[i].secCode,o[i].name,new Adress(o[i].bline1,o[i].bline2,o[i].bcountry,o[i].bzipCode,o[i].bcity,o[i].bstate)),0,empty,new Adress(o[i].sline1,o[i].sline2,o[i].scountry,o[i].szipCode,o[i].scity,o[i].sstate),new BidEvent(new product(rest[8][0][i].pname,rest[8][0][i].sellerPId,rest[8][0][i].mname,rest[8][0][i].width,rest[8][0][i].height,rest[8][0][i].depth,rest[8][0][i].dimensions),rest[8][0][i].startingBid,rest[8][0][i].startingTime,rest[8][0][i].endingTime,rest[8][0][i].features,rest[8][0][i].description,rest[8][0][i].minBid,rest[8][0][i].bidEventId,rest[8][0][i].seller, rest[8][0][i].sellerRating,rest[8][0][i].bidTitle,rest[8][0][i].picture,new Bid(rest[8][0][i].wusername,rest[8][0][i].time,rest[8][0][i].wamount))));	
			// 	else
			// 		OrderList.push(new Order(o[i].endingTime,new CreditCard(o[i].cardId,o[i].cardNum,o[i].expMonth,o[i].expYear,o[i].secCode,o[i].name,new Adress(o[i].bline1,o[i].bline2,o[i].bcountry,o[i].bzipCode,o[i].bcity,o[i].bstate)),0,empty,new Adress(o[i].sline1,o[i].sline2,o[i].scountry,o[i].szipCode,o[i].scity,o[i].sstate),new BidEvent(new product(rest[8][0][i].pname,rest[8][0][i].sellerPId,rest[8][0][i].mname,rest[8][0][i].width,rest[8][0][i].height,rest[8][0][i].depth,rest[8][0][i].dimensions),rest[8][0][i].startingBid,rest[8][0][i].startingTime,rest[8][0][i].endingTime,rest[8][0][i].features,rest[8][0][i].description,rest[8][0][i].minBid,rest[8][0][i].bidEventId,rest[8][0][i].seller, rest[8][0][i].sellerRating,rest[8][0][i].bidTitle,rest[8][0][i].picture,null)));		  

			// }

			// console.log(OrderList);



			// //bidding on in BidEvents
			// var BidEvents= new Array();
			// for (var i=0;i<rest[3][0].length;i++)
			// {
			// 	if(rest[3][0][i].wusername!=null)
			// 		BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,new Bid(rest[3][0][i].wusername,rest[3][0][i].time,rest[3][0][i].wamount))); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			// 	else
			// 		BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,null)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!


		 //    }
		    
//		    
		    var o=rest[8][0];
		    //get a list of bid orders 
		    var  empty = new Array();
		    
		    for (var i =0;i<o.length;i++)
		    {
		    	if(rest[8][0][i].wusername!=null)
		    	OrderList.push(new Order(o[i].endingTime,new CreditCard(o[i].cardId,o[i].cardNum,o[i].expMonth,o[i].expYear,o[i].secCode,o[i].name,new Adress(o[i].bline1,o[i].bline2,o[i].bcountry,o[i].bzipCode,o[i].bcity,o[i].bstate)),0,empty,new Adress(o[i].sline1,o[i].sline2,o[i].scountry,o[i].szipCode,o[i].scity,o[i].sstate),new BidEvent(new product(rest[8][0][i].pname,rest[8][0][i].sellerPId,rest[8][0][i].mname,rest[8][0][i].width,rest[8][0][i].height,rest[8][0][i].depth,rest[8][0][i].dimensions),rest[8][0][i].startingBid,rest[8][0][i].startingTime,rest[8][0][i].endingTime,rest[8][0][i].features,rest[8][0][i].description,rest[8][0][i].minBid,rest[8][0][i].bidEventId,rest[8][0][i].seller, rest[8][0][i].sellerRating,rest[8][0][i].bidTitle,rest[8][0][i].picture,new Bid(rest[8][0][i].wusername,rest[8][0][i].time,rest[8][0][i].wamount),rest[8][0][i].finished,rest[8][0][i].accepted)));	
		    	else
			    	OrderList.push(new Order(o[i].endingTime,new CreditCard(o[i].cardId,o[i].cardNum,o[i].expMonth,o[i].expYear,o[i].secCode,o[i].name,new Adress(o[i].bline1,o[i].bline2,o[i].bcountry,o[i].bzipCode,o[i].bcity,o[i].bstate)),0,empty,new Adress(o[i].sline1,o[i].sline2,o[i].scountry,o[i].szipCode,o[i].scity,o[i].sstate),new BidEvent(new product(rest[8][0][i].pname,rest[8][0][i].sellerPId,rest[8][0][i].mname,rest[8][0][i].width,rest[8][0][i].height,rest[8][0][i].depth,rest[8][0][i].dimensions),rest[8][0][i].startingBid,rest[8][0][i].startingTime,rest[8][0][i].endingTime,rest[8][0][i].features,rest[8][0][i].description,rest[8][0][i].minBid,rest[8][0][i].bidEventId,rest[8][0][i].seller, rest[8][0][i].sellerRating,rest[8][0][i].bidTitle,rest[8][0][i].picture,null,rest[8][0][i].finished,rest[8][0][i].accepted)));		  

		    }
		    
		   
		   
		   //bidding on in BidEvents
		   var BidEvents= new Array();
		   for (var i=0;i<rest[3][0].length;i++)
		   {
			   if(rest[3][0][i].wusername!=null)
		    	BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,new Bid(rest[3][0][i].wusername,rest[3][0][i].time,rest[3][0][i].wamount),rest[3][0][i].finished,rest[3][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			   else
			    	BidEvents.push(new BidEvent(new product(rest[3][0][i].pname,rest[3][0][i].sellerPId,rest[3][0][i].mname,rest[3][0][i].width,rest[3][0][i].height,rest[3][0][i].depth,rest[3][0][i].dimensions),rest[3][0][i].startingBid,rest[3][0][i].startingTime,rest[3][0][i].endingTime,rest[3][0][i].features,rest[3][0][i].description,rest[3][0][i].minBid,rest[3][0][i].bidEventId,rest[3][0][i].username, rest[3][0][i].rating,rest[3][0][i].bidTitle,rest[3][0][i].picture,null,rest[3][0][i].finished,rest[3][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

		   }

		   //console.log(BidEvents);
		   
		   //get the credit cards
		   
		   var CreditCards= new Array();
		   for (var i=0;i<rest[4][0].length;i++)
		   {
			   CreditCards.push(new CreditCard(rest[4][0][i].cardId,rest[4][0][i].cardNum,rest[4][0][i].expMonth,rest[4][0][i].expYear,rest[4][0][i].secCode,rest[4][0][i].name,new Adress(rest[4][0][i].line1,rest[4][0][i].line2,rest[4][0][i].country,rest[4][0][i].zipCode,rest[4][0][i].city,rest[4][0][i].state,rest[4][0][i].AddressId)));
		   }
		   //console.log(CreditCards);
		   
		   //get sold by Bid
		   var sBidEvents= new Array();
		   for (var i=0;i<rest[5][0].length;i++)
		   {
			   console.log(rest[5][0]);
			   var arrayBuffer = rest[5][0][i]; 
			   if (arrayBuffer) {
			     var byteArray = new Uint8Array(arrayBuffer);
			   }
			   if(rest[5][0][i].winnerName!=null)
			   sBidEvents.push(new BidEvent(new product(rest[5][0][i].pname,rest[5][0][i].productPId,rest[5][0][i].mname,rest[5][0][i].width,rest[5][0][i].height,rest[5][0][i].depth,rest[5][0][i].dimensions),rest[5][0][i].startingBid,rest[5][0][i].startingTime,rest[5][0][i].endingTime,rest[5][0][i].features,rest[5][0][i].description,rest[5][0][i].minBid,rest[5][0][i].bidEventId,rest[5][0][i].username,rest[5][0][i].rating,rest[5][0][i].bidTitle,rest[5][0][i].picture,new Bid(rest[5][0][i].winnerName,rest[5][0][i].bidTime,rest[5][0][i].amount),rest[5][0][i].finished,rest[5][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
			   else
			   sBidEvents.push(new BidEvent(new product(rest[5][0][i].pname,rest[5][0][i].productPId,rest[5][0][i].mname,rest[5][0][i].width,rest[5][0][i].height,rest[5][0][i].depth,rest[5][0][i].dimensions),rest[5][0][i].startingBid,rest[5][0][i].startingTime,rest[5][0][i].endingTime,rest[5][0][i].features,rest[5][0][i].description,rest[5][0][i].minBid,rest[5][0][i].bidEventId,rest[5][0][i].username,rest[5][0][i].rating,rest[5][0][i].bidTitle,rest[5][0][i].picture,null,rest[5][0][i].finished,rest[5][0][i].accepted)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!

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
		    var idPerBasket={};
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
				    	idPerBasket[rest[7][0][i-1].bname]=rest[7][0][i-1].basketId;
				    	Events= new Array();
		    			}
				    	Events.push(new BuyEvent(new product(rest[7][0][i].pname,rest[7][0][i].sellerPId,rest[7][0][i].mname,1,1,1,rest[7][0][i].dimensions),rest[7][0][i].price,rest[7][0][i].sellingTime,false,rest[7][0][i].features,rest[7][0][i].description,rest[7][0][i].buyEventId,rest[7][0][i].username,rest[7][0][i].rating,rest[7][0][i].btitle,rest[7][0][i].pic,rest[7][0][i].item_quantity)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		    			EventsPerBasket[rest[7][0][i].bname]=Events; 
				    	idPerBasket[rest[7][0][i].bname]=rest[7][0][i].basketId;

		    		}
		    		else
		    		{
		    		EventsPerBasket[rest[7][0][i-1].bname]=Events; //must address multiple name existance?
			    	idPerBasket[rest[7][0][i-1].bname]=rest[7][0][i-1].basketId;

		    		}
		    		
		    		Events= new Array();
		    	}
		    	Events.push(new BuyEvent(new product(rest[7][0][i].pname,rest[7][0][i].sellerPId,rest[7][0][i].mname,1,1,1,rest[7][0][i].dimensions),rest[7][0][i].price,rest[7][0][i].sellingTime,false,rest[7][0][i].features,rest[7][0][i].description,rest[7][0][i].buyEventId,rest[7][0][i].username,rest[7][0][i].rating,rest[7][0][i].btitle,rest[7][0][i].pic,rest[7][0][i].item_quantity)); //must change dimension to char and sql date to corresponding, eliminae reviews from here!!!
		    	currId=rest[7][0][i].basketId;
		    }
		    
		    
		    
		   
//		   var keys = Object.keys(EventsPerBasket);
		   for (var key in EventsPerBasket)
		   {
			   BasketList.push(new Basket(key,EventsPerBasket[key],null,idPerBasket[key]));
		   }
		   var empty = new Array();
		   for(var i=0;i<rest[9][0].length;i++){
			   BasketList.push(new Basket(rest[9][0][i].bname,empty,null,rest[9][0][i].basketId));  //add empty baskets separately
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
					"userOrders":OrderList,
					"userId":result[0].userId
			};
		  
	    //console.log(sBuyEvents[0].pic);
	     
			console.log(billing);
			res.json(response);
		});


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
		
	}
});
var sender = new gcm.Sender('AIzaSyCTFn1fBSl-7jcUgWIDb6SE17qiaoFpr6o');
//At least one required
/**
 * Parameters: message-literal, registrationIds-array, No. of retries, callback-function
 */
//var myVar=setInterval(function(){myTimer()},10000);
//check for completed bidEvents

var myVar2=setInterval(function(){myBidEventTimer()},60000);

function myBidEventTimer()
{
	console.log('Checking for completed Bid Events');
	function getFinishedBidEvents () 
	{
		var defered = Q.defer();

		var query='select bidEventId from Bid_Events where NOW()>= endingTime and finished=false';
		connection.query(query, defered.makeNodeResolver());
		return defered.promise;
	};

	Q.all([getFinishedBidEvents()]).then(function(rest)
			{
		console.log(rest[0][0].length+' events to update');
		var trans= connection.startTransaction();
		for (var i=0;i<rest[0][0].length;i++)
		{
			trans.query('update bid_events set finished=true where bidEventId='+connection.escape(rest[0][0][i].bidEventId));
		}
		trans.commit();
			});
};

function myTimer()
{
	var userquery = 'select device_id from users';
	connection.query(userquery,function(err,resp){
		console.log("Bam");
		for(var i = 0; i<resp.length;i++){
			console.log(resp[i].device_id);
			if(resp[i].device_id==''){
				continue;
			}
			else
			sender.send(message, resp[i].device_id, 4, function (err, result) {console.log(result);});
		}
	});
	
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
				    if(typeof result != undefined || result!=null){
				    	console.log("No email found")
				    	res.json("false");

				    }
				    else{



		console.log(err || JSON.stringify(result));
		var smtpTransport = nodemailer.createTransport("SMTP",
		{
		     service: "Gmail",
		     auth: {
		         user: "basketservices@gmail.com",
		         pass: "tito12@@"
		     }
		});
		var mailOptions = {
				from: "Basket Services <basketservices@gmail.com>", // sender address
				to: req.params.email, // list of receivers
				subject: "Your basket account ", // Subject line
				text: 'Hello,\nYour account details are:\nusername:'+result[0].username+'\npassword:'+result[0].password+'\nHave a basketful day!'//, // plaintext body
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
	}
			});


		});

function createCategory (req, callback)
{
	console.log("Request body of create cate");
	console.log(req.body);
    if(typeof req.body.parent != undefined || req.body.parent!=null){
        getCategoryId(req.body.parent.name, function(err,response){
        	console.log("Creating category with parent");
        	console.log(response);
            var userquery='insert into categories values(NULL,'+connection.escape(req.body.name)+','+response[0].categoryId+');';
            console.log(userquery);
		    connection.query(userquery, function(err, response) {
		        if (err){

		                console.log("Problem in query");

		        }
		        callback(err,response);
		    });
        });
    }
    else{
        var userquery='insert into categories values(NULL,'+connection.escape(req.body.name)+',NULL);'
        console.log(userquery);
	    connection.query(userquery, function(err, response) {
	        if (err){

	                console.log("Problem in query");

	        }
	        callback(err,response);
	    });
    }

    

};
//Delete user
app.post('/Basket.js/AdminCreateCategory/', function(req,res){
    console.log(req.body);
    console.log("Going into create category");
    try{
        connection.beginTransaction(function(err) {
            createCategory(req, function(err,response){
            	if(err){
            		res.json("false");
            	}
                console.log(err);
                console.log(response);
                console.log("In here");
                res.json(true);
            });
        });
    }
    catch(error){
        console.log("Error"+error);
        res.json("false");
    }
});

function getCategoryId (name , callback)
{
    console.log("Geting cat id");
    var userquery='select categoryId from categories where name='+connection.escape(name);
    console.log(userquery);
    connection.query(userquery, function(err, response) {
        if (err){
            console.log("Problem in query");
        }
        console.log(response);
        console.log(err);
        callback(err,response);
        

    });

};
function getCategoryParentId (name , callback)
{
    console.log("Geting parent id")
    var userquery='select parentCategoryId from categories where name='+connection.escape(name);
    console.log(userquery);
    connection.query(userquery, function(err, response) {
        if (err){
            console.log("Problem in query");
        }
        console.log(response);

        if(typeof response == 'undefined'){
            var resp1 = new Array();
            var resp = {
                "parentCategoryId":""
            };
            resp1.push(resp)   ;
            console.log(resp1);
            callback(err,resp1);
        }
        else{
            callback(err,response);
        }

    });

};
//Delete user
function getCategory (id , callback) {
    var userquery='select name from categories where categoryId='+connection.escape(id);
    console.log(userquery);
    connection.query(userquery, function(err, response) {
        if (err){
            console.log("Problem in query");
            console.log(err);
        }
        console.log(response);
        if(typeof response[0] == 'undefined'){
            var resp1 = new Array();
            var resp = {
                "name":""
            };
            resp1.push(resp)   ;
            callback(err,resp1);
        }
        else{
            callback(err,response);
        }
    });
}

app.post('/Basket.js/GetCatParent/', function(req,res){
    console.log(req.body);
    console.log("Getting parent");
    try{
        getCategoryParentId(req.body.name, function(err,response){
            console.log(err);
            console.log(response);
            if(typeof response[0].parentCategoryId == 'undefined'|| response[0].parentCategoryId ==""){
                console.log("In here");
                res.json(response[0].parentCategoryId.toString());
            }
            else{
            	console.log("Out here");
                var id = response[0].parentCategoryId;
                getCategory(response[0].parentCategoryId,function(err,response){
                    res.json(response[0].name);
                });
            }
        });
    }
    catch(error){
        console.log("Error"+error);
    }
});
