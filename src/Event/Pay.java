// Amadeus Färdig

package Event;

import SimState.*;

public class Pay extends Event {
	private Customer customer;
	private double time;

	public Pay(SimState state, EventQueue eventQueue, double time, Customer customer) {
		super(state, eventQueue);
		this.customer = customer;
		this.time = time;
	}

	@Override
	public void doMe() {
		state.update(this);
		state.getStore().addMoney();
		state.getStore().removeCustomer(customer);
		state.getStore().releaseRegister();

		if (!state.getStore().getFIFO().isEmpty()) {
			Customer nextCustomer = state.getStore().getFIFO().poll();
			double nextPayTime = this.time + state.getPayTime().next();
			Pay nextPayEvent = new Pay(this.state, this.eventQueue, nextPayTime, nextCustomer);
			eventQueue.addEvent(nextPayEvent);
			state.getStore().occupyRegister();
		}
	}

	@Override
	public double getTime() {
		return time;
	}

	@Override
	public String getName() {
		return "Betalning";
	}

	@Override
	public Customer getCustomer() {
		return customer;
	}

}
