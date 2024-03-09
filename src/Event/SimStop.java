// Amadeus Färdig

package Event;

import SimState.Customer;
import SimState.SimState;

public class SimStop extends Event {
	private double time;

	public SimStop(SimState state, EventQueue eventQueue) {
		super(state, eventQueue);
		this.time = 999.0;

	}

	@Override
	public void doMe() {
		state.update(this);
		state.turnOffSimRunning();
	}

	@Override
	public double getTime() {
		return time;
	}

	@Override
	public String getName() {
		return "Stop";
	}

	@Override
	public Customer getCustomer() {
		return null;
	}

}
