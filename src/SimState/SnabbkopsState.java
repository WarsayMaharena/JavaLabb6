package SimState;

import java.util.ArrayList;


public class SnabbkopsState {
	   private boolean isOpen;
	   private int maxCustomers;
	   private int registers;
	   private double closingTime;
	   
	   // customers holds all customers, also the ones that aren't in the store.
	   private ArrayList<Customer> customers;
	   
	   // customerFactory can create new customers.
	   private CustomerFactory customerFactory;
	   
	   private int openRegisters;
	   
	   private double registerFreeTime;
	   private double customerQueueTime;
	   private int totalCustomersInQueue;
	   private double lastPaymentTime;
	   
	   public SnabbkopsState() {
		   
	   }
	   
	   public Customer createNewCustomer()
	   {
	      Customer customer = customerFactory.createCustomer();
	      return customer;
	   }
	
	

}
