package com.fabiopagoti.codesprint.fraudprevention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Represents a Purchase Order. Also contains a collection which holds all existing purchase orders and specified services like a fraud prevention 
 * @author fabiopagoti
 * TODO: Consider extending a general Order class
 */
public class PurchaseOrder implements Comparable<PurchaseOrder>, Comparator<PurchaseOrder> { // extends Order

	/**
	 * Group of existing purchase orders
	 */
	protected static Collection<PurchaseOrder> purchase_orders;

	/**
	 * Purchase Order ID
	 */
	protected int id;

	/**
	 * Deal ID
	 */
	protected int deal_id;

	/**
	 * Email address
	 */
	protected String email;

	/**
	 * Street Addres
	 */
	protected String street;

	/**
	 * City
	 */
	protected String city;

	/**
	 * State
	 */
	protected String state;

	/**
	 * Zip Code
	 */
	protected String zip_code;

	/**
	 * Credit Card
	 */
	protected String credit_card;


	/**
	 * Constructor of a Purchase Order
	 * @param id
	 * @param deal_id
	 * @param email
	 * @param street
	 * @param city
	 * @param state
	 * @param zip_code
	 * @param credit_card
	 */
	public PurchaseOrder(int id, int deal_id, String email, String street,
			String city, String state, String zip_code, String credit_card) {
		super();
		this.id = id;
		this.deal_id = deal_id;
		this.email = email;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip_code = zip_code;
		this.credit_card = credit_card;
		PurchaseOrderFormatter.formatPurchaseOrder(this);
	}

	/**
	 * Local class PurchaseOrderFraudPrevention is used to call all classes responsible for validating frauds in purchase orders.
	 * TODO: Consider separate this class for refactoring purposes
	 * @author fabiopagoti
	 */
	static class PurchaseOrderFraudPrevention{

		/**
		 * Call all Fraud Prevention Classes
		 * @param _purchase_orders_to_be_evaluated
		 * @return
		 */
		static Collection<PurchaseOrder> evaluateFraudulentPurchaseOrders(Collection<PurchaseOrder> _purchase_orders_to_be_evaluated){

			// A collection for each group of purchase orders fraudulent under a specified criteria
			Collection<PurchaseOrder> purchase_orders_FraudRuleEvaluatorEmail = null;
			Collection<PurchaseOrder> purchase_orders_FraudRuleEvaluatorAddress = null;
			// *** Include your collection here ***

			// Instantiate a new evaluator for each class. If a new rule must be applied, create a new class which implements interface IFraudEvaluator and intantiate one object here
			purchase_orders_FraudRuleEvaluatorEmail = new FraudRuleEvaluatorEmail().evaluateFraudAmongPurchaseOrders(_purchase_orders_to_be_evaluated);
			purchase_orders_FraudRuleEvaluatorAddress = new FraudRuleEvaluatorAddress().evaluateFraudAmongPurchaseOrders(_purchase_orders_to_be_evaluated);
			// *** Include your method call here ***

			// As all collections might have duplicate purchase orders among them, a HashSet is used as it doesn't allow duplicates
			HashSet<PurchaseOrder> fraudulent_purchase_orders_no_duplicate = new HashSet<PurchaseOrder>();
			fraudulent_purchase_orders_no_duplicate.addAll(purchase_orders_FraudRuleEvaluatorEmail);
			fraudulent_purchase_orders_no_duplicate.addAll(purchase_orders_FraudRuleEvaluatorAddress);

			// Assigns the HashSet to a new ArrayList so it can be sorted
			ArrayList<PurchaseOrder> fraudulent_purchase_orders_no_duplicate_list = new ArrayList<PurchaseOrder>(fraudulent_purchase_orders_no_duplicate);
			Collections.sort((ArrayList<PurchaseOrder>) fraudulent_purchase_orders_no_duplicate_list);

			return fraudulent_purchase_orders_no_duplicate_list;

		}
	}


