package SimState;

import java.util.ArrayList;

/**
 * Representerar tillståndet för en snabbköpssimulation. Denna klass hanterar
 * snabbköpets logiska tillstånd, inklusive kunder, kassor och öppettider. Den
 * tillhandahåller metoder för att interagera med snabbköpet, som att öppna
 * eller stänga det, hantera kundflödet och uppdatera tillstånd baserat på
 * händelser i simuleringen.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class SnabbkopsState {
	private boolean isStoreOpen = false;
	private final int maxStoreCustomers;
	private final int numberOfRegisters;
	private final double storeClosingTime;

	private final ArrayList<Customer> customers = new ArrayList<>();

	private final FIFO FIFOregisterLine = new FIFO();
	private final CustomerFactory customerFactory = new CustomerFactory();

	private int totalMoney = 0;
	private int freeRegisters;
	private double registerFreeTime = 0.0;
	private double customerQueueTime = 0.0;
	private int totalCustomersInQueue = 0;
	private double lastPayTime = 0.0;

	/**
	 * Skapar en ny instans av SnabbkopsState. Initierar snabbköpet med angivet
	 * antal maximala kunder, kassor och stängningstid.
	 * 
	 * @param maxCustomers Maximalt antal kunder som får vara i snabbköpet
	 *                     samtidigt.
	 * @param registers    Antal tillgängliga kassor.
	 * @param closingTime  Tiden då snabbköpet stänger.
	 */
	public SnabbkopsState(int maxCustomers, int registers, double closingTime) {
		this.maxStoreCustomers = maxCustomers;
		this.numberOfRegisters = registers;
		this.storeClosingTime = closingTime;
		this.freeRegisters = registers;
	}

	/**
	 * Kontrollerar om snabbköpet är öppet.
	 * 
	 * @return true om snabbköpet är öppet, annars false.
	 */
	public boolean isStoreOpen() {
		
		return isStoreOpen;
		
	}

	/**
	 * Ställer in snabbköpets öppettillstånd.
	 * 
	 * @param isOpen true för att öppna snabbköpet, false för att stänga det.
	 */
	public void setStoreOpen(boolean isOpen) {
		this.isStoreOpen = isOpen;
	}

	/**
	 * Hämtar det maximala antalet kunder som tillåts i snabbköpet samtidigt.
	 * 
	 * @return Maximalt antal kunder.
	 */
	public int getMaxCustomers() {
		return maxStoreCustomers;
	}

	/**
	 * Hämtar antalet kassor i snabbköpet.
	 * 
	 * @return Antalet kassor.
	 */
	public int getRegisters() {
		return numberOfRegisters;
	}

	/**
	 * Hämtar tiden då snabbköpet stänger.
	 * 
	 * @return Stängningstiden.
	 */
	public double getClosingTime() {
		return storeClosingTime;
	}

	/**
	 * Hämtar den totala mängden pengar som snabbköpet har tjänat.
	 * 
	 * @return Totala mängden pengar.
	 */
	public int getTotalMoney() {
		return totalMoney;
	}

	/**
	 * Hämtar antalet lediga kassor.
	 * 
	 * @return Antalet lediga kassor.
	 */
	public int getFreeRegisters() {
		return freeRegisters;
	}

	/**
	 * Hämtar den totala tiden kassorna har varit lediga.
	 * 
	 * @return Tiden kassorna har varit lediga.
	 */
	public double getRegisterFreeTime() {
		return registerFreeTime;
	}

	/**
	 * Hämtar den totala tiden kunder har tillbringat i kö.
	 * 
	 * @return Tiden kunder har tillbringat i kö.
	 */
	public double getCustomerQueueTime() {
		return customerQueueTime;
	}

	/**
	 * Hämtar kölinjen till kassorna.
	 * 
	 * @return Kölinjen som ett FIFO-objekt.
	 */
	public FIFO getRegisterLine() {
		return FIFOregisterLine;
	}

	/**
	 * Ändrar tillgängligheten för kassorna baserat på om en kassa ska upptas eller
	 * frigöras.
	 * 
	 * @param occupy Sant för att uppta en kassa, falskt för att frigöra en.
	 */
	public void changeRegisterAvailability(boolean occupy) {
		if (occupy && freeRegisters > 0) {
			freeRegisters--;
		} else if (!occupy) {
			freeRegisters++;
		}
	}

	/**
	 * Skapar en ny kund via kundfabriken.
	 * 
	 * @return Den nyskapade kunden.
	 */
	public Customer createNewCustomer() {
		return customerFactory.createCustomer();
	}

	/**
	 * Lägger till en kund i snabbköpet och ställer in kundens tillstånd baserat på
	 * snabbköpets status.
	 * 
	 * @param customer Kunden som ska läggas till.
	 */
	public void addCustomer(Customer customer) {
		if (!isStoreOpen) {
			customer.setState(Customer.CustomerState.lateCustomer);
		} else if (getCustomersInStore() < maxStoreCustomers) {
			customer.setState(Customer.CustomerState.inStore);
		} else {
			customer.setState(Customer.CustomerState.turnedAway);
		}
		customers.add(customer);
	}

	/**
	 * Räknar antalet kunder i snabbköpet.
	 * 
	 * @return Antalet kunder som är inne i snabbköpet.
	 */
	public int getCustomersInStore() {
		return (int) customers.stream().filter(c -> c.getState() == Customer.CustomerState.inStore).count();
	}

	/**
	 * Räknar antalet kunder som har nekats tillträde till snabbköpet.
	 * 
	 * @return Antalet kunder som har nekats tillträde.
	 */
	public int getCustomersTurnedAway() {
		return (int) customers.stream().filter(c -> c.getState() == Customer.CustomerState.turnedAway).count();
	}

	/**
	 * Räknar det totala antalet kunder som har besökt snabbköpet, exklusive de som
	 * kommit efter stängning.
	 * 
	 * @return Det totala antalet besökande kunder.
	 */
	public int getTotalCustomers() {
		return (int) customers.stream().filter(c -> c.getState() != Customer.CustomerState.lateCustomer).count();
	}

	/**
	 * Ökar den totala mängden pengar snabbköpet har tjänat med en enhet.
	 */
	public void addMoney() {
		totalMoney += 1;
	}

	/**
	 * Tar bort en kund från snabbköpet och uppdaterar kundens tillstånd till ej
	 * längre i butiken.
	 * 
	 * @param customer Kunden som ska tas bort.
	 */
	public void removeCustomer(Customer customer) {
		customer.setState(Customer.CustomerState.notInStore);
	}

	/**
	 * Uppdaterar den totala tiden kassorna har varit lediga.
	 * 
	 * @param time Den nya totala lediga tiden för kassorna.
	 */
	public void setRegisterFreeTime(double time) {
		registerFreeTime = time;
	}

	/**
	 * Uppdaterar den totala tiden kunder har tillbringat i kö.
	 * 
	 * @param time Den nya totala kötiden för kunder.
	 */
	void setCustomerQueueTime(double time) {
		customerQueueTime = time;
	}

	/**
	 * Hämtar det totala antalet kunder som har stått i kö.
	 * 
	 * @return Totala antalet kunder som har köat.
	 */
	public int getTotalCustomersInQueue() {
		return totalCustomersInQueue;
	}

	/**
	 * Ökar antalet kunder som totalt har köat med ett.
	 */
	public void addTotalCustomersInQueue() {
		totalCustomersInQueue++;
	}

	/**
	 * Hämtar tiden för den senaste betalningen.
	 * 
	 * @return Tiden för den senaste betalningen.
	 */
	public double getLastPaymentTime() {
		return lastPayTime;
	}

	/**
	 * Sätter tiden för den senaste betalningen.
	 * 
	 * @param lastPaymentTime Den nya tiden för den senaste betalningen.
	 */
	public void setLastPaymentTime(double lastPaymentTime) {
		this.lastPayTime = lastPaymentTime;
	}

}
