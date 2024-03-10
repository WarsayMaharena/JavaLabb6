package SimState;

/**
 * Representerar en kund inom simuleringen av en butik. Denna klass hanterar
 * kundens unika identifierare och tillstånd inom butiken. Kundens tillstånd
 * representerar olika faser i kundens interaktion med butiken, såsom närvarande
 * i butiken, inte närvarande, avvisad, eller ankommit efter stängning.
 *
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class Customer {
	private static int nextId = -1;
	private int customerID;
	private CustomerState state;

	/**
	 * Definierar olika tillstånd som en kund kan befinna sig i under simuleringen.
	 */
	public enum CustomerState {
		inStore, // Kunden befinner sig i butiken.
		notInStore, // Kunden är inte i butiken.
		turnedAway, // Kunden avvisades på grund av platsbrist.
		lateCustomer // Kunden kom till butiken efter stängning.
	}

	/**
	 * Skapar en ny kund med ett unikt ID och initierar kunden till att inte vara i
	 * butiken.
	 */
	public Customer() {
		this.customerID = ++nextId;
		this.state = CustomerState.notInStore;
	}

	/**
	 * Hämtar kundens unika identifierare.
	 * 
	 * @return Kundens ID.
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * Hämtar kundens aktuella tillstånd i simuleringen.
	 * 
	 * @return Kundens tillstånd.
	 */
	public CustomerState getState() {
		return state;
	}

	/**
	 * Ändrar kundens tillstånd.
	 * 
	 * @param state Det nya tillståndet för kunden.
	 */
	public void setState(CustomerState state) {
		this.state = state;
	}

	/**
	 * Returnerar en strängrepresentation av kunden, baserat på dess ID.
	 * 
	 * @return En sträng som representerar kunden genom dess ID.
	 */
	@Override
	public String toString() {
		return String.valueOf(customerID);
	}
}
