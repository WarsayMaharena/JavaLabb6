package Event;

import SimState.*;

abstract public class Event {
	protected SimState state;
	protected EventQueue eventQueue;

	abstract public void doMe();

	abstract public double getTime();

	abstract public String getName();

	abstract public Customer getCustomer();

	public Event(SimState state, EventQueue eventQueue) {
		this.eventQueue = eventQueue;
		this.state = state;
	}
}
