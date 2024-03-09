// Amadeus Färdig

package SimState;


public class CustomerFactory {


	public Customer createCustomer() {
		return new Customer(); // Customer ID is now managed internally by the Customer class.
	}
}
