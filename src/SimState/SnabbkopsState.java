//Amadeus UPPDATERA CHANGEREGISTERAVAILABILITY I GITHUB OCH GETCUSTOMERS

package SimState;

import java.util.ArrayList;

public class SnabbkopsState {
	private boolean isOpen = false;
	private final int maxCustomers;
	private final int registers;
	private final double closingTime;

	private final ArrayList<Customer> customers = new ArrayList<>();

	private final FIFO registerLine = new FIFO();
	private final CustomerFactory customerFactory = new CustomerFactory();

	private int totalMoney = 0;
	private int freeRegisters;
	private double registerFreeTime = 0.0;
	private double customerQueueTime = 0.0;
	private int totalCustomersInQueue = 0;
	private double lastPaymentTime = 0.0;

	public SnabbkopsState(int maxCustomers, int registers, double closingTime) {
		this.maxCustomers = maxCustomers;
		this.registers = registers;
		this.closingTime = closingTime;
		this.freeRegisters = registers;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getMaxCustomers() {
		return maxCustomers;
	}

	public int getRegisters() {
		return registers;
	}

	public double getClosingTime() {
		return closingTime;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public int getFreeRegisters() {
		return freeRegisters;
	}

	public double getRegisterFreeTime() {
		return registerFreeTime;
	}

	public double getCustomerQueueTime() {
		return customerQueueTime;
	}

	public FIFO getRegisterLine() {
		return registerLine;
	}

	public void changeRegisterAvailability(boolean occupy) {
		if (occupy && freeRegisters > 0) {
			freeRegisters--;
		} else if (!occupy) {
			freeRegisters++;
		}
	}

	public Customer createNewCustomer() {
		Customer customer = customerFactory.createCustomer();
		return customer;
	}

	public void addCustomer(Customer customer) {
		if (!isOpen) {
			customer.setState(Customer.CustomerState.lateCustomer);
		} else if (getCustomersInStore() < maxCustomers) {
			customer.setState(Customer.CustomerState.inStore);
		} else {
			customer.setState(Customer.CustomerState.turnedAway);
		}
		customers.add(customer);
	}

	public int getCustomersInStore() {
		return (int) customers.stream().filter(c -> c.getState() == Customer.CustomerState.inStore).count();
	}

	public int getCustomersTurnedAway() {
		return (int) customers.stream().filter(c -> c.getState() == Customer.CustomerState.turnedAway).count();
	}

	public int getTotalCustomers() {
		return (int) customers.stream().filter(c -> c.getState() != Customer.CustomerState.lateCustomer).count();
	}

	public void addMoney() {
		totalMoney += 1;
	}

	public void removeCustomer(Customer customer) {
		customer.setState(Customer.CustomerState.notInStore);
	}

	public void setRegisterFreeTime(double time) {
		registerFreeTime = time;
	}

	void setCustomerQueueTime(double time) {
		customerQueueTime = time;
	}

	public int getTotalCustomersInQueue() {
		return totalCustomersInQueue;
	}

	public void addTotalCustomersInQueue() {
		totalCustomersInQueue += 1;
	}

	public double getLastPaymentTime() {
		return lastPaymentTime;
	}

	public void setLastPaymentTime(double lastPaymentTime) {
		this.lastPaymentTime = lastPaymentTime;
	}

}
