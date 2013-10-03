package com.basket.general;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class User 
{

	private ArrayList<Adress> billingAdress, shippingAdress;
	private ArrayList<ProductBasket> baskets;
	private ArrayList<BidEvent> currentlyBiddingOn;
	private ArrayList<BidEvent> currentlySellingOnBid;
	private ArrayList<BuyEvent> currentlySellingOnBuy;
	private ArrayList<CreditCard> creditCards;

	private String email,username,password, fName, lName;
	private int age;
	
	public User(){}
	
	public User(String n,String e, String p){
		username=n;
		email=e;
		password=p;
		billingAdress= new ArrayList<Adress>();
		shippingAdress= new ArrayList<Adress>();
		baskets=new ArrayList<ProductBasket>();

		currentlyBiddingOn= new ArrayList<BidEvent> ();
		currentlySellingOnBid= new ArrayList<BidEvent> ();
		currentlySellingOnBuy= new ArrayList<BuyEvent>();
		creditCards=new ArrayList<CreditCard>();
	}
	
	public User(String n,String e, String p, String fn, String ln, int a){
		username=n;
		email=e;
		password=p;
		billingAdress= new ArrayList<Adress>();
		shippingAdress= new ArrayList<Adress>();
		baskets=new ArrayList<ProductBasket>();

		currentlyBiddingOn= new ArrayList<BidEvent> ();
		currentlySellingOnBid= new ArrayList<BidEvent> ();
		currentlySellingOnBuy= new ArrayList<BuyEvent>();
		creditCards=new ArrayList<CreditCard>();
	}
	public ArrayList<Adress> getBillingAdress() {
		return billingAdress;
	}

	public void setBillingAdress(ArrayList<Adress> billingAdress) {
		this.billingAdress = billingAdress;
	}
	
	public void addBillingAdress(Adress newBillingAdress){
		this.billingAdress.add(newBillingAdress);
	}

	public ArrayList<Adress> getShippingAdress() {
		return shippingAdress;
	}

	public void setShippingAdress(ArrayList<Adress> shippingAdress) {
		this.shippingAdress = shippingAdress;
	}

	public void addShippingAdress(Adress newShippingAdress){
		this.shippingAdress.add(newShippingAdress);
	}
	
	public ArrayList<ProductBasket> getBaskets() {
		return baskets;
	}

	public void setBaskets(ArrayList<ProductBasket> baskets) {
		this.baskets = baskets;
	}

	public ArrayList<BidEvent> getCurrentlyBiddingOn() {
		return currentlyBiddingOn;
	}

	public void setCurrentlyBiddingOn(ArrayList<BidEvent> currentlyBiddingOn) {
		this.currentlyBiddingOn = currentlyBiddingOn;
	}

	public ArrayList<BidEvent> getCurrentlySellingOnBid() {
		return currentlySellingOnBid;
	}

	public void setCurrentlySellingOnBid(ArrayList<BidEvent> currentlySellingOnBid) {
		this.currentlySellingOnBid = currentlySellingOnBid;
	}

	public ArrayList<BuyEvent> getCurrentlySellingOnBuy() {
		return currentlySellingOnBuy;
	}

	public void setCurrentlySellingOnBuy(ArrayList<BuyEvent> currentlySellingOnBuy) {
		this.currentlySellingOnBuy = currentlySellingOnBuy;
	}

	public ArrayList<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(ArrayList<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
	
	public void addCreditCard(CreditCard newCC){
		this.creditCards.add(newCC);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", username=" + username
				+ ", password=" + password + ", billingAdress=" + billingAdress
				+ ", shippingAdress=" + shippingAdress + ", baskets=" + baskets
				+ ", currentlyBiddingOn=" + currentlyBiddingOn
				+ ", currentlySellingOnBid=" + currentlySellingOnBid
				+ ", currentlySellingOnBuy=" + currentlySellingOnBuy
				+ ", creditCards=" + creditCards + "]";
	}	
}
