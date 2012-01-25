package com.fabiopagoti.codesprint.fraudprevention;

import java.util.Collection;

/**
 * IFraudEvaluator represents evaluator responsible for detecting fraudulent purchase orders among a specific group. 
 * @author fabiopagoti
 *
 */
public interface IFraudRuleEvaluator {

	public Collection<PurchaseOrder> evaluateFraudAmongPurchaseOrders(Collection<PurchaseOrder> _order_to_be_evaluates);
	
	
}
