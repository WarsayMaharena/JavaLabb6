package Event;

import SimState.*;

/**
 * Representerar en plockhändelse i simuleringen av snabbköp. Denna händelse
 * inträffar när en kund väljer sina varor och bestämmer sig för att gå till
 * kassan för att betala.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */
public class Pick extends Event {
	private Customer customer;
	private double time;
	private Pay payEvent;

	/**
	 * Skapar en plockhändelse.
	 * 
	 * @param state      Den aktuella tillståndet av simuleringen.
	 * @param eventQueue Händelsekön där framtida händelser placeras.
	 * @param customer   Kunden som genomför plockningen.
	 * @param time       Tiden då händelsen inträffar.
	 */
	public Pick(SimState state, EventQueue eventQueue, Customer customer, double time) {
		super(state, eventQueue);
		this.customer = customer;
		this.time = time;
	}

	/**
	 * Utför plockhändelsen. Om det finns lediga kassor skapas en
	 * betalningshändelse, annars läggs kunden till i kölinjen.
	 */
	public void doMe() {
		state.update(this);
		if (state.getStore().getFreeRegisters() > 0) {
			double payTime = this.time + state.getPayTime().next();
			payEvent = new Pay(this.state, this.eventQueue, payTime, customer);
			eventQueue.addEvent(payEvent);
			state.getStore().changeRegisterAvailability(true);
		} else {
			state.getStore().getRegisterLine().add(customer);
			state.getStore().addTotalCustomersInQueue();
		}
	}

	/**
	 * Hämtar tiden för händelsen.
	 * 
	 * @return Tiden då händelsen inträffar.
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Hämtar händelsens namn.
	 * 
	 * @return En sträng som representerar händelsens namn.
	 */
	public String getName() {
		return "Plock";
	}

	/**
	 * Hämtar kunden associerad med händelsen.
	 * 
	 * @return Kunden som utför plockningen.
	 */
	public Customer getCustomer() {
		return customer;
	}
}