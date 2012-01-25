package com.fabiopagoti.codesprint.fraudprevention;
import java.util.Scanner;

/**
 * Solution class is the Main Class for challenge "Fraud Prevention" from InterviewStreet/Codesprint.
 * It reads the input data and uses services from class "DirrectConnections" to print the answer
 * @author fabiopagoti
 * @version 1.0
 * @see http://codesprint.interviewstreet.com/recruit/challenges/solve/view/4eff8a6c7ac39/4f000909cf26d} 
 */
public class Solution {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readInput();
		solveChallenge();
	}


	/**
	 * Execute all test cases already loaded and print their results
	 */
	private static void solveChallenge() {
		PurchaseOrder.printFraulentPurchaseOrders();
	}

	/**
	 * Reads the input file
	 */
	private static void readInput() {

		Scanner sc = new Scanner(System.in);

		String record = null;
		String record_attributes[] = null;

		// Purchase Orders attibutes
		int number_of_records;
		int order_id;
		int deal_id;
		String email;
		String street;
		String city;
		String state;
		String zip_code;
		String credit_card;

		while(sc.hasNextLine()) {

			// Get N -> Number Of Records
			record = sc.nextLine().trim();			
			if (record.compareTo("") == 0 || record == null) {
				sc.close();
				return;
			}

			number_of_records = Integer.parseInt(record);
			PurchaseOrder.initPurchaseOrders(number_of_records);

			for (int current_record = 0; current_record < number_of_records; current_record++) {

				record = sc.nextLine();

				if (record.compareTo("") == 0 || record == null) {
					sc.close();
					return;
				}

				record_attributes = record.split(",");

				// Order ID
				order_id = Integer.parseInt(record_attributes[0]);

				// Deal ID
				deal_id = Integer.parseInt(record_attributes[1]);

				// Email
				email = record_attributes[2].trim();

				// Street
				street = record_attributes[3].trim();

				// City
				city = record_attributes[4].trim();

				// State 
				state = record_attributes[5].trim();

				// Zip Code
				zip_code = record_attributes[6].trim();

				// Credit Card
				credit_card = record_attributes[7].trim();

				// Create Purchase Order
				PurchaseOrder.addPurchaseOrder(new PurchaseOrder(order_id, deal_id, email, street, city, state, zip_code, credit_card));
			}
			
			sc.close();
			return;

		}

	}

}
