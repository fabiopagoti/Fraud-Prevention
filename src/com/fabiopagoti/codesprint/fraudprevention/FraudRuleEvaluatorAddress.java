package com.fabiopagoti.codesprint.fraudprevention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Class FraudRuleEvaluatorAddress has the following criteria to detect fraudulent purchase orders
 * Two orders have the same Address/City/State/Zip and deal id, but different credit card information, regardless of email address
 * @author fabiopagoti
 *
 */
public class FraudRuleEvaluatorAddress implements IFraudRuleEvaluator {

	HashMap<Integer, ArrayList<PurchaseOrder>> purchase_orders_with_same_deal_id; // given an ID, retrieves a ArrayList of all purchase orders with the same ID
	// TODO: if other classes must use this collection, consider transfering this attibute to a interface child of IFraudRuleEvaluator
	
	/**
	 * Constructor
	 */
	public FraudRuleEvaluatorAddress() {
		this.purchase_orders_with_same_deal_id = new HashMap<Integer, ArrayList<PurchaseOrder>>();
	}


	@Override
	/**
	 * Receives a group of purchase orders and detect which of them are fraudulent based on the criteria specified in class description.
	 */
	public Collection<PurchaseOrder> evaluateFraudAmongPurchaseOrders(Collection<PurchaseOrder> _purchase_order_to_be_evaluated) {

		ArrayList<PurchaseOrder> purchase_orders_to_be_evaluated = (ArrayList<PurchaseOrder>) _purchase_order_to_be_evaluated;
		ArrayList<PurchaseOrder> fraudulent_purchase_orders = new ArrayList<PurchaseOrder>(); // return object
		
		
		ArrayList<PurchaseOrder> current_purchase_orders_with_same_deal_id = null; // used to set and get data from HashMap
		
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
					 * Their Street, City, State and Zip Code must be equal AND
					 * Credit Card must be different
					 */
					
					if (	current_purchase_order.getStreet().compareTo(purchase_order_to_be_compared.getStreet()) == 0 &&
							current_purchase_order.getCity().compareTo(purchase_order_to_be_compared.getCity()) == 0 &&
							current_purchase_order.getState().compareTo(purchase_order_to_be_compared.getState()) == 0 &&
							current_purchase_order.getZip_code().compareTo(purchase_order_to_be_compared.getZip_code()) == 0 &&
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

}