	/**
	 * Add a purchase order into collection if possible
	 * @param _new_purchase_order
	 * @return
	 */
	public static boolean addPurchaseOrder(PurchaseOrder _new_purchase_order){

		if (PurchaseOrder.purchase_orders == null){ 
			PurchaseOrder.initPurchaseOrders();
		}

		if( purchase_orders.add(_new_purchase_order))
			return true;
		else return false;

	}

	/**
	 * Initialize purchase order collection without specifying initial capacity
	 * Consider using the method with the initial capacity whenever possible
	 */
	protected static void initPurchaseOrders() {
		PurchaseOrder.purchase_orders = new ArrayList<PurchaseOrder>(0);
	}

	/**
	 * Initialize purchase order collection using a initial capacity
	 * @param _number_of_new_purchase_orders
	 */
	public static void initPurchaseOrders(int _number_of_new_purchase_orders){
		PurchaseOrder.purchase_orders = new ArrayList<PurchaseOrder>(_number_of_new_purchase_orders);
	}

	/**
	 * Print all purchase orders from the collection
	 */
	public static void printOrders(){
		for (PurchaseOrder current_purchase_order : PurchaseOrder.purchase_orders) {
			System.out.println(current_purchase_order.toString());
		}
	}

	@Override
	public String toString() {
		return ("Order ID:\t" + this.id +
				"\t| Deal ID:\t" + this.deal_id +
				"\t| Email:\t" + this.email +
				"\t| Street:\t" + this.street +
				"\t| City:\t" + this.city +
				"\t| State:\t" + this.state +
				"\t| Zip Code:\t" + this.zip_code +
				"\t| Credit Card:\t" + this.credit_card);
	}

	/**
	 * Print all Fraudulent Purchase Orders' ID separated by commas
	 */
	public static void printFraulentPurchaseOrders() {
		String output = "";
		Collection<PurchaseOrder> fraudulent_purchase_orders = (ArrayList<PurchaseOrder>) PurchaseOrderFraudPrevention.evaluateFraudulentPurchaseOrders(purchase_orders);

		for (PurchaseOrder current_purchase_order : fraudulent_purchase_orders) {
			output = output.concat(String.valueOf(current_purchase_order.getId()).concat(","));			
		}

		if (output.length() > 2) {
			output = output.substring(0, (output.length() - 1));			
		}
		System.out.println(output);
	}

	@Override
	/**
	 * Used for sorting purposes
	 */
	public int compareTo(PurchaseOrder _purchase_order_to_be_comparable) {
		if (this.getId() < _purchase_order_to_be_comparable.getId()) {
			return -1;
		} else if (this.getId() > _purchase_order_to_be_comparable.getId()) {
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	/**
	 * Used for sorting purposes
	 */
	public int compare(PurchaseOrder o1, PurchaseOrder o2) {
		return Integer.valueOf(o1.getId()).compareTo(o2.getId());
	}


	/* **************************************************
	 * Getters and Setter
	 * **************************************************
	 */

	/**
	 * @return the purchase_orders
	 */
	public static Collection<PurchaseOrder> getPurchase_orders() {
		return purchase_orders;
	}

	/**
	 * @param purchase_orders the purchase_orders to set
	 */
	public static void setPurchase_orders(
			Collection<PurchaseOrder> purchase_orders) {
		PurchaseOrder.purchase_orders = purchase_orders;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the deal_id
	 */
	public int getDeal_id() {
		return deal_id;
	}

	/**
	 * @param deal_id the deal_id to set
	 */
	public void setDeal_id(int deal_id) {
		this.deal_id = deal_id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zip_code
	 */
	public String getZip_code() {
		return zip_code;
	}

	/**
	 * @param zip_code the zip_code to set
	 */
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	
	/**
	 * @return the credit_card
	 */
	public String getCredit_card() {
		return credit_card;
	}

	/**
	 * @param credit_card the credit_card to set
	 */
	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

}
