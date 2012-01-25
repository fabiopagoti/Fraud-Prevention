package com.fabiopagoti.codesprint.fraudprevention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Class FraudRuleEvaluatorAddress has the following criteria to detect fraudulent purchase orders
 * Two orders have the same email address and deal id, but different credit card information, regardless of street address.
 * @author fabiopagoti
 *
 */
public class FraudRuleEvaluatorEmail implements IFraudRuleEvaluator {

	HashMap<Integer, ArrayList<PurchaseOrder>> purchase_orders_with_same_deal_id;  // given an ID, retrieves a ArrayList of all purchase orders with the same ID
	// TODO: if other classes must use this collection, consider transfering this attibute to a interface child of IFraudRuleEvaluator 

	/**
	 * Constructor
	 */
	public FraudRuleEvaluatorEmail(){
		this.purchase_orders_with_same_deal_id = new HashMap<Integer, ArrayList<PurchaseOrder>>();
	}

	@Override
	/**
	 * Receives a group of purchase orders and detect which of them are fraudulent based on the criteria specified in class description.
	 */
	public Collection<PurchaseOrder> evaluateFraudAmongPurchaseOrders(Collection<PurchaseOrder> _purchase_order_to_be_evaluated) {

		ArrayList<PurchaseOrder> purchase_orders_to_be_evaluated = (ArrayList<PurchaseOrder>) _purchase_order_to_be_evaluated;
		ArrayList<PurchaseOrder> fraudulent_purchase_orders = new ArrayList<PurchaseOrder>(); // return object

		ArrayList<PurchaseOrder> current_purchase_orders_with_same_deal_id = null;  // used to set and get data from HashMap

		for (PurchaseOrder current_purchase_order : purchase_orders_to_be_evaluated) {

			// First, get all purchase orders which has the same ID of current_purchase_order
			current_purchase_orders_with_same_deal_id = purchase_orders_with_same_deal_id.get(current_purchase_order.getDeal_id());

			// If there's none, create an empty list
			if (current_purchase_orders_with_same_deal_id == null) {
				current_purchase_orders_with_same_deal_id = new ArrayList<PurchaseOrder>();
			}else{

				// check if there is fraud between current_purchase_order and the list
				for (PurchaseOrder purchase_order_to_be_compared : current_purchase_orders_with_same_deal_id) {


					/*
					 * In order to 2 purchase orders be considered fraudulent,
					 * Their emails must be equal AND
					 * Credit Card must be different
					 */
					if (compareEmails(current_purchase_order.getEmail(),purchase_order_to_be_compared.getEmail()) &&
							current_purchase_order.getCredit_card().compareTo(purchase_order_to_be_compared.getCredit_card()) != 0) {

						// Add the fraudulent purchase order(s) to fraudulent list
						if (!fraudulent_purchase_orders.contains(purchase_order_to_be_compared)) { // NOT contains
							fraudulent_purchase_orders.add(purchase_order_to_be_compared);
						}
						fraudulent_purchase_orders.add(current_purchase_order);

					}
				}
			}

			// Update list of orders with the same id and add current_purchase_order into the HashMap of orders
			current_purchase_orders_with_same_deal_id.add(current_purchase_order);
			purchase_orders_with_same_deal_id.put(current_purchase_order.getDeal_id(), current_purchase_orders_with_same_deal_id);

		}

		return fraudulent_purchase_orders;
	}

	/**
	 * Compare emails considering the following rule
	 * The username portion of an email address can have ignored characters
	 * A `.` in an email is flat out ignored, so `bugs1@bunny.com`, and `bugs.1@bunny.com` are the same email address.
	 * A `+` in an email means the plus and everything after is ignored, so `bugs@bunny.com` and `bugs+10@bunny.com` are the same email address.
	 * @param _email_1
	 * @param _email_2
	 * @return
	 */
	protected boolean compareEmails(String _email_1, String _email_2){
		
		// Both emails are all ignored, so they are equal
		if (_email_1.charAt(0) == '+' && _email_2.charAt(0) == '+' ) {
			return true;
		}

		String valid_email_1 = retrieveValidEmail(_email_1);
		String valid_email_2 = retrieveValidEmail(_email_2);

		// evaluate the shortest valid email portion and check if it's contained in the other, if yes, both emails are equal
		if (valid_email_1.length() > valid_email_2.length()) {
			if (valid_email_1.startsWith(valid_email_2)) {
				return true;
			}	else {
				return false;
			}

		} else if (valid_email_1.length() < valid_email_2.length()) {
			if (valid_email_2.startsWith(valid_email_1)) {
				return true;
			}	else {
				return false;
			}
		}
		else { // emails lenghts are the same
			if (valid_email_1.compareTo(valid_email_2) == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Retrieves a substring of the email from its first position until a (if any) occurence of '+'
	 * @param _email
	 * @return valid username portion or the hole email address if there's no '+'
	 */
	protected String retrieveValidEmail(String _email){

		int email_plus_index;
		email_plus_index = _email.indexOf("+");
		if (email_plus_index >= 0) {
			return _email.substring(0,email_plus_index);
		}
		return _email;
	}

}
