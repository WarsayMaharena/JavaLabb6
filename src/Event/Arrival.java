package Event;

import SimState.*;

/**
 * Representerar en ankomstshändelse.
 * 
 * @author Amadeus Olofsson, Warsay Maharena, Hjalmar Norén
 */

public class Arrival extends Event {
	Pick pickEvent;
	double time;
	Customer customer;

	public Arrival(SimState state, EventQueue eventQueue, double time) {
		super(state, eventQueue);
		this.time = time;
		customer = state.getStore().createNewCustomer();
	}

	public void doMe() {

		state.update(this);

		this.state.getStore().addCustomer(customer);

		if (customer.getState() == Customer.CustomerState.inStore) {
			double pickTime = this.time + state.getPickTime().next();

			pickEvent = new Pick(this.state, this.eventQueue, customer, pickTime);

			eventQueue.addEvent(pickEvent);
		}
	}

	public double getTime() {
		return time;
	}

	public Customer getCustomer() {
		return customer;
	}

	public String getName() {
		return "Ankomst";
	}
}
