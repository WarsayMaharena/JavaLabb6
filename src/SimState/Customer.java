// Amadeus Färdig

package SimState;




public class Customer {
	private static int nextId = -1;
    private int customerID;
    private CustomerState state;


	public Customer() {
		this.customerID = ++nextId;
		this.state = CustomerState.notInStore;
	}

	public int getCustomerID() {
		return customerID;
	}

	public CustomerState getState() {
		return state;
	}

	public void setState(CustomerState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(customerID);
		return sb.toString();
	}

	public enum CustomerState {
		inStore, notInStore, turnedAway, lateCustomer
	}

}
