//Amadeus UPPDATERA CHANGEREGISTERAVAILABILITY I GITHUB

package Event;

import SimState.*;

public class Pick extends Event {
	private Customer customer;
	private double time;

	private Pay payEvent;

	public Pick(SimState state, EventQueue eventQueue, Customer customer, double time) {
		super(state, eventQueue);

		this.customer = customer;
		this.time = time;
	}

	public void doMe() {
		state.update(this);

		if (state.getStore().getFreeRegisters() > 0) {
			double payTime = this.time + state.getPayTime().next();

			payEvent = new Pay(this.state, this.eventQueue, payTime, customer);
			eventQueue.addEvent(payEvent);
			state.getStore().changeRegisterAvailability(true);
		}

		else {
			state.getStore().getRegisterLine().add(customer);
			state.getStore().addTotalCustomersInQueue();
		}
	}

	public double getTime() {
		return time;
	}

	public String getName() {
		return "Plock";
	}

	public Customer getCustomer() {
		return customer;
	}

}
