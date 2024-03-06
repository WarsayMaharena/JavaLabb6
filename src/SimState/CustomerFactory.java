package SimState;


public class CustomerFactory {
		
	private int customersCreated = 0;

	public Customer createCustomer()
	   {
	      Customer customer = new Customer(customersCreated);
	      customersCreated += 1;
	      return customer;
	   }
}
