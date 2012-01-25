package com.fabiopagoti.codesprint.fraudprevention;

/**
 * PurchaseOrderFormatter provides services to format Purchase Orders attributes so they can be compared easily.
 * As soon a Purchase Order is created, all it's attributes are formated with the help of this class.
 * @author fabiopagoti
 */
public class PurchaseOrderFormatter {

	/**
	 * Formats all atrributes of a purchase order
	 * @param _purchase_order
	 * @return
	 */
	public static PurchaseOrder formatPurchaseOrder(PurchaseOrder _purchase_order){
		
		// Format Email
		_purchase_order.setEmail(formatEmail(_purchase_order.getEmail()));
		
		// Format Street Address
		_purchase_order.setStreet(formatStreet(_purchase_order.getStreet()));
		
		// Format City
		_purchase_order.setCity(formatCity(_purchase_order.getCity()));
		
		// Format State
		_purchase_order.setState(formatState(_purchase_order.getState()));
		
		return _purchase_order;
	}
	
	/**
	 * Format a Purchase Order Email
	 * @param _email
	 * @return
	 */
	protected static String formatEmail(String _email){
		_email = _email.toLowerCase();
		String username = _email.substring(0,_email.indexOf("@"));
		String domain = _email.substring(_email.indexOf("@"));
		_email = username.replaceAll("[.]", "").concat(domain);
		return _email;
	}
	
	/**
	 * Format a Purchase Order Street Address
	 * @param _street
	 * @return
	 */
	protected static String formatStreet(String _street){
		_street = _street.toLowerCase();
		_street = _street.replaceAll("street", "st.");
		_street = _street.replaceAll("road", "rd.");
		return _street;
	}
	
	/**
	 * Format a Purchase Order City
	 * @param _city
	 * @return
	 */
	protected static String formatCity(String _city){
		_city = _city.toLowerCase();
		return _city;
	}
	
	
	/**
	 * Format a Purchase Order State
	 * @param _state
	 * @return
	 */
	protected static String formatState(String _state){
		_state = _state.toLowerCase();
		_state = _state.replaceAll("new york", "ny");
		_state = _state.replaceAll("illinois", "il");
		_state = _state.replaceAll("california", "ca");
		return _state;
	}
}
